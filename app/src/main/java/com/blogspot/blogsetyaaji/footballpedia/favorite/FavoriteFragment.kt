package com.blogspot.blogsetyaaji.footballpedia.favorite


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blogspot.blogsetyaaji.footballpedia.R
import com.blogspot.blogsetyaaji.footballpedia.adapter.MyTabAdapter
import com.blogspot.blogsetyaaji.footballpedia.favorite.favmatch.FavMatchFragment
import com.blogspot.blogsetyaaji.footballpedia.favorite.favteam.FavTeamFragment
import kotlinx.android.synthetic.main.fragment_fvorite.*

class FavoriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fvorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabAdapter = MyTabAdapter(activity?.supportFragmentManager)
        tabAdapter.addFragment(FavMatchFragment(), getString(R.string.title_match))
        tabAdapter.addFragment(FavTeamFragment(), getString(R.string.title_team))
        pagerTeam.adapter = tabAdapter
        tabTeam.setupWithViewPager(pagerTeam)
        setHasOptionsMenu(true)
    }
}
