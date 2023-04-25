package com.ltu.m7019e.v23.themoviedb.api.response
import com.google.gson.annotations.SerializedName
import com.ltu.m7019e.v23.themoviedb.model.Movie

data class ApiMovieLinkResponse(
    @SerializedName("id")
    val movieID: Int? = null,

    @SerializedName("imdb_id")
    val movieLink: String? = null

)
