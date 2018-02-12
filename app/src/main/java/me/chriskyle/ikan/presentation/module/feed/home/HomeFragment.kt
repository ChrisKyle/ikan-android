package me.chriskyle.ikan.presentation.module.feed.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.View
import butterknife.BindView
import me.chriskyle.ikan.R
import me.chriskyle.ikan.presentation.module.base.BaseFragment
import me.chriskyle.ikan.presentation.module.feed.FeedFragment
import me.chriskyle.ikan.presentation.module.feed.FeedViewPagerAdapter
import me.chriskyle.library.support.navigationtabstrip.NavigationTabStrip
import me.chriskyle.library.support.parallaxviewpager.ParallaxViewPager
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class HomeFragment : BaseFragment<HomeView, HomePresenter>(), HomeView {

    @BindView(R.id.movie_feed_view_page_tab_strip)
    @JvmField
    var movieTabStrip: NavigationTabStrip? = null

    @BindView(R.id.movie_view_pager)
    @JvmField
    var movieViewPager: ParallaxViewPager? = null

    @Inject
    override lateinit var presenter: HomePresenter

    override fun initInjection(savedInstanceState: Bundle?) {
        super.initInjection(savedInstanceState)

        fragmentComponent().homeComponent(HomeModule()).inject(this)
    }

    override val layout: Int
        get() = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.loadFeedSegments()
    }

    override fun renderFeedSegments(segments: ArrayList<String>) {
        movieTabStrip?.setTitles(segments)

        movieViewPager?.adapter = FeedViewPagerAdapter(childFragmentManager, getSegmentFeedsFragments(segments))
        movieViewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
            }
        })
        movieViewPager?.offscreenPageLimit = movieViewPager?.adapter!!.count
        movieTabStrip?.setViewPager(movieViewPager, 0)
    }

    private fun getSegmentFeedsFragments(segments: ArrayList<String>): MutableList<Fragment> {
        val numbers: MutableList<Fragment> = mutableListOf()
        for (segment in segments) {
            val feedFragment = FeedFragment()
            val bundle = Bundle()
            bundle.putString(FeedFragment.BUNDLE_KEY_SEGMENT, segment)
            feedFragment.arguments = bundle
            numbers.add(feedFragment)
        }

        return numbers
    }
}