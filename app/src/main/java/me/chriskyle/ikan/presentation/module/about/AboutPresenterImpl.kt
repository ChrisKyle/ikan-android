package me.chriskyle.ikan.presentation.module.about

import io.reactivex.disposables.Disposable
import me.chriskyle.ikan.data.entity.VersionEntity
import me.chriskyle.ikan.data.repository.datastore.params.provider.UpdateParamProvider
import me.chriskyle.ikan.domain.DefaultObserver
import me.chriskyle.ikan.domain.repository.manager.UpdateRepositoryManager
import me.chriskyle.ikan.presentation.Router
import me.chriskyle.ikan.presentation.module.base.BasePresenter
import me.chriskyle.library.toolkit.utils.ManifestUtils
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class AboutPresenterImpl @Inject constructor(private val updateRepositoryManager: UpdateRepositoryManager) :
        BasePresenter<AboutView>(), AboutPresenter {

    override fun checkCurrentVersion() {
        view?.renderVersion(ManifestUtils.getVersionName(view?.ctx))
    }

    override fun checkVersionUpdate() {
        updateRepositoryManager.executeCheckUpdate(UpdateParamProvider(), CheckVersionUpdateObserver())
    }

    override fun versionUpdate() {
    }

    override fun share() {
        view?.showSharePopupWindow()
    }

    override fun feedback() {
        Router.routeToFeedback(view?.ctx)
    }

    fun checkVersionUpdateSuccess(t: VersionEntity) {
    }

    inner class CheckVersionUpdateObserver : DefaultObserver<VersionEntity>() {

        override fun onSubscribe(d: Disposable) {
            addSubscription(d)
        }

        override fun onNext(t: VersionEntity) {
            checkVersionUpdateSuccess(t)
        }
    }
}