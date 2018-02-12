package me.chriskyle.ikan.presentation.module.help

import me.chriskyle.ikan.data.entity.ProblemEntity
import me.chriskyle.library.mvp.lce.MvpLceView

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
interface HelpView : MvpLceView {

    fun renderProblems(problems: List<ProblemEntity>)
}