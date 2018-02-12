package me.chriskyle.ikan.presentation.module.feed.detail

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

import me.chriskyle.ikan.data.entity.ActorEntity

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/27.
 */
class ActorViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    var actors: List<ActorEntity> = mutableListOf()

    override fun getItem(position: Int): Fragment {
        val actorsFragment = ActorsFragment()
        actorsFragment.setData(actors[position])
        return actorsFragment
    }

    override fun getCount() = actors.size
}
