package com.blogspot.blogsetyaaji.footballpedia.match.searchmatch

import com.blogspot.blogsetyaaji.footballmatchschedule.util.CoroutineContextProvider
import com.blogspot.blogsetyaaji.footballpedia.api.ApiRepository
import com.blogspot.blogsetyaaji.footballpedia.api.TheSportDBApi
import com.blogspot.blogsetyaaji.footballpedia.base.BasePresenter
import com.blogspot.blogsetyaaji.footballpedia.model.searchmatch.ResponseSearch
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class SearchMatchPresenter(
    private var view: SearchMatchView? = null,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) : BasePresenter<SearchMatchView> {

    fun getSearchMatchList(name: String) {
        view?.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getSearchMatch(name)),
                    ResponseSearch::class.java
                )
            }
            view?.showSearchMatchList(data.await().events)
            view?.hideLoading()
        }
    }

    override fun onAttach(view: SearchMatchView) {
        this.view = view
    }

    override fun onDettach() {
        view = null
    }
}