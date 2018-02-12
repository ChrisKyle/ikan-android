package me.chriskyle.library.design.lceview

import android.content.Context
import android.util.AttributeSet

import me.chriskyle.library.support.circularprogressbar.CircularProgressBar

/**
 * Description :
 *
 *
 * Created by Chris Kyle on 2017/3/20.
 */
class LoadingLceView : CircularProgressBar {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}
}
