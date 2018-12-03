package com.blogspot.blogsetyaaji.footballpedia.model.hometeam

import com.google.gson.annotations.SerializedName

data class DetailHomeTeamResponse(

	@field:SerializedName("teams")
	val teams: List<HomeTeamsItem>
)