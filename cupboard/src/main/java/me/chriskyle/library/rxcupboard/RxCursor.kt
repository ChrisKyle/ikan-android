package me.chriskyle.library.rxcupboard

import android.database.Cursor

import io.reactivex.Flowable
import nl.qbusict.cupboard.Cupboard
import nl.qbusict.cupboard.CursorCompartment

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/7/17.
 */
class RxCursor internal constructor(cupboard: Cupboard, cursor: Cursor) {

    private val compartment: CursorCompartment = cupboard.withCursor(cursor)

    fun <T> iterate(entityClass: Class<T>): Flowable<T> {
        return Flowable.fromIterable(compartment.iterate(entityClass))
    }
}
