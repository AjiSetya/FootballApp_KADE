package com.blogspot.blogsetyaaji.footballpedia.model.detailplayer

import com.google.gson.annotations.SerializedName

data class ResponseDetailPlayer(

	@field:SerializedName("players")
	val players: List<PlayersItem>
)