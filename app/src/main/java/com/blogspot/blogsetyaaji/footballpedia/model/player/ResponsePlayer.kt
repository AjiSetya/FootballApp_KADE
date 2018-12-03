package com.blogspot.blogsetyaaji.footballpedia.model.player

import com.google.gson.annotations.SerializedName

data class ResponsePlayer(

	@field:SerializedName("player")
	val player: List<PlayerItem>
)