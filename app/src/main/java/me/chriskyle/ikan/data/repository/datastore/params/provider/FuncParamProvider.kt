package me.chriskyle.ikan.data.repository.datastore.params.provider

import me.chriskyle.ikan.data.repository.datastore.params.Constants
import me.chriskyle.library.net.request.param.BaseParamsProvider

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/11.
 */
class FuncParamProvider : BaseParamsProvider() {

    fun content(content: String): FuncParamProvider {
        append(FEEDBACK_CONTENT, content)
        return this
    }

    companion object {

        const val FEEDBACK_CONTENT = Constants.API.FEEDBACK_CONTENT
    }
}
