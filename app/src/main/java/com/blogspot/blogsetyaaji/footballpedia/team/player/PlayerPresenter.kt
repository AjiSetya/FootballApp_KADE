package com.blogspot.blogsetyaaji.footballpedia.team.player

import com.blogspot.blogsetyaaji.footballmatchschedule.util.CoroutineContextProvider
import com.blogspot.blogsetyaaji.footballpedia.api.ApiRepository
import com.blogspot.blogsetyaaji.footballpedia.api.TheSportDBApi
import com.blogspot.blogsetyaaji.footballpedia.base.BasePresenter
import com.blogspot.blogsetyaaji.footballpedia.model.player.ResponsePlayer
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class PlayerPresenter(
    private var view: PlayerView? = null,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) : BasePresenter<PlayerView> {

    fun getListPlayer(team: String) {
        view?.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getAllPlayerInTeam(team)),
                    ResponsePlayer::class.java
                )
            }
            view?.showPlayerList(data.await().player)
            view?.hideLoading()
        }
    }

    override fun onAttach(view: PlayerView) {
        this.view = view
    }

    override fun onDettach() {
        view = null
    }
}