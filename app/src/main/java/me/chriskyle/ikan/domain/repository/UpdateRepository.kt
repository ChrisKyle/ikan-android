package me.chriskyle.ikan.domain.repository

import io.reactivex.Observable
import me.chriskyle.ikan.data.entity.VersionEntity
import me.chriskyle.ikan.data.repository.datastore.params.provider.UpdateParamProvider

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/15.
 */
interface UpdateRepository {

    fun checkUpdate(updateParamProvider: UpdateParamProvider): Observable<VersionEntity>
}