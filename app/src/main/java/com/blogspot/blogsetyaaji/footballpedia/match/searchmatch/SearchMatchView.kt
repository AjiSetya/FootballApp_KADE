package com.blogspot.blogsetyaaji.footballpedia.match.searchmatch

import com.blogspot.blogsetyaaji.footballpedia.base.BaseView
import com.blogspot.blogsetyaaji.footballpedia.model.searchmatch.EventsItem

interface SearchMatchView : BaseView {
    fun showLoading()
    fun hideLoading()
    fun showSearchMatchList(data : List<EventsItem>)
}