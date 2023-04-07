package com.ltu.m7019e.v23.themoviedb.database
import com.ltu.m7019e.v23.themoviedb.model.Movie
class Genres {
    val genres = mutableListOf<String>()

    val comedy_movies = mutableListOf<Movie>()



    init{
        genres.add("Comedy")




        val movies = Movies()
        for (movie in movies.list){
            when (movie.genres){
                "Comedy" -> comedy_movies.add(movie)
            }
        }
    }
}