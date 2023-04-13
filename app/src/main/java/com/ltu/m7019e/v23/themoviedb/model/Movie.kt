package com.ltu.m7019e.v23.themoviedb.model

import com.google.gson.annotations.SerializedName

data class Movie(
        @SerializedName("poster_path")
        var posterPath: String,

        @SerializedName("overview")
        var overview: String,

        @SerializedName("release_date")
        var releaseDate: String,

        @SerializedName("genre_ids")
        var genres: List<Int>,

        @SerializedName("id")
        var movieId: Int =0,

        @SerializedName("original_title")
        var title: String,

        @SerializedName("popularity")
        var popularity: Float = 0f,

        @SerializedName("vote_average")
        var voteAverage: Float = 0f,

        var imdb_link: String
)