package com.blogspot.blogsetyaaji.footballpedia.match.detailmatch

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.blogspot.blogsetyaaji.footballmatchschedule.util.invisible
import com.blogspot.blogsetyaaji.footballmatchschedule.util.visible
import com.blogspot.blogsetyaaji.footballpedia.R
import com.blogspot.blogsetyaaji.footballpedia.R.drawable.ic_add_to_favorites
import com.blogspot.blogsetyaaji.footballpedia.R.drawable.ic_added_to_favorites
import com.blogspot.blogsetyaaji.footballpedia.R.id.add_to_favorite
import com.blogspot.blogsetyaaji.footballpedia.R.menu.detail_menu
import com.blogspot.blogsetyaaji.footballpedia.api.ApiRepository
import com.blogspot.blogsetyaaji.footballpedia.dblocal.FavoriteMatch
import com.blogspot.blogsetyaaji.footballpedia.dblocal.database
import com.blogspot.blogsetyaaji.footballpedia.model.awayteam.AwayTeamsItem
import com.blogspot.blogsetyaaji.footballpedia.model.hometeam.HomeTeamsItem
import com.blogspot.blogsetyaaji.footballpedia.model.match.EventsItem
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DetailMatchActivity : AppCompatActivity(), DetailMatchView {

    private lateinit var detailMatchPresenter: DetailMatchPresenter
    private lateinit var eventsItem: List<EventsItem>
    private lateinit var id: String
    private lateinit var options: RequestOptions
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)

        id = intent.getStringExtra("idEvent")
        favoriteState()

        options = RequestOptions()
            .fitCenter()
            .error(R.drawable.ic_broken_image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)

        val apiRepository = ApiRepository()
        val gson = Gson()
        detailMatchPresenter = DetailMatchPresenter(this, apiRepository, gson)

        detailMatchPresenter.getDetailMatch(id)
    }

    override fun showLoading() {
        progressBar.bringToFront()
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showDetailMatch(data: List<EventsItem>) {
        eventsItem = data
        detailhometeam.text = eventsItem[0].strHomeTeam
        detailawayteam.text = eventsItem[0].strAwayTeam
        detailhomescore.text = eventsItem[0].intHomeScore
        detailawayscore.text = eventsItem[0].intAwayScore
        detailhomegoal.text = eventsItem[0].strHomeGoalDetails
        detailawaygoal.text = eventsItem[0].strAwayGoalDetails
        detailhomedefense.text = eventsItem[0].strHomeLineupDefense
        detailawaydefense.text = eventsItem[0].strAwayLineupDefense
        detailhomemodifield.text = eventsItem[0].strHomeLineupMidfield
        detailawaymodifield.text = eventsItem[0].strAwayLineupMidfield
        detailhomeforward.text = eventsItem[0].strHomeLineupForward
        detailawayforward.text = eventsItem[0].strAwayLineupForward
        detailhomesubstitutes.text = eventsItem[0].strHomeLineupSubstitutes
        detailawaysubstitutes.text = eventsItem[0].strAwayLineupSubstitutes
        detailhomeshot.text = eventsItem[0].intHomeShots
        detailawayshot.text = eventsItem[0].intAwayShots

        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val outputDateFormat = SimpleDateFormat("EEE, d MMM yyyy", Locale.US)
        val inputTimeFormat = SimpleDateFormat("HH:mm:ssZ", Locale.US)
        val outputTimeFormat = SimpleDateFormat("HH:mm", Locale.US)
        outputTimeFormat.setTimeZone(TimeZone.getTimeZone("UTC/GMT +7:00"))

        detaildate?.text = parseDate(eventsItem[0].dateEvent.toString(), inputDateFormat, outputDateFormat)
        detailtime?.text = parseDate(eventsItem[0].strTime.toString(), inputTimeFormat, outputTimeFormat)

        detailMatchPresenter.getDetailHomeTeam(eventsItem[0].idHomeTeam!!)
        detailMatchPresenter.getDetailAwayTeam(eventsItem[0].idAwayTeam!!)
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

    override fun showDetailHomeTeam(data: List<HomeTeamsItem>) {
        Glide.with(this).load(data[0].strTeamBadge).apply(options).into(detailhomeimg)
    }

    override fun showDetailAwayTeam(data: List<AwayTeamsItem>) {
        Glide.with(this).load(data[0].strTeamBadge).apply(options).into(detailawayimg)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    private fun favoriteState() {
        database.use {
            val result = select(FavoriteMatch.TABLE_FAVORITE_MATCH)
                .whereArgs(
                    "(EVENT_ID = {id})",
                    "id" to id
                )
            val favorite = result.parseList(classParser<FavoriteMatch>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(
                    FavoriteMatch.TABLE_FAVORITE_MATCH,
                    FavoriteMatch.EVENT_ID to eventsItem[0].idEvent,
                    FavoriteMatch.TEAM_HOME to eventsItem[0].strHomeTeam,
                    FavoriteMatch.TEAM_HOME_SCORE to eventsItem[0].intHomeScore,
                    FavoriteMatch.TEAM_AWAY to eventsItem[0].strAwayTeam,
                    FavoriteMatch.TEAM_AWAY_SCORE to eventsItem[0].intAwayScore,
                    FavoriteMatch.EVENT_DATE to eventsItem[0].dateEvent,
                    FavoriteMatch.EVENT_TIME to eventsItem[0].strTime
                )
            }
            snackbar(progressBar, R.string.add_fav).show()
        } catch (e: SQLiteConstraintException) {
            snackbar(progressBar, e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(
                    FavoriteMatch.TABLE_FAVORITE_MATCH, "(EVENT_ID = {id})",
                    "id" to id
                )
            }
            snackbar(progressBar, R.string.remove_fav).show()
        } catch (e: SQLiteConstraintException) {
            snackbar(progressBar, e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }

    override fun onAttachView() {
        detailMatchPresenter.onAttach(this)
    }

    override fun onDettachView() {
        detailMatchPresenter.onDettach()
    }

    override fun onStart() {
        super.onStart()
        onAttachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        onDettachView()
    }
}
