package com.ltu.m7019e.v23.themoviedb.model

data class Movie(
        var title: String,
        var posterPath: String,
        var releaseDate: String,
        var overview: String,
        var genres: List<String>,
        var imdb_link: String
)