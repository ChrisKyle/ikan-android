package me.chriskyle.ikan.presentation.module.assets

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import butterknife.BindView
import butterknife.OnClick
import me.chriskyle.ikan.R
import me.chriskyle.ikan.presentation.module.base.BaseActivity
import me.chriskyle.library.design.table.TableRow
import me.chriskyle.library.support.TagGroup
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class AssetsActivity : BaseActivity<AssetsView, AssetsPresenter>(), AssetsView {

    @BindView(R.id.balance)
    @JvmField
    var balance: TableRow? = null

    @BindView(R.id.denominations)
    @JvmField
    var denominations: TagGroup? = null

    @BindView(R.id.donate)
    @JvmField
    var donate: Button? = null

    @Inject
    override lateinit var presenter: AssetsPresenter

    override val layout: Int
        get() = R.layout.activity_assets

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.loadBalance()
        presenter.loadDenominations()
    }

    override fun initInjection(savedInstanceState: Bundle?) {
        super.initInjection(savedInstanceState)

        activityComponent().assetsComponent(AssetsModule()).inject(this)
    }

    override fun initView() {
        super.initView()

        title?.text = getString(R.string.account_assets)
        denominations?.setOnTagClickListener { tag ->
            presenter.denominationChanged(tag)
        }
    }

    override fun renderBalance(balance: Long) {
        this.balance?.setSecondaryTitle(String.format(getString(R.string.account_balance), balance))
    }

    override fun renderDenominations(denominations: MutableList<String>) {
        this.denominations?.setTags(denominations)
    }

    override fun enableDonate() {
        donate?.isEnabled = true
    }

    @OnClick(R.id.donate)
    fun donate() {
        presenter.donate()
    }

    companion object {

        fun getCallingIntent(context: Context) = Intent(context, AssetsActivity::class.java)
    }
}