package com.blogspot.blogsetyaaji.footballpedia.match.nextmatch

import com.blogspot.blogsetyaaji.footballpedia.base.BaseView
import com.blogspot.blogsetyaaji.footballpedia.model.match.EventsItem

interface NextMatchView : BaseView {
    fun showLoading()
    fun hideLoading()
    fun showNextMatchList(data : List<EventsItem>)
}