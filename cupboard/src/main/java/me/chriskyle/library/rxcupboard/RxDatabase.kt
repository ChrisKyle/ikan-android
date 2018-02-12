package me.chriskyle.library.rxcupboard

import android.database.sqlite.SQLiteDatabase
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.Single
import io.reactivex.functions.Consumer
import io.reactivex.functions.Predicate
import io.reactivex.processors.PublishProcessor
import nl.qbusict.cupboard.Cupboard
import nl.qbusict.cupboard.DatabaseCompartment
import nl.qbusict.cupboard.QueryResultIterable

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/7/17.
 */
class RxDatabase internal constructor(private val cupboard: Cupboard,
                                      private val compartment: DatabaseCompartment,
                                      private val database: SQLiteDatabase) {

    private val processor = PublishProcessor.create<DatabaseChange<*>>()

    fun <T : Any> inserts(entityClass: Class<T>): Flowable<DatabaseChange.DatabaseInsert<T>> {
        return processor
                .filter(isEventOf(entityClass))
                .ofType(DatabaseChange.DatabaseInsert::class.java)
                .map<DatabaseChange.DatabaseInsert<T>> { it as DatabaseChange.DatabaseInsert<T>? }
                .hide()
    }

    fun <T : Any> updates(entityClass: Class<T>): Flowable<DatabaseChange.DatabaseUpdate<T>> {
        return processor
                .filter(isEventOf(entityClass))
                .ofType(DatabaseChange.DatabaseUpdate::class.java)
                .map<DatabaseChange.DatabaseUpdate<T>> { it as DatabaseChange.DatabaseUpdate<T>? }
                .hide()
    }

    fun <T : Any> deletes(entityClass: Class<T>): Flowable<DatabaseChange.DatabaseDelete<T>> {
        return processor
                .filter(isEventOf(entityClass))
                .ofType(DatabaseChange.DatabaseDelete::class.java)
                .map<DatabaseChange.DatabaseDelete<T>> { it as DatabaseChange.DatabaseDelete<T>? }
                .hide()
    }

    fun <T : Any> get(entityClass: Class<T>, id: Long): Single<T> {
        return Single.fromCallable { compartment.get(entityClass, id) }
    }

    fun <T : Any> putDirectly(entity: T): Long {
        val entityConverter = cupboard.getEntityConverter(entity.javaClass)
        val existed = entityConverter.getId(entity)
        val inserted = compartment.put(entity)
        return if (existed == null) {
            if (processor.hasSubscribers()) {
                processor.onNext(DatabaseChange.insert(entity))
            }
            inserted
        } else {
            if (processor.hasSubscribers()) {
                processor.onNext(DatabaseChange.update(entity))
            }
            existed
        }
    }

    fun <T : Any> put(entity: T): Single<T> {
        return Single.fromCallable {
            putDirectly(entity)
            entity
        }
    }

    fun put(entities: Collection<*>): Single<Collection<*>> {
        return Single.create { e ->
            compartment.put(entities)
            e.onSuccess(entities)
        }
    }

    fun <T : Any> put(): Consumer<T> {
        return Consumer { t -> putDirectly(t) }
    }

    fun <T : Any> deleteDirectly(entity: T): Boolean {
        val result = compartment.delete(entity)
        if (result && processor.hasSubscribers()) {
            processor.onNext(DatabaseChange.delete(entity))
        }
        return result
    }

    fun <T : Any> delete(entity: T): Single<T> {
        return Single.fromCallable {
            deleteDirectly(entity)
            entity
        }
    }

    fun <T : Any> deleteDirectly(entityClass: Class<T>, id: Long): Boolean {
        val result: Boolean
        if (processor.hasSubscribers()) {
            val entity = compartment.get(entityClass, id)
            result = compartment.delete(entity)
            if (result) {
                processor.onNext(DatabaseChange.delete(entity))
            }
        } else {
            result = compartment.delete(entityClass, id)
        }
        return result
    }

    fun <T : Any> delete(entityClass: Class<T>, id: Long): Single<Boolean> {
        return Single.fromCallable { deleteDirectly(entityClass, id) }
    }

    fun <T : Any> delete(entityClass: Class<T>, selection: String,
                         vararg selectionArgs: String): Single<Long> =
            Single.fromCallable { compartment.delete(entityClass, selection, *selectionArgs).toLong() }

    fun <T : Any> deleteAll(entityClass: Class<T>): Single<Long> {
        return delete(entityClass, "")
    }

    fun <T : Any> query(entityClass: Class<T>): Flowable<T> {
        val iterable = compartment.query(entityClass).query()
        return Flowable.fromIterable(iterable).compose(autoClose(iterable))
    }

    fun <T : Any> query(entityClass: Class<T>, selection: String, vararg args: String): Flowable<T> {
        val iterable = compartment.query(entityClass).withSelection(selection, *args).query()
        return Flowable.fromIterable(iterable).compose(autoClose(iterable))
    }

    fun <T : Any> query(query: DatabaseCompartment.QueryBuilder<T>): Flowable<T> {
        val iterable = query.query()
        return Flowable.fromIterable(iterable).compose(autoClose(iterable))
    }

    fun <T : Any> count(entityClass: Class<T>): Single<Long> {
        return Single.fromCallable {
            val table = cupboard.getTable(entityClass)
            database.compileStatement("select count(*) from " + table).simpleQueryForLong()
        }
    }

    fun <T : Any> buildQuery(entityClass: Class<T>): DatabaseCompartment.QueryBuilder<T> {
        return compartment.query(entityClass)
    }

    private fun <T : Any> autoClose(iterable: QueryResultIterable<T>): FlowableTransformer<T, T> {
        return FlowableTransformer { upstream ->
            upstream.doOnTerminate { iterable.close() }
                    .doOnCancel { iterable.close() }
        }
    }

    private fun <T : Any> isEventOf(entityClass: Class<T>): Predicate<DatabaseChange<*>> {
        return Predicate { event ->
            entityClass.isAssignableFrom(event.entityClass())
        }
    }
}
