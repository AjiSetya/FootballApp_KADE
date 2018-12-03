package com.blogspot.blogsetyaaji.footballpedia.team.teamoverview

import com.blogspot.blogsetyaaji.footballpedia.base.BaseView
import com.blogspot.blogsetyaaji.footballpedia.model.awayteam.AwayTeamsItem
import com.blogspot.blogsetyaaji.footballpedia.model.hometeam.HomeTeamsItem
import com.blogspot.blogsetyaaji.footballpedia.model.match.EventsItem

interface TeamOvView  : BaseView {
    fun showLoading()
    fun hideLoading()
    fun showOverViewTeam(data: List<HomeTeamsItem>)
}