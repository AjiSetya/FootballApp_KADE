package com.blogspot.blogsetyaaji.footballpedia.team.player


import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blogspot.blogsetyaaji.footballmatchschedule.util.invisible
import com.blogspot.blogsetyaaji.footballmatchschedule.util.visible
import com.blogspot.blogsetyaaji.footballpedia.R
import com.blogspot.blogsetyaaji.footballpedia.adapter.PlayerAdapter
import com.blogspot.blogsetyaaji.footballpedia.api.ApiRepository
import com.blogspot.blogsetyaaji.footballpedia.model.player.PlayerItem
import com.blogspot.blogsetyaaji.footballpedia.team.detailplayer.DetailPlayerActivity
import com.blogspot.blogsetyaaji.footballpedia.team.detailteam.DetailTeamActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_player.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class PlayerFragment : Fragment(), PlayerView {

    private val player: MutableList<PlayerItem> = mutableListOf()
    private lateinit var adapterPlayer: PlayerAdapter
    private lateinit var playerPresenter: PlayerPresenter
    private lateinit var team: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        team = (activity as DetailTeamActivity).getIdTeam()

        swipePlayer.setColorSchemeColors(
            Color.MAGENTA,
            Color.GREEN,
            Color.YELLOW,
            Color.RED
        )

        lv_player.layoutManager = LinearLayoutManager(activity)
        adapterPlayer = PlayerAdapter(player) {
            startActivity<DetailPlayerActivity>("idPlayer" to it.idPlayer)
        }
        lv_player.adapter = adapterPlayer

        val apiRepository = ApiRepository()
        val gson = Gson()
        playerPresenter = PlayerPresenter(this@PlayerFragment, apiRepository, gson)

        swipePlayer.onRefresh {
            playerPresenter.getListPlayer(team)
        }

        playerPresenter.getListPlayer(team)
    }

    override fun showLoading() {
        playerProgress.visible()
    }

    override fun hideLoading() {
        playerProgress.invisible()
    }

    override fun showPlayerList(data: List<PlayerItem>) {
        swipePlayer.isRefreshing = false
        player.clear()
        player.addAll(data)
        adapterPlayer.notifyDataSetChanged()
    }

    override fun onAttachView() {
        playerPresenter.onAttach(this)
    }

    override fun onDettachView() {
        playerPresenter.onDettach()
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
