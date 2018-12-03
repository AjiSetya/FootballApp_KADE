package com.blogspot.blogsetyaaji.footballpedia.dblocal


// class model pada database sqlite
class FavoriteMatch(val id: Long?, val eventId: String?,
                    val teamHome: String?, val teamHomeScore: String?,
                    val teamAway: String?, val teamAwayScore: String?,
                    val eventDate: String?, val eventTime: String?) {

    // variable yang akan menjadi tag
    companion object {
        const val TABLE_FAVORITE_MATCH: String = "TABLE_FAVORITE_MATCH"
        const val ID_FAVORITE: String = "ID_FAVORITE"
        const val EVENT_ID: String = "EVENT_ID"
        const val TEAM_HOME: String = "TEAM_HOME"
        const val TEAM_HOME_SCORE: String = "TEAM_HOME_SCORE"
        const val TEAM_AWAY: String = "TEAM_AWAY"
        const val TEAM_AWAY_SCORE: String = "TEAM_AWAY_SCORE"
        const val EVENT_DATE: String = "EVENT_DATE"
        const val EVENT_TIME: String = "EVENT_TIME"
    }
}