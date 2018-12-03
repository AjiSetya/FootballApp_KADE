package com.blogspot.blogsetyaaji.footballpedia.team.teamoverview

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

class TeamOvPresenter(
    private var view: TeamOvView? = null,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) : BasePresenter<TeamOvView> {

    fun getOverViewTeam(idTeam: String) {
        view?.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getDetailHomeTeam(idTeam)),
                    DetailHomeTeamResponse::class.java
                )
            }
            view?.showOverViewTeam(data.await().teams)
            view?.hideLoading()
        }
    }

    override fun onAttach(view: TeamOvView) {
        this.view = view
    }

    override fun onDettach() {
        view = null
    }
}