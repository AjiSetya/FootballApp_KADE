package com.blogspot.blogsetyaaji.footballpedia.match.searchmatch

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import com.blogspot.blogsetyaaji.footballmatchschedule.util.invisible
import com.blogspot.blogsetyaaji.footballmatchschedule.util.visible
import com.blogspot.blogsetyaaji.footballpedia.R
import com.blogspot.blogsetyaaji.footballpedia.adapter.SearchMatchAdapter
import com.blogspot.blogsetyaaji.footballpedia.api.ApiRepository
import com.blogspot.blogsetyaaji.footballpedia.match.detailmatch.DetailMatchActivity
import com.blogspot.blogsetyaaji.footballpedia.model.searchmatch.EventsItem
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_search_match.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh

class SearchMatchActivity : AppCompatActivity(), SearchMatchView {

    private val events: MutableList<EventsItem> = mutableListOf()
    private lateinit var adapterSearch: SearchMatchAdapter
    private lateinit var searchMatchPresenter: SearchMatchPresenter
    private lateinit var nameSearch: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_match)
        setTitle(R.string.search_matches)

        smatchswipe.setColorSchemeColors(
            Color.MAGENTA,
            Color.GREEN,
            Color.YELLOW,
            Color.RED
        )

        slv_match.layoutManager = LinearLayoutManager(this)
        adapterSearch = SearchMatchAdapter(0, events) {
            startActivity<DetailMatchActivity>("idEvent" to it.idEvent)
        }
        slv_match.adapter = adapterSearch

        nameSearch = intent.getStringExtra("query")
        val apiRepository = ApiRepository()
        val gson = Gson()
        searchMatchPresenter = SearchMatchPresenter(this, apiRepository, gson)

        smatchswipe.onRefresh {
            searchMatchPresenter.getSearchMatchList(nameSearch)
        }

        searchMatchPresenter.getSearchMatchList(nameSearch)
    }

    override fun showLoading() {
        smatchProgress.visible()
//        slv_match.invisible()
    }

    override fun hideLoading() {
        smatchProgress.invisible()
//        slv_match.visible()
    }

    override fun showSearchMatchList(data: List<EventsItem>) {
        smatchswipe.isRefreshing = false
        events.clear()
        events.addAll(data)
        adapterSearch.notifyDataSetChanged()
    }

    override fun onAttachView() {
        searchMatchPresenter.onAttach(this)
    }

    override fun onDettachView() {
        searchMatchPresenter.onDettach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search_match, menu)
        val searchView = menu?.findItem(R.id.search_match)?.actionView as SearchView?
        searchView?.queryHint = getString(R.string.search_matches)
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                searchMatchPresenter.getSearchMatchList(p0.toString())
                return false
            }
        })
        return true
    }

    override fun onStart() {
        super.onStart()
        onAttachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        onDettachView()
    }
}
