package me.chriskyle.ikan.presentation.module.feed

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/10/13.
 */
class FeedViewPagerAdapter(fragmentManager: FragmentManager, private val numbers: MutableList<Fragment>) :
        FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int) = numbers[position]

    override fun getCount() = numbers.size

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
    }
}