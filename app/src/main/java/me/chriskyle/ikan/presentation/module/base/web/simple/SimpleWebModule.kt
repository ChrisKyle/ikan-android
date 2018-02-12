package me.chriskyle.ikan.presentation.module.base.web.simple

import dagger.Module
import dagger.Provides

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Module
class SimpleWebModule {

    @Provides
    fun provideSimpleWebPresenter(): SimpleWebPresenter = SimpleWebPresenterImpl()
}