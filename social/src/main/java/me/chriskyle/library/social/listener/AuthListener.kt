package me.chriskyle.library.social.listener

import me.chriskyle.library.social.internal.PlatformType
import me.chriskyle.library.social.model.UserModel

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/21.
 */
interface AuthListener {

    fun onAuthComplete(platformType: PlatformType, userModel: UserModel)

    fun onAuthError(platformType: PlatformType, errMsg: String)

    fun onAuthCancel(platformType: PlatformType)
}
