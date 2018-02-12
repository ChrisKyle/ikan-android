package me.chriskyle.ikan.data.repository.datastore.cloud.response

import io.reactivex.Observable
import me.chriskyle.ikan.data.exception.BalanceNotEnoughException
import me.chriskyle.ikan.data.exception.BuyFailException
import me.chriskyle.ikan.data.exception.UnKnownException
import me.chriskyle.library.net.response.DataResponse

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/16.
 */
object ResponseFlatResult {

    fun <T : Any> flatResult(result: DataResponse<T>): Observable<T> {
        return Observable.create { emitter ->
            when (result.statusCode) {
                ResponseStatus.STATUS_CODE_10000 -> {
                    result.data?.let {
                        emitter.onNext(result.data!!)
                    }
                    emitter.onComplete()
                }
                ResponseStatus.STATUS_CODE_10001 -> {
                }
                ResponseStatus.STATUS_CODE_10002 -> {
                }
                ResponseStatus.STATUS_CODE_10003 -> {
                }
                ResponseStatus.STATUS_CODE_10004 -> {
                }
                ResponseStatus.STATUS_CODE_10005 -> {
                }
                ResponseStatus.STATUS_CODE_10006 -> {
                }
                ResponseStatus.STATUS_CODE_10007 -> {
                }
                ResponseStatus.STATUS_CODE_10008 -> {
                }
                ResponseStatus.STATUS_CODE_10009 -> {
                }
                ResponseStatus.STATUS_CODE_10010 -> {
                }
                ResponseStatus.STATUS_CODE_10011 -> {
                }
                ResponseStatus.STATUS_CODE_10012 -> {
                }
                ResponseStatus.STATUS_CODE_10013 -> {
                    emitter.onError(BuyFailException())
                }
                ResponseStatus.STATUS_CODE_10014 -> {
                    emitter.onError(BalanceNotEnoughException())
                }
                else -> {
                    emitter.onError(UnKnownException())
                }
            }
        }
    }
}
