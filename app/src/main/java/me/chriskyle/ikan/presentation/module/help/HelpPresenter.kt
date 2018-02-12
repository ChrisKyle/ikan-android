package me.chriskyle.ikan.presentation.module.help

import me.chriskyle.ikan.data.entity.ProblemEntity
import me.chriskyle.library.mvp.lce.MvpLcePresenter

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface HelpPresenter : MvpLcePresenter<HelpView> {

    fun sendFeedback()

    fun loadHotProblems()

    fun problemDetail(problem: ProblemEntity)
}