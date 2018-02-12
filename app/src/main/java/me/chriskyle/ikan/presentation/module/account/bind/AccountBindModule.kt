package me.chriskyle.ikan.presentation.module.account.bind

import dagger.Module
import dagger.Provides
import me.chriskyle.ikan.domain.repository.manager.AccountRepositoryManager

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Module
class AccountBindModule {

    @Provides
    fun provideAccountBindPresenter(accountRepositoryManager: AccountRepositoryManager): AccountBindPresenter =
            AccountBindPresenterImpl(accountRepositoryManager)
}