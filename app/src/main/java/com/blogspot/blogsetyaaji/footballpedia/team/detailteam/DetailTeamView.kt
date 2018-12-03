package com.blogspot.blogsetyaaji.footballpedia.team.detailteam

import com.blogspot.blogsetyaaji.footballpedia.base.BaseView
import com.blogspot.blogsetyaaji.footballpedia.model.hometeam.HomeTeamsItem

interface DetailTeamView  : BaseView {
    fun showLoading()
    fun hideLoading()
    fun showDetailTeam(data: List<HomeTeamsItem>)
}