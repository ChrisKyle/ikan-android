package me.chriskyle.library.rxcupboard

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri

import nl.qbusict.cupboard.Cupboard
import nl.qbusict.cupboard.CupboardFactory

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/7/17.
 */
object RxCupboard {

    fun with(cupboard: Cupboard, db: SQLiteDatabase): RxDatabase {
        return RxDatabase(cupboard, cupboard.withDatabase(db), db)
    }

    fun withDefault(db: SQLiteDatabase): RxDatabase {
        return RxDatabase(CupboardFactory.cupboard(), CupboardFactory.cupboard().withDatabase(db), db)
    }

    fun with(cupboard: Cupboard, context: Context, uri: Uri): RxContentProvider {
        return RxContentProvider(cupboard, context, uri)
    }

    fun withDefault(context: Context, uri: Uri): RxContentProvider {
        return RxContentProvider(CupboardFactory.cupboard(), context, uri)
    }

    fun with(cupboard: Cupboard, cursor: Cursor): RxCursor {
        return RxCursor(cupboard, cursor)
    }

    fun withDefault(cursor: Cursor): RxCursor {
        return RxCursor(CupboardFactory.cupboard(), cursor)
    }
}
