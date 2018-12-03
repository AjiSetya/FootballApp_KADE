package com.blogspot.blogsetyaaji.footballpedia.match.detailmatch

import com.blogspot.blogsetyaaji.footballpedia.base.BaseView
import com.blogspot.blogsetyaaji.footballpedia.model.awayteam.AwayTeamsItem
import com.blogspot.blogsetyaaji.footballpedia.model.hometeam.HomeTeamsItem
import com.blogspot.blogsetyaaji.footballpedia.model.match.EventsItem

interface DetailMatchView  : BaseView {
    fun showLoading()
    fun hideLoading()
    fun showDetailMatch(data: List<EventsItem>)
    fun showDetailHomeTeam(data: List<HomeTeamsItem>)
    fun showDetailAwayTeam(data: List<AwayTeamsItem>)
}