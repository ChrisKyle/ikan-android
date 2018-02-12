package me.chriskyle.ikan.data.repository.datastore

import io.reactivex.Observable
import me.chriskyle.ikan.data.entity.PagerEntity
import me.chriskyle.ikan.data.entity.ProblemEntity
import me.chriskyle.ikan.data.entity.VersionEntity
import me.chriskyle.ikan.data.repository.datastore.params.provider.FuncParamProvider

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/11.
 */
interface FuncDataStore {

    fun getHotProblems(funcParamProvider: FuncParamProvider): Observable<PagerEntity<List<ProblemEntity>>>

    fun searchProblem(funcParamProvider: FuncParamProvider): Observable<List<ProblemEntity>>

    fun sendFeedback(funcParamProvider: FuncParamProvider): Observable<Any>

    fun getNewestVersion(): Observable<VersionEntity>
}
