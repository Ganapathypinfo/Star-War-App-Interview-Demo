package com.assignment.demo.starwarsapp.datamodel.peoples

import com.google.gson.annotations.SerializedName

data class PeopleResponseModel (

	@SerializedName("count") val count : Int,
	@SerializedName("next") val next : String,
	@SerializedName("previous") val previous : String,
	@SerializedName("results") val results : List<Results>
)