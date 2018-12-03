package com.blogspot.blogsetyaaji.footballpedia.team


import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.AdapterView
import com.blogspot.blogsetyaaji.footballmatchschedule.util.invisible
import com.blogspot.blogsetyaaji.footballmatchschedule.util.visible
import com.blogspot.blogsetyaaji.footballpedia.R
import com.blogspot.blogsetyaaji.footballpedia.adapter.TeamAdapter
import com.blogspot.blogsetyaaji.footballpedia.api.ApiRepository
import com.blogspot.blogsetyaaji.footballpedia.model.hometeam.HomeTeamsItem
import com.blogspot.blogsetyaaji.footballpedia.team.detailteam.DetailTeamActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_team.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class TeamFragment : Fragment(), TeamView {

    private val teams: MutableList<HomeTeamsItem> = mutableListOf()
    private lateinit var teamAdapter: TeamAdapter
    private lateinit var teamPresenter: TeamPresenter
    private lateinit var league: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        league = spinnerTeam.selectedItem.toString()
        league = "4328"

        spinnerTeam.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when (p2) {
                    0 -> league = "4328"
                    1 -> league = "4329"
                    2 -> league = "4331"
                    3 -> league = "4332"
                    4 -> league = "4334"
                    5 -> league = "4335"
                }

                teamPresenter.getAllTeamList(league)
            }

        }

        teamswipe.setColorSchemeColors(
            Color.MAGENTA,
            Color.GREEN,
            Color.YELLOW,
            Color.RED
        )

        lv_team.layoutManager = LinearLayoutManager(activity)
        teamAdapter = TeamAdapter(teams) {
                        startActivity<DetailTeamActivity>("idTeam" to it.idTeam)
        }
        lv_team.adapter = teamAdapter

        val apiRepository = ApiRepository()
        val gson = Gson()
        teamPresenter = TeamPresenter(this@TeamFragment, apiRepository, gson)

        teamswipe.onRefresh {
            teamPresenter.getAllTeamList(league)
        }

        teamPresenter.getAllTeamList(league)
    }

    override fun showLoading() {
        teamProgress.visible()
    }

    override fun hideLoading() {
        teamProgress.invisible()
    }

    override fun showListTeam(data: List<HomeTeamsItem>) {
        teamswipe.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        teamAdapter.notifyDataSetChanged()
    }

    override fun onAttachView() {
        teamPresenter.onAttach(this)
    }

    override fun onDettachView() {
        teamPresenter.onDettach()
    }

    override fun onStart() {
        super.onStart()
        onAttachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        onDettachView()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_search_team, menu)

        val searchView = menu?.findItem(R.id.search_team)?.actionView as SearchView?
        searchView?.queryHint = getString(R.string.search_teams)
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                teamPresenter.getSearchTeam(p0.toString())
                return false
            }
        })

        searchView?.setOnCloseListener {
            teamPresenter.getAllTeamList(league)
            true
        }
    }
}
