package com.blogspot.blogsetyaaji.footballpedia.model.match

import com.google.gson.annotations.SerializedName

data class MatchResponse(

	@field:SerializedName("events")
	val events: List<EventsItem>
)