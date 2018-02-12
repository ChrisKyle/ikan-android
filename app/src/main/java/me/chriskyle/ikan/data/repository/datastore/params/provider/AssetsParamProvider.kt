package me.chriskyle.ikan.data.repository.datastore.params.provider

import me.chriskyle.ikan.data.repository.datastore.params.Constants
import me.chriskyle.library.net.request.param.SimpleParamProvider

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/17.
 */
class AssetsParamProvider : SimpleParamProvider() {

    fun accountId(accountId: Long): AssetsParamProvider {
        append(ACCOUNT_ID, accountId)
        return this
    }

    fun getAccountId() = optionalParam.map[ACCOUNT_ID] as String

    companion object {

        const val ACCOUNT_ID = Constants.API.ACCOUNT_ID
    }
}