package com.blogspot.blogsetyaaji.footballpedia.team.detailteam

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import com.blogspot.blogsetyaaji.footballpedia.Helper
import com.blogspot.blogsetyaaji.footballpedia.R
import com.blogspot.blogsetyaaji.footballpedia.adapter.MyTabAdapter
import com.blogspot.blogsetyaaji.footballpedia.api.ApiRepository
import com.blogspot.blogsetyaaji.footballpedia.dblocal.FavoriteTeam
import com.blogspot.blogsetyaaji.footballpedia.dblocal.database
import com.blogspot.blogsetyaaji.footballpedia.model.hometeam.HomeTeamsItem
import com.blogspot.blogsetyaaji.footballpedia.team.player.PlayerFragment
import com.blogspot.blogsetyaaji.footballpedia.team.teamoverview.TeamOverviewFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail_match.*
import kotlinx.android.synthetic.main.activity_detail_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar

class DetailTeamActivity : AppCompatActivity(), DetailTeamView {

    private lateinit var detailTeamPresenter: DetailTeamPresenter
    private lateinit var teamItem: List<HomeTeamsItem>
    private lateinit var id: String
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)
        title = ""
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val helper = Helper()
        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (Math.abs(verticalOffset) == appBarLayout.totalScrollRange) {
                helper.changeStatusbarColor(this, R.color.colorPrimary)
            } else if (verticalOffset == 0) {
                helper.changeStatusBarColor(this, R.color.colorPrimary)
            }
        })

        val tabAdapter = MyTabAdapter(this.supportFragmentManager)
        tabAdapter.addFragment(TeamOverviewFragment(), getString(R.string.overview))
        tabAdapter.addFragment(PlayerFragment(), getString(R.string.player))
        container.adapter = tabAdapter
        tabs.setupWithViewPager(container)

        id = intent.getStringExtra("idTeam")
        favoriteTeamState()

        val apiRepository = ApiRepository()
        val gson = Gson()
        detailTeamPresenter = DetailTeamPresenter(this, apiRepository, gson)

        detailTeamPresenter.getDetailTeam(id)
    }

    fun getIdTeam(): String {
        return id
    }

    override fun showLoading() {
        parentHeadDetailTeam.visibility = GONE
    }

    override fun hideLoading() {
        parentHeadDetailTeam.visibility = VISIBLE
    }

    override fun showDetailTeam(data: List<HomeTeamsItem>) {
        teamItem = data
        namadetailteam.text = teamItem[0].strTeam
        thndetailteam.text = teamItem[0].intFormedYear
        lapangandetailteam.text = teamItem[0].strStadium

        val options = RequestOptions()
            .fitCenter()
            .error(R.drawable.ic_broken_image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)

        Glide.with(this).load(teamItem[0].strTeamBadge).apply(options).into(imgdetailteam)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavoriteTeam()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_to_favorite -> {
                if (isFavorite) removeFromFavoriteTeam() else addToFavoriteTeam()

                isFavorite = !isFavorite
                setFavoriteTeam()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun favoriteTeamState() {
        database.use {
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
                .whereArgs(
                    "(TEAM_ID = {id})",
                    "id" to id
                )
            val favorite = result.parseList(classParser<FavoriteTeam>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun addToFavoriteTeam() {
        try {
            database.use {
                insert(
                    FavoriteTeam.TABLE_FAVORITE_TEAM,
                    FavoriteTeam.TEAM_ID to teamItem[0].idTeam,
                    FavoriteTeam.TEAM_NAME to teamItem[0].strTeam,
                    FavoriteTeam.TEAM_IMAGE to teamItem[0].strTeamBadge
                )
            }
            snackbar(imgdetailteam, R.string.add_fav).show()
        } catch (e: SQLiteConstraintException) {
            snackbar(imgdetailteam, e.localizedMessage).show()
        }
    }

    private fun removeFromFavoriteTeam() {
        try {
            database.use {
                delete(
                    FavoriteTeam.TABLE_FAVORITE_TEAM, "(TEAM_ID = {id})",
                    "id" to id
                )
            }
            snackbar(imgdetailteam, R.string.remove_fav).show()
        } catch (e: SQLiteConstraintException) {
            snackbar(imgdetailteam, e.localizedMessage).show()
        }
    }

    private fun setFavoriteTeam() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }

    override fun onAttachView() {
        detailTeamPresenter.onAttach(this)
    }

    override fun onDettachView() {
        detailTeamPresenter.onDettach()
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
