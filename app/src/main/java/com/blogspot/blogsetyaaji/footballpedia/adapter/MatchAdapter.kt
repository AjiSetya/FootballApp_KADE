package com.blogspot.blogsetyaaji.footballpedia.adapter

import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import android.provider.CalendarContract.Events
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.blogspot.blogsetyaaji.footballmatchschedule.util.invisible
import com.blogspot.blogsetyaaji.footballmatchschedule.util.visible
import com.blogspot.blogsetyaaji.footballpedia.R
import com.blogspot.blogsetyaaji.footballpedia.model.match.EventsItem
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class LastMatchAdapter(
    private val contex: Context,
    private val jenis: Int,
    private val events: List<EventsItem>,
    private val listener: (EventsItem) -> Unit
) : RecyclerView.Adapter<MatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MatchViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_match, parent, false)
    )

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) =
        holder.bindItem(jenis, events[position], listener, contex)

}

class MatchViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
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
        listener: (EventsItem) -> Unit,
        contex: Context
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

        reminMatch?.onClick {
            val intent = Intent(Intent.ACTION_INSERT)
            intent.type = "vnd.android.cursor.item/event"

            val startDate = items.dateEvent
            val startHour = items.dateEvent
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(startDate + "-" + startHour)

            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, date.time)
            intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, date.time + 60 * 60 * 1000)
            intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false)

            intent.putExtra(Events.TITLE, "Next Match ${items.strEvent}")
            intent.putExtra(Events.DESCRIPTION, "${items.strDescriptionEN}")

            contex.startActivity(intent)
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
