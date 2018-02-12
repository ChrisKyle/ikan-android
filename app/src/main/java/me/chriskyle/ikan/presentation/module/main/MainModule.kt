package me.chriskyle.ikan.presentation.module.main

import dagger.Module
import dagger.Provides
import me.chriskyle.ikan.domain.repository.manager.AccountRepositoryManager
import me.chriskyle.ikan.domain.repository.manager.FuncRepositoryManager
import me.chriskyle.library.eventbus.Bus

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/15.
 */
@Module
class MainModule {

    @Provides
    fun provideMainPresenter(bus: Bus, accountRepositoryManager: AccountRepositoryManager,
                             funcRepositoryManager: FuncRepositoryManager):
            MainPresenter = MainPresenterImpl(bus, accountRepositoryManager, funcRepositoryManager)
}