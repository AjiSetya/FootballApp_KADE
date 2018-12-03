package com.blogspot.blogsetyaaji.footballpedia

import com.blogspot.blogsetyaaji.footballpedia.TestContextProvider
import com.blogspot.blogsetyaaji.footballpedia.api.ApiRepository
import com.blogspot.blogsetyaaji.footballpedia.api.TheSportDBApi
import com.blogspot.blogsetyaaji.footballpedia.match.lastmatch.LastMatchPresenter
import com.blogspot.blogsetyaaji.footballpedia.match.lastmatch.LastMatchView
import com.blogspot.blogsetyaaji.footballpedia.model.hometeam.DetailHomeTeamResponse
import com.blogspot.blogsetyaaji.footballpedia.model.hometeam.HomeTeamsItem
import com.blogspot.blogsetyaaji.footballpedia.model.match.EventsItem
import com.blogspot.blogsetyaaji.footballpedia.model.match.MatchResponse
import com.blogspot.blogsetyaaji.footballpedia.team.TeamPresenter
import com.blogspot.blogsetyaaji.footballpedia.team.TeamView
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TeamPresenterTest {
    @Mock
    private
    lateinit var view: TeamView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: TeamPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = TeamPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testGetPrevList() {
        val teamsItem: MutableList<HomeTeamsItem> = mutableListOf()
        val response = DetailHomeTeamResponse(teamsItem)
        val league = "4328"
        val sportDbApi = Mockito.mock(TheSportDBApi::class.java)

        Mockito.`when`(
            gson.fromJson(
                apiRepository
                    .doRequest(sportDbApi.getAllTeam(league)),
                DetailHomeTeamResponse::class.java
            )
        ).thenReturn(response)

        presenter.getAllTeamList(league)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showListTeam(teamsItem)
        Mockito.verify(view).hideLoading()
    }
}