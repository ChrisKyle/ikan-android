package me.chriskyle.ikan.presentation.module.help

import dagger.Module
import dagger.Provides
import me.chriskyle.ikan.domain.repository.manager.FuncRepositoryManager

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Module
class HelpModule {

    @Provides
    fun provideHelpPresenter(funcRepositoryManager: FuncRepositoryManager):
            HelpPresenter = HelpPresenterImpl(funcRepositoryManager)
}