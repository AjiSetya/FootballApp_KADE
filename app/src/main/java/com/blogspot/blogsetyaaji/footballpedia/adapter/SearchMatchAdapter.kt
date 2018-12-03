package com.blogspot.blogsetyaaji.footballpedia.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.blogspot.blogsetyaaji.footballmatchschedule.util.invisible
import com.blogspot.blogsetyaaji.footballmatchschedule.util.visible
import com.blogspot.blogsetyaaji.footballpedia.R
import com.blogspot.blogsetyaaji.footballpedia.model.searchmatch.EventsItem
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class SearchMatchAdapter(
    private val jenis: Int,
    private val events: List<EventsItem>,
    private val listener: (EventsItem) -> Unit
) : RecyclerView.Adapter<SearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SearchViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_match, parent, false)
    )

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) =
        holder.bindItem(jenis, events[position], listener)

}

class SearchViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
    private val homeClub = itemView?.find<TextView>(R.id.itemhometeam)
    private val awayClub = itemView?.find<TextView>(R.id.itemawayteam)
    private val homeScore = itemView?.find<TextView>(R.id.itemhomescore)
    private val awayScore = itemView?.find<TextView>(R.id.itemawayscore)
    private val dateMatch = itemView?.find<TextView>(R.id.txtmatchdate)
    private val timeMatch = itemView?.find<TextView>(R.id.txtmatchtime)
    private val reminMatch = itemView?.find<ImageView>(R.id.btnReminder)

    fun bindItem(
        jenis: Int,
        items: EventsItem,
        listener: (EventsItem) -> Unit
    ) {
        homeClub?.text = items.strHomeTeam
        awayClub?.text = items.strAwayTeam
        homeScore?.text = items.intHomeScore
        awayScore?.text = items.intAwayScore

        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val outputDateFormat = SimpleDateFormat("EEE, d MMM yyyy", Locale.US)
        val inputTimeFormat = SimpleDateFormat("HH:mm:ssZ", Locale.US)
        val outputTimeFormat = SimpleDateFormat("HH:mm", Locale.US)
        outputTimeFormat.setTimeZone(TimeZone.getTimeZone("UTC/GMT +7:00"))

        dateMatch?.text = parseDate(items.dateEvent.toString(), inputDateFormat, outputDateFormat)
        timeMatch?.text = parseDate(items.strTime.toString(), inputTimeFormat, outputTimeFormat)

        itemView.onClick {
            listener(items)
        }

        if (jenis == 0) {
            reminMatch?.invisible()
        } else if (jenis == 1) {
            reminMatch?.visible()
        }
    }

    //h:mm a /EEE, d MMM yyyy/9:15 PM

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
