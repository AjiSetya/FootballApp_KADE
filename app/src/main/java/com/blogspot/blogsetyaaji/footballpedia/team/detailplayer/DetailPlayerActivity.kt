package com.blogspot.blogsetyaaji.footballpedia.team.detailplayer

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.blogspot.blogsetyaaji.footballmatchschedule.util.invisible
import com.blogspot.blogsetyaaji.footballmatchschedule.util.visible
import com.blogspot.blogsetyaaji.footballpedia.Helper
import com.blogspot.blogsetyaaji.footballpedia.R
import com.blogspot.blogsetyaaji.footballpedia.api.ApiRepository
import com.blogspot.blogsetyaaji.footballpedia.model.detailplayer.PlayersItem
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail_player.*
import kotlinx.android.synthetic.main.content_detail_player.*
import org.jetbrains.anko.toast
import android.support.v4.app.NavUtils
import android.view.MenuItem


class DetailPlayerActivity : AppCompatActivity(), DetailPlayerView {

    private lateinit var detailPlayerPresenter: DetailPlayerPresenter
    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_player)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        title = ""

        val helper = Helper()
        app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (Math.abs(verticalOffset) == appBarLayout.totalScrollRange) {
                helper.changeStatusbarColor(this, R.color.colorPrimary)
            } else if (verticalOffset == 0) {
                helper.changeStatusBarColor(this, R.color.transparantheader)
            }
        })

        id = intent.getStringExtra("idPlayer")

        val apiRepository = ApiRepository()
        val gson = Gson()
        detailPlayerPresenter = DetailPlayerPresenter(this, apiRepository, gson)

        detailPlayerPresenter.getDetailPlayer(id)
    }

    override fun showLoading() {
//        parentdetailplayer.invisible()
        pgdetailplayer.visible()
    }

    override fun hideLoading() {
//        parentdetailplayer.visible()
        pgdetailplayer.invisible()
    }

    override fun showDetailPlayer(data: List<PlayersItem>) {
        val options = RequestOptions()
            .fitCenter()
            .error(R.drawable.ic_broken_image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)

        Glide.with(this).load(data[0].strFanart1).apply(options).into(imgdetailplayer)

        toolbar.title = data[0].strPlayer
        dweightplayer.text = data[0].strWeight
        dheightplayer.text = data[0].strHeight
        dplayerposition.text = data[0].strPosition
        dplayerdesc.text = data[0].strDescriptionEN

        Log.d("data_player", data[0].toString())
    }

    override fun onAttachView() {
        detailPlayerPresenter.onAttach(this)
    }

    override fun onDettachView() {
        detailPlayerPresenter.onDettach()
    }

    override fun onStart() {
        super.onStart()
        onAttachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        onDettachView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
