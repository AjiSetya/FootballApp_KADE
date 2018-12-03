package com.blogspot.blogsetyaaji.footballpedia

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.blogspot.blogsetyaaji.footballpedia.favorite.FavoriteFragment
import com.blogspot.blogsetyaaji.footballpedia.match.MatchFragment
import com.blogspot.blogsetyaaji.footballpedia.team.TeamFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var fragment: Fragment

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_match -> {
                fragment = MatchFragment()
                showFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_team -> {
                fragment = TeamFragment()
                showFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_favorites -> {
                fragment = FavoriteFragment()
                showFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.maincontainer, fragment).commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getSupportActionBar()?.setElevation(0F)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        fragment = MatchFragment()
        showFragment(fragment)
    }
}
