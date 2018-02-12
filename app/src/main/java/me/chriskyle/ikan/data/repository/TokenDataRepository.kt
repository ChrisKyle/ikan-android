package me.chriskyle.ikan.data.repository

import me.chriskyle.ikan.data.repository.datastore.factory.TokenDataStoreFactory
import me.chriskyle.ikan.data.repository.datastore.params.provider.TokenParamProvider
import me.chriskyle.ikan.domain.repository.TokenRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/18.
 */
@Singleton
internal class TokenDataRepository @Inject
constructor(private val tokenDataStoreFactory: TokenDataStoreFactory)
    : TokenRepository {

    override fun bindDeviceToken(tokenParamProvider: TokenParamProvider) = tokenDataStoreFactory
            .createCloudDataStore()
            .bindDeviceToken(tokenParamProvider)
}
