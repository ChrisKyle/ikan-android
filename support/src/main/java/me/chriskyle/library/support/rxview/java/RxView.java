package me.chriskyle.library.support.rxview.java;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.view.View;

import io.reactivex.Observable;

import static me.chriskyle.library.support.rxview.java.internal.Preconditions.checkNotNull;

public final class RxView {

    @CheckResult
    @NonNull
    public static Observable<Object> clicks(View view) {
        checkNotNull(view, "view == null");
        return new ViewClickObservable(view);
    }

    private RxView() {
        throw new AssertionError("No instances.");
    }
}
