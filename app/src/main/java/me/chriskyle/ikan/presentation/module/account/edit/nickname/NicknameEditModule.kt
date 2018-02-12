package me.chriskyle.ikan.presentation.module.account.edit.nickname

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
class NicknameEditModule {

    @Provides
    fun provideNicknameEditPresenter(bus: Bus, accountRepositoryManager: AccountRepositoryManager):
            NicknameEditPresenter = NicknameEditPresenterImpl(bus, accountRepositoryManager)
}