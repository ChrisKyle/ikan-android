package me.chriskyle.ikan.presentation.module.main

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.View
import android.widget.ImageView
import android.widget.ViewFlipper
import butterknife.BindView
import com.facebook.drawee.view.SimpleDraweeView
import io.reactivex.android.schedulers.AndroidSchedulers
import me.chriskyle.ikan.R
import me.chriskyle.ikan.data.entity.VersionEntity
import me.chriskyle.ikan.presentation.module.account.AccountFragment
import me.chriskyle.ikan.presentation.module.base.BaseActivity
import me.chriskyle.ikan.presentation.module.feed.search.SearchFragment
import me.chriskyle.ikan.presentation.module.account.login.LoginFragment
import me.chriskyle.ikan.presentation.module.update.UpdateAppManager
import me.chriskyle.library.design.fragmentanim.FragmentTransactionWrapper
import me.chriskyle.library.design.fragmentanim.PlaceHolderFragment
import me.chriskyle.library.social.SocialApi
import me.chriskyle.library.support.bottomnavigation.BottomNavigationBar
import me.chriskyle.library.support.bottomnavigation.BottomNavigationItem
import me.chriskyle.library.support.rxview.java.RxView
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/15.
 */
class MainActivity : BaseActivity<MainView, MainPresenter>(), MainView,
        BottomNavigationBar.OnTabSelectedListener, OnLoginCallback {

    @BindView(R.id.fragment_container)
    @JvmField
    internal var fragmentContainer: ViewFlipper? = null

    @BindView(R.id.bottom_nav_bar)
    @JvmField
    var bottomNavigationBar: BottomNavigationBar? = null

    @BindView(R.id.search)
    @JvmField
    var search: ImageView? = null

    @BindView(R.id.account_avatar)
    @JvmField
    var accountAvatar: SimpleDraweeView? = null

    @Inject
    override lateinit var presenter: MainPresenter

    private var placeHolderFragment: PlaceHolderFragment? = null

    override val layout: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.checkLoginStatus()
        presenter.checkVersionUpdate()
    }

    override fun initInjection(savedInstanceState: Bundle?) {
        super.initInjection(savedInstanceState)

        activityComponent().mainComponent(MainModule()).inject(this)
    }

    override fun initView() {
        super.initView()

        bottomNavigationBar!!.setMode(BottomNavigationBar.MODE_FIXED)
        bottomNavigationBar!!.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
        bottomNavigationBar!!
                .addItem(BottomNavigationItem(R.drawable.ic_tab_home, R.string.main_tab_home)
                        .setActiveColorResource(R.color.main_bottom_nar_bar_text_color_active)
                        .setInActiveColorResource(R.color.main_bottom_nar_bar_text_color_inactive))
                .addItem(BottomNavigationItem(R.drawable.ic_tab_trending, R.string.main_tab_trending)
                        .setActiveColorResource(R.color.main_bottom_nar_bar_text_color_active)
                        .setInActiveColorResource(R.color.main_bottom_nar_bar_text_color_inactive))
                .addItem(BottomNavigationItem(R.drawable.ic_tab_discover, R.string.main_tab_discover)
                        .setActiveColorResource(R.color.main_bottom_nar_bar_text_color_active)
                        .setInActiveColorResource(R.color.main_bottom_nar_bar_text_color_inactive))
                .addItem(BottomNavigationItem(R.drawable.ic_tab_see_list, R.string.main_tab_see_list)
                        .setActiveColorResource(R.color.main_bottom_nar_bar_text_color_active)
                        .setInActiveColorResource(R.color.main_bottom_nar_bar_text_color_inactive))
                .setFirstSelectedPosition(0)
                .initialise()
        bottomNavigationBar!!.setTabSelectedListener(this)

        initPlaceHolderFragment()

        RxView.clicks(accountAvatar as View)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    presenter.account()
                }

        RxView.clicks(search as View)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    presenter.search()
                }
    }

    private fun initPlaceHolderFragment() {
        placeHolderFragment = PlaceHolderFragment()

        val fm = supportFragmentManager
        val fragmentTransaction = fm.beginTransaction()
        fragmentTransaction.add(R.id.root_fragment_container, placeHolderFragment)
        fragmentTransaction.commit()
    }

    override fun showSearchFragment() {
        replaceRootFragment(SearchFragment(), true)
    }

    override fun showAccountFragment() {
        val fm = supportFragmentManager
        val fragmentTransaction = fm.beginTransaction()
        val fragmentTransactionExtended = FragmentTransactionWrapper(this, fragmentTransaction,
                placeHolderFragment, AccountFragment(), R.id.root_fragment_container)
        fragmentTransactionExtended.addTransition(FragmentTransactionWrapper.SLIDE_VERTICAL)
        fragmentTransactionExtended.commit()
    }

    override fun showUpdateDialog(versionEntity: VersionEntity) {
        UpdateAppManager().showUpdateDialog(this, versionEntity)    }

    override fun renderAvatar(avatarUrl: String?) {
        if (avatarUrl == null) {
            accountAvatar?.setActualImageResource(R.drawable.ic_account_avatar_default)
        } else {
            accountAvatar?.setImageURI(avatarUrl)
        }
    }

    private fun replaceRootFragment(fragment: Fragment, isAddBackStack: Boolean) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.root_fragment_container, fragment)
        if (isAddBackStack) {
            transaction.addToBackStack(fragment.javaClass.simpleName)
        }

        transaction.commit()
    }

    override fun onOnLogin() {
        val fm = supportFragmentManager
        val fragmentTransaction = fm.beginTransaction()
        val fragmentTransactionExtended = FragmentTransactionWrapper(this, fragmentTransaction,
                placeHolderFragment, LoginFragment(), R.id.root_fragment_container)
        fragmentTransactionExtended.addTransition(FragmentTransactionWrapper.SCALEXY)
        fragmentTransactionExtended.commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount != 0) {
            super.onBackPressed()
        } else {
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null) {
            SocialApi.instance.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onTabSelected(position: Int) {
        fragmentContainer!!.displayedChild = position
    }

    override fun onTabUnselected(position: Int) {
    }

    override fun onTabReselected(position: Int) {
    }
}