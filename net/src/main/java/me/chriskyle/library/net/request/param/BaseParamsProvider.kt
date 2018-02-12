package me.chriskyle.library.net.request.param

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/16.
 */
abstract class BaseParamsProvider : SimpleParamProvider() {

    fun page(pageNumber: Int): BaseParamsProvider {
        append(PAGE, pageNumber.toString())
        return this
    }

    fun pageSize(pageSize: Int): BaseParamsProvider {
        append(PAGE_SIZE, pageSize.toString())
        return this
    }

    companion object {

        val PAGE = ParamConstants.API.PAGE
        val PAGE_SIZE = ParamConstants.API.PAGE_SIZE
    }
}
