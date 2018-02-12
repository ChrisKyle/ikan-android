package me.chriskyle.library.support.recyclerview.cardview;

import android.support.v7.widget.RecyclerView;

/**
 * @author yuqirong
 */
public interface OnSwipeListener<T> {

    void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction);

    void onSwiped(RecyclerView.ViewHolder viewHolder, T t, int direction);

    void onSwipedClear();
}
