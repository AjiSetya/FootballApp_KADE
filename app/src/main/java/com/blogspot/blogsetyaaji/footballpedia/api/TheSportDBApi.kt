package com.blogspot.blogsetyaaji.footballpedia.api

import com.blogspot.blogsetyaaji.footballpedia.BuildConfig.BASE_URL
import com.blogspot.blogsetyaaji.footballpedia.BuildConfig.TSDB_API_KEY

object TheSportDBApi {

    fun getNextMatch(idLeague: String?): String =
        BASE_URL + "api/v1/json/$TSDB_API_KEY/eventsnextleague.php?id=" + idLeague

    fun getPrevMatch(idLeague: String?): String =
        BASE_URL + "api/v1/json/$TSDB_API_KEY/eventspastleague.php?id=" + idLeague

    fun getDetailMatch(idEvent: String?): String = BASE_URL + "api/v1/json/$TSDB_API_KEY/lookupevent.php?id=" + idEvent
    fun getDetailHomeTeam(idTeam: String?): String = BASE_URL + "api/v1/json/$TSDB_API_KEY/lookupteam.php?id=" + idTeam
    fun getDetailAwayTeam(idTeam: String?): String = BASE_URL + "api/v1/json/$TSDB_API_KEY/lookupteam.php?id=" + idTeam
    fun getSearchMatch(query: String?): String = BASE_URL + "api/v1/json/$TSDB_API_KEY/searchevents.php?e=" + query
    fun getAllTeam(idLeague: String?): String =
        BASE_URL + "api/v1/json/$TSDB_API_KEY/lookup_all_teams.php?id=" + idLeague

    fun getSearchTeam(query: String?): String = BASE_URL + "api/v1/json/$TSDB_API_KEY/searchteams.php?t=" + query
    fun getAllPlayerInTeam(idTeam: String?): String =
        BASE_URL + "api/v1/json/$TSDB_API_KEY/lookup_all_players.php?id=" + idTeam

    fun getDetailPlayer(idPlayer: String?): String =
        BASE_URL + "api/v1/json/$TSDB_API_KEY/lookupplayer.php?id=" + idPlayer
}