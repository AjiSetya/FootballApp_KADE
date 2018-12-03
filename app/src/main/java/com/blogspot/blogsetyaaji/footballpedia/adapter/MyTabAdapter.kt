package com.blogspot.blogsetyaaji.footballpedia.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class MyTabAdapter(fragmentManager: FragmentManager?) : FragmentStatePagerAdapter(fragmentManager) {

    private var fragmentList = arrayListOf<Fragment>()
    private var titleList = arrayListOf<String>()

    override fun getItem(position: Int): Fragment = fragmentList[position]

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? = titleList[position]

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        titleList.add(title)
    }
}
