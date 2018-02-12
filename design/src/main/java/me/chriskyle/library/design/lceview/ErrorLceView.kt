package me.chriskyle.library.design.lceview

import android.content.Context
import android.util.AttributeSet

import me.chriskyle.library.design.R

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/1/5.
 */
class ErrorLceView : AbsLceView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun bindView() {
        setIcon(R.drawable.ic_lce_error)
        setDesText(resources.getString(R.string.lce_error_des))
    }
}
