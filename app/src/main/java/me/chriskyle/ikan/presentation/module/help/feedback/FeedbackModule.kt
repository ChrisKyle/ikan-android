package me.chriskyle.ikan.presentation.module.help.feedback

import dagger.Module
import dagger.Provides
import me.chriskyle.ikan.domain.repository.manager.FuncRepositoryManager

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Module
class FeedbackModule {

    @Provides
    fun provideFeedbackPresenter(funcRepositoryManager: FuncRepositoryManager):
            FeedbackPresenter = FeedbackPresenterImpl(funcRepositoryManager)
}