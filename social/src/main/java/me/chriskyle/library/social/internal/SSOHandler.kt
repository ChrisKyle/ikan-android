package me.chriskyle.library.social.internal

import android.app.Activity
import android.content.Intent
import me.chriskyle.library.social.listener.AuthListener
import me.chriskyle.library.social.listener.ShareListener
import me.chriskyle.library.social.media.BaseShareMedia

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/21.
 */
interface SSOHandler {

    fun init(activity: Activity, platform: PlatformConfig.Platform)

    fun authorize(activity: Activity, authListener: AuthListener?)

    fun share(activity: Activity, baseShareMedia: BaseShareMedia, shareListener: ShareListener?)

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent)

    fun isInstall(): Boolean
}
