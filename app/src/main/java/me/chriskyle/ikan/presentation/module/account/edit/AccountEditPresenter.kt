package me.chriskyle.ikan.presentation.module.account.edit

import android.content.Intent
import me.chriskyle.library.mvp.MvpPresenter

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface AccountEditPresenter : MvpPresenter<AccountEditView>  {

    fun loadAccount()

    fun editNickname()

    fun genderChanged(id: Int)

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}