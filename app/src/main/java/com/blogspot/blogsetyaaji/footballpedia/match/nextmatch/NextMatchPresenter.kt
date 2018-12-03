package com.blogspot.blogsetyaaji.footballpedia.match.nextmatch

import com.blogspot.blogsetyaaji.footballmatchschedule.util.CoroutineContextProvider
import com.blogspot.blogsetyaaji.footballpedia.api.ApiRepository
import com.blogspot.blogsetyaaji.footballpedia.api.TheSportDBApi
import com.blogspot.blogsetyaaji.footballpedia.base.BasePresenter
import com.blogspot.blogsetyaaji.footballpedia.model.match.MatchResponse
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class NextMatchPresenter(
    private var view: NextMatchView? = null,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()) : BasePresenter<NextMatchView> {

    fun getNextMatchList(league: String) {
        view?.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getNextMatch(league)),
                        MatchResponse::class.java
                )
            }
            view?.showNextMatchList(data.await().events)
            view?.hideLoading()
        }
    }

    override fun onAttach(view: NextMatchView) {
        this.view = view
    }

    override fun onDettach() {
        view = null
    }
}