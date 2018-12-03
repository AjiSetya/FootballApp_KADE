package com.blogspot.blogsetyaaji.footballpedia.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.blogspot.blogsetyaaji.footballpedia.R
import com.blogspot.blogsetyaaji.footballpedia.model.player.PlayerItem
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick


class PlayerAdapter(
    private val events: List<PlayerItem>,
    private val listener: (PlayerItem) -> Unit
) : RecyclerView.Adapter<PlayerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PlayerViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.player_item, parent, false)
    )

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) =
        holder.bindItem(events[position], listener)

}

class PlayerViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
    private val itemImgPlayer = itemView?.find<ImageView>(R.id.item_imgplayer)
    private val itemNameplayer = itemView?.find<TextView>(R.id.item_nameplayer)
    private val itemPositionplayer = itemView?.find<TextView>(R.id.item_positionplayer)

    fun bindItem(
        items: PlayerItem,
        listener: (PlayerItem) -> Unit
    ) {
        itemNameplayer?.text = items.strPlayer
        itemPositionplayer?.text = items.strPosition
        val options = RequestOptions()
            .fitCenter()
            .error(R.drawable.ic_broken_image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)

        Glide.with(itemView)
            .load(items.strCutout)
            .apply(options)
            .into(this.itemImgPlayer!!)


        itemView.onClick {
            listener(items)
        }
    }
}
