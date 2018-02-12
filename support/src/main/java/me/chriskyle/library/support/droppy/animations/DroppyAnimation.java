package me.chriskyle.library.support.droppy.animations;

import android.view.View;

import me.chriskyle.library.support.droppy.DroppyMenuPopup;
import me.chriskyle.library.support.droppy.views.DroppyMenuPopupView;

public interface DroppyAnimation {

    void animateShow(final DroppyMenuPopupView popup, final View anchor);

    void animateHide(final DroppyMenuPopup popup, final DroppyMenuPopupView popupView, final View anchor, final boolean itemSelected);

}
