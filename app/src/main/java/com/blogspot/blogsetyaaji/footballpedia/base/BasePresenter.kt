package com.blogspot.blogsetyaaji.footballpedia.base

interface BasePresenter<in T : BaseView> {

    fun onAttach(view : T)
    fun onDettach()
}