package com.blogspot.blogsetyaaji.footballpedia.team.detailplayer

import com.blogspot.blogsetyaaji.footballmatchschedule.util.CoroutineContextProvider
import com.blogspot.blogsetyaaji.footballpedia.api.ApiRepository
import com.blogspot.blogsetyaaji.footballpedia.api.TheSportDBApi
import com.blogspot.blogsetyaaji.footballpedia.base.BasePresenter
import com.blogspot.blogsetyaaji.footballpedia.model.detailplayer.ResponseDetailPlayer
import com.blogspot.blogsetyaaji.footballpedia.model.player.ResponsePlayer
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class DetailPlayerPresenter(
    private var view: DetailPlayerView? = null,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) : BasePresenter<DetailPlayerView> {

    fun getDetailPlayer(idPlayer: String) {
        view?.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getDetailPlayer(idPlayer)),
                    ResponseDetailPlayer::class.java
                )
            }
            view?.showDetailPlayer(data.await().players)
            view?.hideLoading()
        }
    }

    override fun onAttach(view: DetailPlayerView) {
        this.view = view
    }

    override fun onDettach() {
        view = null
    }
}