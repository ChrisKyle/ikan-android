package me.chriskyle.ikan.data.repository.datastore

import io.reactivex.Observable
import me.chriskyle.ikan.data.entity.AssetsEntity
import me.chriskyle.ikan.data.entity.DenominationEntity

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/17.
 */
interface AssetsDataStore {

    fun getBalance(): Observable<AssetsEntity>

    fun getDenominations(): Observable<List<DenominationEntity>>
}