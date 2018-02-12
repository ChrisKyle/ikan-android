package me.chriskyle.library.rxcupboard

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/7/17.
 */
abstract class DatabaseChange<T : Any> {

    internal var entity: T? = null

    fun entityClass(): Class<T>? {
        return entity?.javaClass
    }

    fun entity(): T? {
        return entity
    }

    class DatabaseInsert<T : Any> : DatabaseChange<T>()

    class DatabaseUpdate<T : Any> : DatabaseChange<T>()

    class DatabaseDelete<T : Any> : DatabaseChange<T>()

    companion object {

        fun <T : Any> insert(entity: T): DatabaseInsert<T> {
            val insert = DatabaseInsert<T>()
            insert.entity = entity
            return insert
        }

        fun <T : Any> update(entity: T): DatabaseUpdate<T> {
            val update = DatabaseUpdate<T>()
            update.entity = entity
            return update
        }

        fun <T : Any> delete(entity: T): DatabaseDelete<T> {
            val delete = DatabaseDelete<T>()
            delete.entity = entity
            return delete
        }
    }
}
