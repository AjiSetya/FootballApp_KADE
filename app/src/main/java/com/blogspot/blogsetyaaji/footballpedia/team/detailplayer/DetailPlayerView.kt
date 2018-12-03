package com.blogspot.blogsetyaaji.footballpedia.team.detailplayer

import com.blogspot.blogsetyaaji.footballpedia.base.BaseView
import com.blogspot.blogsetyaaji.footballpedia.model.detailplayer.PlayersItem

interface DetailPlayerView : BaseView {
    fun showLoading()
    fun hideLoading()
    fun showDetailPlayer(data: List<PlayersItem>)
}