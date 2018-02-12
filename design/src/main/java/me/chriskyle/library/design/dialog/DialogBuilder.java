package me.chriskyle.library.design.dialog;

import android.os.Bundle;

/**
 * Description :
 * <p/>
 * Created by Chris Kyle on 2017/2/14.
 */
public abstract class DialogBuilder {

    protected final Bundle data = new Bundle();

    protected abstract AlertDialogFragment newInstance();

    public AlertDialogFragment build() {
        AlertDialogFragment frag = newInstance();
        frag.setArguments(this.data);

        return frag;
    }
}
