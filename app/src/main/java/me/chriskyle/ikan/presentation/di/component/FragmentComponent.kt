package me.chriskyle.ikan.presentation.di.component

import dagger.Subcomponent
import me.chriskyle.ikan.presentation.di.PerFragment
import me.chriskyle.ikan.presentation.di.module.FragmentModule
import me.chriskyle.ikan.presentation.module.account.AccountComponent
import me.chriskyle.ikan.presentation.module.account.AccountModule
import me.chriskyle.ikan.presentation.module.feed.discover.DiscoverComponent
import me.chriskyle.ikan.presentation.module.feed.discover.DiscoverModule
import me.chriskyle.ikan.presentation.module.feed.FeedComponent
import me.chriskyle.ikan.presentation.module.feed.FeedModule
import me.chriskyle.ikan.presentation.module.feed.home.HomeComponent
import me.chriskyle.ikan.presentation.module.feed.home.HomeModule
import me.chriskyle.ikan.presentation.module.account.login.LoginComponent
import me.chriskyle.ikan.presentation.module.account.login.LoginModule
import me.chriskyle.ikan.presentation.module.feed.search.SearchComponent
import me.chriskyle.ikan.presentation.module.feed.search.SearchModule
import me.chriskyle.ikan.presentation.module.feed.recommend.RecommendComponent
import me.chriskyle.ikan.presentation.module.feed.recommend.RecommendModule
import me.chriskyle.ikan.presentation.module.feed.trending.TrendingComponent
import me.chriskyle.ikan.presentation.module.feed.trending.TrendingModule

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/15.
 */
@PerFragment
@Subcomponent(modules = [FragmentModule::class])
interface FragmentComponent {

    fun loginComponent(loginModule: LoginModule): LoginComponent

    fun homeComponent(homeModule: HomeModule): HomeComponent

    fun movieFeedsComponent(feedModule: FeedModule): FeedComponent

    fun trendingComponent(trendingModule: TrendingModule): TrendingComponent

    fun seeListComponent(recommendModule: RecommendModule): RecommendComponent

    fun searchComponent(searchModule: SearchModule): SearchComponent

    fun discoverComponent(discoverModule: DiscoverModule): DiscoverComponent

    fun accountComponent(accountModule: AccountModule): AccountComponent
}