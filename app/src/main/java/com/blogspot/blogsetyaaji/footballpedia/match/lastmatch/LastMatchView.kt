package com.blogspot.blogsetyaaji.footballpedia.match.lastmatch

import com.blogspot.blogsetyaaji.footballpedia.base.BaseView
import com.blogspot.blogsetyaaji.footballpedia.model.match.EventsItem

interface LastMatchView : BaseView {
    fun showLoading()
    fun hideLoading()
    fun showPrevMatchList(data : List<EventsItem>)
}