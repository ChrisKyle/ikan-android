package me.chriskyle.ikan.presentation.module.feed.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView

import butterknife.ButterKnife
import com.facebook.drawee.view.SimpleDraweeView
import me.chriskyle.ikan.R
import me.chriskyle.ikan.data.entity.ActorEntity

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/11/7.
 */
class ActorsFragment : Fragment() {

    @BindView(R.id.avatar)
    @JvmField
    var avatar: SimpleDraweeView? = null

    @BindView(R.id.role_playing)
    @JvmField
    var rolePlaying: TextView? = null

    @BindView(R.id.actor_name)
    @JvmField
    var actorName: TextView? = null

    private lateinit var actor: ActorEntity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_actors, null)
        ButterKnife.bind(this, rootView)
        bindData()
        return rootView
    }

    fun setData(actorEntity: ActorEntity) {
        actor = actorEntity
    }

    private fun bindData() {
        avatar?.setImageURI(actor.avatarUrl)
        rolePlaying?.text = String.format(getString(R.string.feed_detail_video_role), actor.rolePlaying)
        actorName?.text = String.format(getString(R.string.feed_detail_video_actors), actor.name)
    }
}
