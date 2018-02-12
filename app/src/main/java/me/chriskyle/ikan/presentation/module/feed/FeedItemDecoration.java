package me.chriskyle.ikan.presentation.module.feed;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/03.
 */
public class FeedItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public FeedItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.right = space;
        outRect.left = space;
    }
}
