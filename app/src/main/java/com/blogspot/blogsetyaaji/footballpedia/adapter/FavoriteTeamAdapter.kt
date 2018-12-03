package com.blogspot.blogsetyaaji.footballpedia.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.blogspot.blogsetyaaji.footballpedia.R
import com.blogspot.blogsetyaaji.footballpedia.dblocal.FavoriteTeam
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import org.jetbrains.anko.find

class FavoriteTeamAdapter(private val favorite: List<FavoriteTeam>, private val listener: (FavoriteTeam) -> Unit) :
    RecyclerView.Adapter<FavoriteTeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FavoriteTeamViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.team_item, parent, false)
    )

    override fun getItemCount(): Int = favorite.size

    override fun onBindViewHolder(holder: FavoriteTeamViewHolder, position: Int) =
        holder.bindItem(favorite[position], listener)
}

class FavoriteTeamViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
    private val itemNameteam = itemView?.find<TextView>(R.id.item_nameteam)
    private val itemImgteam = itemView?.find<ImageView>(R.id.item_imgteam)

    fun bindItem(items: FavoriteTeam, listener: (FavoriteTeam) -> Unit) {
        itemNameteam?.text = items.teamName

        val options = RequestOptions()
            .fitCenter()
            .error(R.drawable.ic_broken_image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)

        Glide.with(itemView)
            .load(items.teamImage)
            .apply(options)
            .into(this.itemImgteam!!)

        itemView.setOnClickListener {
            listener(items)
        }
    }
}
