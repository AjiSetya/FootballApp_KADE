package com.blogspot.blogsetyaaji.footballpedia.model.awayteam

import com.google.gson.annotations.SerializedName

data class DetailAwayTeamResponse(

	@field:SerializedName("teams")
	val teams: List<AwayTeamsItem>
)