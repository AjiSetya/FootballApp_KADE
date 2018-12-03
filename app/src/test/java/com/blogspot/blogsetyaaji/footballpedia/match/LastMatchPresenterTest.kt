package com.blogspot.blogsetyaaji.footballpedia.match

import com.blogspot.blogsetyaaji.footballpedia.TestContextProvider
import com.blogspot.blogsetyaaji.footballpedia.api.ApiRepository
import com.blogspot.blogsetyaaji.footballpedia.api.TheSportDBApi
import com.blogspot.blogsetyaaji.footballpedia.match.lastmatch.LastMatchPresenter
import com.blogspot.blogsetyaaji.footballpedia.match.lastmatch.LastMatchView
import com.blogspot.blogsetyaaji.footballpedia.model.match.EventsItem
import com.blogspot.blogsetyaaji.footballpedia.model.match.MatchResponse
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class LastMatchPresenterTest {
    @Mock
    private
    lateinit var view: LastMatchView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: LastMatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = LastMatchPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testGetPrevList() {
        val events: MutableList<EventsItem> = mutableListOf()
        val response = MatchResponse(events)
        val league = "4328"
        val sportDbApi = Mockito.mock(TheSportDBApi::class.java)

        Mockito.`when`(
            gson.fromJson(
                apiRepository
                    .doRequest(sportDbApi.getNextMatch(league)),
                MatchResponse::class.java
            )
        ).thenReturn(response)

        presenter.getPrevMatchList(league)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showPrevMatchList(events)
        Mockito.verify(view).hideLoading()
    }
}