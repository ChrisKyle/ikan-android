@file:Suppress(
        names = ["NOTHING_TO_INLINE"]
)

package me.chriskyle.library.support.rxview

import android.view.View
import io.reactivex.Observable
import me.chriskyle.library.support.rxview.internal.VoidToUnit
import me.chriskyle.library.support.rxview.java.RxView

inline fun View.clicks(): Observable<Unit> = RxView.clicks(this).map(VoidToUnit)

