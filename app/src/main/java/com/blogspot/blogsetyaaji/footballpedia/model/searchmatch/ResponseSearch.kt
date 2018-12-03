package com.blogspot.blogsetyaaji.footballpedia.model.searchmatch

import com.google.gson.annotations.SerializedName

data class ResponseSearch(

	@field:SerializedName("event")
	val events: List<EventsItem>
)