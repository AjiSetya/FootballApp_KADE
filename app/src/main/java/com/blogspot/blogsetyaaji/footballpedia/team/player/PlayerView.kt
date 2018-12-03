package com.blogspot.blogsetyaaji.footballpedia.team.player

import com.blogspot.blogsetyaaji.footballpedia.base.BaseView
import com.blogspot.blogsetyaaji.footballpedia.model.player.PlayerItem

interface PlayerView : BaseView {
    fun showLoading()
    fun hideLoading()
    fun showPlayerList(data : List<PlayerItem>)
}