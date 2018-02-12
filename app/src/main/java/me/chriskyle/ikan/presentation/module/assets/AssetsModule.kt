package me.chriskyle.ikan.presentation.module.assets

import dagger.Module
import dagger.Provides
import me.chriskyle.ikan.domain.repository.manager.AccountRepositoryManager
import me.chriskyle.ikan.domain.repository.manager.AssetsRepositoryManager

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Module
class AssetsModule {

    @Provides
    fun provideAboutPresenter(assetsRepositoryManager: AssetsRepositoryManager,
                              accountRepositoryManager: AccountRepositoryManager): AssetsPresenter =
            AssetsPresenterImpl(assetsRepositoryManager, accountRepositoryManager)
}