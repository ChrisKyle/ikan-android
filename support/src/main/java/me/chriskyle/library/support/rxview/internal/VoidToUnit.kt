package me.chriskyle.library.support.rxview.internal

import io.reactivex.functions.Function

object VoidToUnit : Function<Any, Unit> {

    override fun apply(t: Any) = Unit
}
