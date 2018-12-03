package com.blogspot.blogsetyaaji.footballpedia.favorite.favteam


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blogspot.blogsetyaaji.footballpedia.R
import com.blogspot.blogsetyaaji.footballpedia.adapter.FavoriteTeamAdapter
import com.blogspot.blogsetyaaji.footballpedia.dblocal.FavoriteTeam
import com.blogspot.blogsetyaaji.footballpedia.dblocal.database
import com.blogspot.blogsetyaaji.footballpedia.team.detailteam.DetailTeamActivity
import kotlinx.android.synthetic.main.fragment_fav_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh

class FavTeamFragment : Fragment() {

    private var favorites: MutableList<FavoriteTeam> = mutableListOf()
    private lateinit var adapter: FavoriteTeamAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FavoriteTeamAdapter(favorites) {
            ctx.startActivity<DetailTeamActivity>("idTeam" to "${it.teamId}")
        }

        lv_favteam.layoutManager = LinearLayoutManager(activity)
        lv_favteam.adapter = adapter
        showFavorite()
        favTeamSwipe.onRefresh {
            favorites.clear()
            showFavorite()
        }
    }

    private fun showFavorite() {
        context?.database?.use {
            favTeamSwipe.isRefreshing = false
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        favorites.clear()
        showFavorite()
    }
}
