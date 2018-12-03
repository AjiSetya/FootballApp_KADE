package com.blogspot.blogsetyaaji.footballpedia.team.teamoverview


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blogspot.blogsetyaaji.footballmatchschedule.util.invisible
import com.blogspot.blogsetyaaji.footballmatchschedule.util.visible
import com.blogspot.blogsetyaaji.footballpedia.R
import com.blogspot.blogsetyaaji.footballpedia.api.ApiRepository
import com.blogspot.blogsetyaaji.footballpedia.model.hometeam.HomeTeamsItem
import com.blogspot.blogsetyaaji.footballpedia.team.detailteam.DetailTeamActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_team_overview.*

class TeamOverviewFragment : Fragment(), TeamOvView {

    private lateinit var overViewTeamPresenter: TeamOvPresenter
    private lateinit var id: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        id = (activity as DetailTeamActivity).getIdTeam()

        val apiRepository = ApiRepository()
        val gson = Gson()
        overViewTeamPresenter = TeamOvPresenter(this, apiRepository, gson)

        overViewTeamPresenter.getOverViewTeam(id)
    }

    override fun showLoading() {
        pgOverView.visible()
    }

    override fun hideLoading() {
        pgOverView.invisible()
    }

    override fun showOverViewTeam(data: List<HomeTeamsItem>) {
        overviewdteam.text = data[0].strDescriptionEN
    }

    override fun onAttachView() {
        overViewTeamPresenter.onAttach(this)
    }

    override fun onDettachView() {
        overViewTeamPresenter.onDettach()
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
