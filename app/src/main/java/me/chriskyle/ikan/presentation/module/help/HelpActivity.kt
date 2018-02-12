package me.chriskyle.ikan.presentation.module.help

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import butterknife.OnClick
import me.chriskyle.ikan.R
import me.chriskyle.ikan.data.entity.ProblemEntity
import me.chriskyle.ikan.presentation.module.base.lce.LceActivity
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
class HelpActivity : LceActivity<HelpView, HelpPresenter>(), HelpView, ProblemAdapter.OnItemClickListener {

    @Inject
    override lateinit var presenter: HelpPresenter
    @Inject
    lateinit var problemAdapter: ProblemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.loadHotProblems()
    }

    override val layout: Int
        get() = R.layout.activity_help

    override fun initInjection(savedInstanceState: Bundle?) {
        super.initInjection(savedInstanceState)

        activityComponent().helpComponent(HelpModule()).inject(this)
    }

    override fun initView() {
        super.initView()

        title?.text = getString(R.string.help)

        problemAdapter.onItemClickListener = this

        refreshLayout?.isEnableLoadmore = false
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.adapter = problemAdapter
    }

    override fun renderProblems(problems: List<ProblemEntity>) {
        problemAdapter.setProblems(problems)
    }

    @OnClick(R.id.send_feedback)
    fun sendFeedback() {
        presenter.sendFeedback()
    }

    override fun onItemClick(problem: ProblemEntity) {
        presenter.problemDetail(problem)
    }

    companion object {

        fun getCallingIntent(context: Context) = Intent(context, HelpActivity::class.java)
    }
}