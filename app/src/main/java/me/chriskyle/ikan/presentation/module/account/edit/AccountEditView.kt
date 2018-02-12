package me.chriskyle.ikan.presentation.module.account.edit

import android.net.Uri
import me.chriskyle.library.mvp.MvpView

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface AccountEditView : MvpView {

    fun renderAvatar(avatarUrl: String)

    fun renderAvatar(avatarUri: Uri)

    fun renderNickname(nickname: String?)

    fun renderGender(gender: String)

    fun showEditNicknameSuccess()

    fun showEditNicknameError(msg: String)

    fun showEditGenderSuccess()

    fun showEditGenderError(msg: String)
}