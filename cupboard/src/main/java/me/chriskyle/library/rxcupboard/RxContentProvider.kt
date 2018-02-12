package me.chriskyle.library.rxcupboard

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import io.reactivex.Flowable
import nl.qbusict.cupboard.Cupboard
import nl.qbusict.cupboard.ProviderCompartment

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/7/17.
 */
class RxContentProvider internal constructor(cupboard: Cupboard, context: Context, private val uri: Uri) {

    private val compartment: ProviderCompartment = cupboard.withContext(context)

    fun <T> put(entity: T): Flowable<T> {
        compartment.put(uri, entity)
        return Flowable.just(entity)
    }

    fun <T> delete(entity: T): Flowable<T> {
        compartment.delete(uri, entity)
        return Flowable.just(entity)
    }

    fun <T> get(entityClass: Class<T>, id: Long): Flowable<T> {
        return Flowable.fromCallable {
            val getUri = ContentUris.withAppendedId(uri, id)
            compartment.get(getUri, entityClass)
        }
    }

    fun <T> query(entityClass: Class<T>): Flowable<T> {
        return Flowable.fromIterable(compartment.query(uri, entityClass).query())
    }

    fun <T> query(entityClass: Class<T>, selection: String, vararg args: String): Flowable<T> {
        return Flowable.fromIterable(compartment.query(uri, entityClass).withSelection(selection, *args).query())
    }

    fun <T> query(preparedQuery: ProviderCompartment.QueryBuilder<T>): Flowable<T> {
        return Flowable.fromIterable(preparedQuery.query())
    }
}
