package com.blogspot.blogsetyaaji.footballpedia.team

import com.blogspot.blogsetyaaji.footballmatchschedule.util.CoroutineContextProvider
import com.blogspot.blogsetyaaji.footballpedia.api.ApiRepository
import com.blogspot.blogsetyaaji.footballpedia.api.TheSportDBApi
import com.blogspot.blogsetyaaji.footballpedia.base.BasePresenter
import com.blogspot.blogsetyaaji.footballpedia.model.hometeam.DetailHomeTeamResponse
import com.blogspot.blogsetyaaji.footballpedia.model.match.MatchResponse
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class TeamPresenter(
    private var view: TeamView? = null,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()) : BasePresenter<TeamView> {

    fun getAllTeamList(league: String) {
        view?.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getAllTeam(league)),
                        DetailHomeTeamResponse::class.java
                )
            }
            view?.showListTeam(data.await().teams)
            view?.hideLoading()
        }
    }

    fun getSearchTeam(name: String) {
        view?.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getSearchTeam(name)),
                    DetailHomeTeamResponse::class.java
                )
            }
            view?.showListTeam(data.await().teams)
            view?.hideLoading()
        }
    }

    override fun onAttach(view: TeamView) {
        this.view = view
    }

    override fun onDettach() {
        view = null
    }
}