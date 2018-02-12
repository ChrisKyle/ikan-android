package me.chriskyle.ikan.presentation.module.account.edit

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.facebook.drawee.view.SimpleDraweeView

import me.chriskyle.ikan.R
import me.chriskyle.ikan.presentation.module.base.BaseActivity
import me.chriskyle.library.support.droppy.DroppyMenuItem
import me.chriskyle.library.support.droppy.DroppyMenuPopup
import me.chriskyle.library.support.droppy.animations.DroppyFadeInAnimation
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class AccountEditActivity : BaseActivity<AccountEditView, AccountEditPresenter>(), AccountEditView {

    @BindView(R.id.account_avatar)
    @JvmField
    var accountAvatar: SimpleDraweeView? = null

    @BindView(R.id.nickname)
    @JvmField
    var nickname: TextView? = null

    @BindView(R.id.gender)
    @JvmField
    var gender: TextView? = null

    @BindView(R.id.avatar_edit)
    @JvmField
    var avatarEdit: View? = null

    @BindView(R.id.sex_indicator)
    @JvmField
    var sexIndicator: View? = null

    private lateinit var dropMenuPopup: DroppyMenuPopup

    @Inject
    override lateinit var presenter: AccountEditPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.loadAccount()
    }

    override val layout: Int
        get() = R.layout.activity_account_edit

    override fun initInjection(savedInstanceState: Bundle?) {
        super.initInjection(savedInstanceState)

        activityComponent().accountEditComponent(AccountEditModule()).inject(this)
    }

    override fun initView() {
        super.initView()

        back?.setImageResource(R.drawable.ic_arrow_back_grey)
        title?.text = getString(R.string.account_edit)
    }

    override fun renderAvatar(avatarUrl: String) {
        accountAvatar?.setImageURI(avatarUrl)
    }

    override fun renderAvatar(avatarUri: Uri) {
        accountAvatar?.setImageURI(avatarUri, this)
    }

    override fun renderNickname(nickname: String?) {
        this.nickname?.text = nickname
    }

    override fun renderGender(gender: String) {
        this.gender?.text = gender
    }

    override fun showEditNicknameSuccess() {
        showMessage(getString(R.string.nickname_edit_success))
    }

    override fun showEditNicknameError(msg: String) {
        showMessage(msg)
    }

    override fun showEditGenderSuccess() {
        showMessage(getString(R.string.gender_edit_success))
    }

    override fun showEditGenderError(msg: String) {
        showMessage(msg)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        presenter.onActivityResult(requestCode, resultCode, data)
    }

    @OnClick(R.id.sex_edit)
    fun sexEdit() {
        val dropDownMenuBuilder = DroppyMenuPopup.Builder(this, sexIndicator)
        dropMenuPopup = dropDownMenuBuilder
                .addMenuItem(DroppyMenuItem(getString(R.string.account_edit_sex_male), R.drawable.ic_sex_male))
                .addMenuItem(DroppyMenuItem(getString(R.string.account_edit_sex_female), R.drawable.ic_sex_female))
                .addSeparator()
                .setPopupAnimation(DroppyFadeInAnimation())
                .setXOffset(5)
                .setYOffset(5)
                .setOnClick { _, id ->
                    dropMenuPopup.dismiss(false)
                    presenter.genderChanged(id)
                }
                .build()
        dropMenuPopup.show()
    }

    @OnClick(R.id.nickname_lay)
    fun nicknameEdit() {
        presenter.editNickname()
    }

    companion object {

        fun getCallingIntent(context: Context) = Intent(context, AccountEditActivity::class.java)
    }
}