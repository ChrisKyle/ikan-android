package me.chriskyle.library.design.lceview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import me.chriskyle.library.design.R

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/1/5.
 */
abstract class AbsLceView : LinearLayout {

    private var onLceActionListener: OnLceActionListener? = null

    var icon: ImageView? = null
    var des: TextView? = null

    open val layoutResourceId: Int
        get() = R.layout.view_lce

    constructor(context: Context) : super(context) {

        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        initView()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

        initView()
    }

    private fun initView() {
        LayoutInflater.from(context).inflate(layoutResourceId, this)
        icon = findViewById(R.id.icon)
        des = findViewById(R.id.des)
        icon!!.setOnClickListener { _ ->
            if (null != onLceActionListener) {
                onLceActionListener!!.onIceActionClick()
            }
        }
        des!!.setOnClickListener { _ ->
            if (null != onLceActionListener) {
                onLceActionListener!!.onIceActionClick()
            }
        }
        bindView()
    }

    protected abstract fun bindView()

    fun setIcon(resId: Int) {
        icon!!.setBackgroundResource(resId)
    }

    fun setDesText(desText: String?) {
        des!!.text = desText
    }

    fun setDesText(desText: Int) {
        des!!.text = context.getString(desText)
    }

    fun setOnLceActionListener(onLceActionListener: OnLceActionListener) {
        this.onLceActionListener = onLceActionListener
    }

    interface OnLceActionListener {

        fun onIceActionClick()
    }
}
