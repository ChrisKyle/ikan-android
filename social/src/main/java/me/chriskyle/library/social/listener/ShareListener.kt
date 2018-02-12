package me.chriskyle.library.social.listener

import me.chriskyle.library.social.internal.PlatformType

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/21.
 */
interface ShareListener {

    fun onShareComplete(platformType: PlatformType)

    fun onShareError(platformType: PlatformType, errMsg: String)

    fun onShareCancel(platformType: PlatformType)
}
