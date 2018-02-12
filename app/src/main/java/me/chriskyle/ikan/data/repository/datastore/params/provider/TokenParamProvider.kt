package me.chriskyle.ikan.data.repository.datastore.params.provider

import me.chriskyle.ikan.data.repository.datastore.params.Constants
import me.chriskyle.library.net.request.param.SimpleParamProvider

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/16.
 */
class TokenParamProvider : SimpleParamProvider() {

    fun refreshToken(token: String): TokenParamProvider {
        append(REFRESH_TOKEN, token)
        return this
    }

    companion object {

        private val REFRESH_TOKEN = Constants.API.REFRESH_TOKEN
    }
}
