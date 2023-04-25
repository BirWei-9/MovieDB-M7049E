package com.ltu.m7019e.v23.themoviedb.model
import com.google.gson.annotations.SerializedName


data class Genre(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    var movieList: List<Movie>? = null
)
