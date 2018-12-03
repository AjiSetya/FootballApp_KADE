package com.blogspot.blogsetyaaji.footballpedia.match.lastmatch

import com.blogspot.blogsetyaaji.footballmatchschedule.util.CoroutineContextProvider
import com.blogspot.blogsetyaaji.footballpedia.api.ApiRepository
import com.blogspot.blogsetyaaji.footballpedia.api.TheSportDBApi
import com.blogspot.blogsetyaaji.footballpedia.base.BasePresenter
import com.blogspot.blogsetyaaji.footballpedia.model.match.MatchResponse
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class LastMatchPresenter(
    private var view: LastMatchView? = null,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()) : BasePresenter<LastMatchView> {

    fun getPrevMatchList(league: String) {
        view?.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPrevMatch(league)),
                        MatchResponse::class.java
                )
            }
            view?.showPrevMatchList(data.await().events)
            view?.hideLoading()
        }
    }

    override fun onAttach(view: LastMatchView) {
        this.view = view
    }

    override fun onDettach() {
        view = null
    }
}