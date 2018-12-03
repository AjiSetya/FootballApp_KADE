package com.blogspot.blogsetyaaji.footballpedia.team

import com.blogspot.blogsetyaaji.footballpedia.base.BaseView
import com.blogspot.blogsetyaaji.footballpedia.model.hometeam.HomeTeamsItem

interface TeamView : BaseView {
    fun showLoading()
    fun hideLoading()
    fun showListTeam(data : List<HomeTeamsItem>)
}