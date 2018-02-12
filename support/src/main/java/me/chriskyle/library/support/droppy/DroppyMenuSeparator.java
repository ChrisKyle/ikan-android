package me.chriskyle.library.support.droppy;

import android.content.Context;
import android.view.View;

import me.chriskyle.library.support.droppy.views.DroppyMenuSeparatorView;

/**
 * Created by shehabic on 2/28/15.
 */
public class DroppyMenuSeparator extends DroppyMenuItemAbstract {

    public DroppyMenuSeparator() {
        type = TYPE_MENU_SEPARATOR;
        setId(-1);
        isClickable = false;
    }

    @Override
    public View render(Context context) {
        if (renderedView == null) {
            renderedView = new DroppyMenuSeparatorView(context);
        }

        return renderedView;
    }
}
