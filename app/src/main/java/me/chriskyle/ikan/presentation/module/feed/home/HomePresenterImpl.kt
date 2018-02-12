package me.chriskyle.ikan.presentation.module.feed.home

import io.reactivex.MaybeObserver
import io.reactivex.disposables.Disposable
import me.chriskyle.ikan.data.entity.SegmentEntity
import me.chriskyle.ikan.domain.repository.manager.FeedRepositoryManager
import me.chriskyle.ikan.presentation.module.base.BasePresenter
import me.chriskyle.library.eventbus.Bus

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class HomePresenterImpl(private val bus: Bus, private val feedRepositoryManager: FeedRepositoryManager) :
        BasePresenter<HomeView>(), HomePresenter {

    override fun loadFeedSegments() {
        feedRepositoryManager.executeGetSegments(LoadSegmentsObserver())
    }

    private fun loadSegmentsSuccess(segmentEntities: List<SegmentEntity>){
        if (segmentEntities.isNotEmpty()){
            val segments = arrayListOf<String>()

            segmentEntities.iterator().forEach {
                segments.add(it.name)
            }
            view?.renderFeedSegments(segments)
        }
    }

    private fun loadSegmentsError(e: Throwable){}

    inner class LoadSegmentsObserver : MaybeObserver<List<SegmentEntity>>{

        override fun onSubscribe(d: Disposable) {
            addSubscription(d)
        }

        override fun onSuccess(t: List<SegmentEntity>) {
           loadSegmentsSuccess(t)
        }

        override fun onComplete() {
            print("")
        }

        override fun onError(e: Throwable) {
            loadSegmentsError(e)
        }
    }
}