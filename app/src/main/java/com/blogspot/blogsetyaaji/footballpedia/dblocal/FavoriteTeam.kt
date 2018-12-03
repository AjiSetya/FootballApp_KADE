package com.blogspot.blogsetyaaji.footballpedia.dblocal


// class model pada database sqlite
class FavoriteTeam(
    val id: Long?, val teamId: String?,
    val teamName: String?, val teamImage: String?
) {

    // variable yang akan menjadi tag
    companion object {
        const val TABLE_FAVORITE_TEAM: String = "TABLE_FAVORITE_TEAM"
        const val ID_FAVORITE_TEAM: String = "ID_FAVORITE_TEAM"
        const val TEAM_ID: String = "TEAM_ID"
        const val TEAM_NAME: String = "TEAM_NAME"
        const val TEAM_IMAGE: String = "TEAM_IMAGE"
    }
}