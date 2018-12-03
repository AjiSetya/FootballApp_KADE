package com.blogspot.blogsetyaaji.footballpedia.match.detailmatch

import com.blogspot.blogsetyaaji.footballmatchschedule.util.CoroutineContextProvider
import com.blogspot.blogsetyaaji.footballpedia.api.ApiRepository
import com.blogspot.blogsetyaaji.footballpedia.api.TheSportDBApi
import com.blogspot.blogsetyaaji.footballpedia.base.BasePresenter
import com.blogspot.blogsetyaaji.footballpedia.model.awayteam.DetailAwayTeamResponse
import com.blogspot.blogsetyaaji.footballpedia.model.hometeam.DetailHomeTeamResponse
import com.blogspot.blogsetyaaji.footballpedia.model.match.MatchResponse
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class DetailMatchPresenter(
    private var view: DetailMatchView? = null,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) : BasePresenter<DetailMatchView> {

    fun getDetailMatch(idEvent: String) {
        view?.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getDetailMatch(idEvent)),
                    MatchResponse::class.java
                )
            }
            view?.showDetailMatch(data.await().events)
            view?.hideLoading()
        }
    }

    fun getDetailHomeTeam(idTeam: String) {
        view?.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getDetailHomeTeam(idTeam)),
                    DetailHomeTeamResponse::class.java
                )
            }
            view?.showDetailHomeTeam(data.await().teams)
            view?.hideLoading()

        }
    }

    fun getDetailAwayTeam(idTeam: String) {
        view?.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getDetailAwayTeam(idTeam)),
                    DetailAwayTeamResponse::class.java
                )
            }
            view?.showDetailAwayTeam(data.await().teams)
            view?.hideLoading()
        }
    }

    override fun onAttach(view: DetailMatchView) {
        this.view = view
    }

    override fun onDettach() {
        view = null
    }
}