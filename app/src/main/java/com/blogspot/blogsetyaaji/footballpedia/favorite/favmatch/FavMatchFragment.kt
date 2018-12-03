package com.blogspot.blogsetyaaji.footballpedia.favorite.favmatch


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.blogspot.blogsetyaaji.footballpedia.R
import com.blogspot.blogsetyaaji.footballpedia.adapter.FavoriteMatchAdapter
import com.blogspot.blogsetyaaji.footballpedia.dblocal.FavoriteMatch
import com.blogspot.blogsetyaaji.footballpedia.dblocal.database
import com.blogspot.blogsetyaaji.footballpedia.match.detailmatch.DetailMatchActivity
import kotlinx.android.synthetic.main.fragment_fav_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh

class FavMatchFragment : Fragment() {

    private var favorites: MutableList<FavoriteMatch> = mutableListOf()
    private lateinit var adapter: FavoriteMatchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FavoriteMatchAdapter(favorites) {
            ctx.startActivity<DetailMatchActivity>("idEvent" to "${it.eventId}")
        }

        lv_favmatch.layoutManager = LinearLayoutManager(activity)
        lv_favmatch.adapter = adapter
        showFavorite()
        favSwipe.onRefresh {
            favorites.clear()
            showFavorite()
        }
    }

    private fun showFavorite() {
        context?.database?.use {
            favSwipe.isRefreshing = false
            val result = select(FavoriteMatch.TABLE_FAVORITE_MATCH)
            val favorite = result.parseList(classParser<FavoriteMatch>())
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
