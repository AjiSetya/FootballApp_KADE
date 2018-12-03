package com.blogspot.blogsetyaaji.footballpedia.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.blogspot.blogsetyaaji.footballmatchschedule.util.invisible
import com.blogspot.blogsetyaaji.footballpedia.R
import com.blogspot.blogsetyaaji.footballpedia.dblocal.FavoriteMatch
import org.jetbrains.anko.find
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class FavoriteMatchAdapter(private val favorite: List<FavoriteMatch>, private val listener: (FavoriteMatch) -> Unit) :
    RecyclerView.Adapter<FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FavoriteViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_match, parent, false)
    )

    override fun getItemCount(): Int = favorite.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) =
        holder.bindItem(favorite[position], listener)
}

class FavoriteViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
    private val homeclub = itemView?.findViewById<TextView>(R.id.itemhometeam)
    private val awayclub = itemView?.findViewById<TextView>(R.id.itemawayteam)
    private val homescore = itemView?.findViewById<TextView>(R.id.itemhomescore)
    private val awayscore = itemView?.findViewById<TextView>(R.id.itemawayscore)
    private val datematch = itemView?.findViewById<TextView>(R.id.txtmatchdate)
    private val timeMatch = itemView?.find<TextView>(R.id.txtmatchtime)
    private val reminMatch = itemView?.find<ImageView>(R.id.btnReminder)

    fun bindItem(items: FavoriteMatch, listener: (FavoriteMatch) -> Unit) {
        homeclub?.text = items.teamHome
        homescore?.text = items.teamHomeScore
        awayclub?.text = items.teamAway
        awayscore?.text = items.teamAwayScore
        reminMatch?.invisible()

        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val outputDateFormat = SimpleDateFormat("EEE, d MMM yyyy", Locale.US)
        val inputTimeFormat = SimpleDateFormat("HH:mm:ssZ", Locale.US)
        val outputTimeFormat = SimpleDateFormat("HH:mm", Locale.US)
        outputTimeFormat.setTimeZone(TimeZone.getTimeZone("UTC/GMT +7:00"))

        datematch?.text = parseDate(items.eventDate.toString(), inputDateFormat, outputDateFormat)
        timeMatch?.text = parseDate(items.eventTime.toString(), inputTimeFormat, outputTimeFormat)

        itemView.setOnClickListener {
            listener(items)
        }
    }

    private fun parseDate(
        inputDateString: String,
        inputDateFormat: SimpleDateFormat,
        outputDateFormat: SimpleDateFormat
    ): String? {
        var outputDateString: String? = null
        try {
            val date: Date = inputDateFormat.parse(inputDateString)
            outputDateString = outputDateFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return outputDateString
    }
}
