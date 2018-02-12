package me.chriskyle.ikan.presentation.module.account

import dagger.Module
import dagger.Provides
import me.chriskyle.ikan.domain.repository.manager.AccountRepositoryManager
import me.chriskyle.library.eventbus.Bus

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Module
class AccountModule {

    @Provides
    fun provideAccountPresenter(bus: Bus, accountRepositoryManager: AccountRepositoryManager):
            AccountPresenter = AccountPresenterImpl(bus, accountRepositoryManager)
}