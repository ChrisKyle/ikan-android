package me.chriskyle.ikan.presentation.module.about

import dagger.Module
import dagger.Provides
import me.chriskyle.ikan.domain.repository.manager.UpdateRepositoryManager

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Module
class AboutModule {

    @Provides
    fun provideAboutPresenter(updateRepositoryManager: UpdateRepositoryManager): AboutPresenter =
            AboutPresenterImpl(updateRepositoryManager)
}