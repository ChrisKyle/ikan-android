package me.chriskyle.ikan.presentation.module.help

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import me.chriskyle.ikan.R
import me.chriskyle.ikan.data.entity.ProblemEntity
import javax.inject.Inject

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/11.
 */
class ProblemAdapter @Inject constructor() : RecyclerView.Adapter<ProblemAdapter.ViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null
    private var problems: MutableList<ProblemEntity> = mutableListOf()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val problem = problems[position]
        holder.title?.text = problem.title

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(problem)
        }
    }

    override fun getItemCount() = problems.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_help_article, parent,
                    false))

    fun setProblems(problems: List<ProblemEntity>) {
        this.problems = problems as MutableList<ProblemEntity>
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.title)
        @JvmField
        var title: TextView? = null

        init {
            ButterKnife.bind(this, itemView)
        }
    }

    interface OnItemClickListener {

        fun onItemClick(problem: ProblemEntity)
    }
}