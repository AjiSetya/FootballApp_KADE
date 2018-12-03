package com.blogspot.blogsetyaaji.footballpedia.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.blogspot.blogsetyaaji.footballpedia.R
import com.blogspot.blogsetyaaji.footballpedia.model.hometeam.HomeTeamsItem
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick

class TeamAdapter(
    private val events: List<HomeTeamsItem>,
    private val listener: (HomeTeamsItem) -> Unit
) : RecyclerView.Adapter<TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TeamViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.team_item, parent, false)
    )

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) =
        holder.bindItem(events[position], listener)

}

class TeamViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
    private val itemNameteam = itemView?.find<TextView>(R.id.item_nameteam)
    private val itemImgteam = itemView?.find<ImageView>(R.id.item_imgteam)


    fun bindItem(
        items: HomeTeamsItem,
        listener: (HomeTeamsItem) -> Unit
    ) {
        itemNameteam?.text = items.strTeam

        val options = RequestOptions()
            .fitCenter()
            .error(R.drawable.ic_broken_image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)

        Glide.with(itemView)
            .load(items.strTeamBadge)
            .apply(options)
            .into(this.itemImgteam!!)

        itemView.onClick {
            listener(items)
        }

    }
}

