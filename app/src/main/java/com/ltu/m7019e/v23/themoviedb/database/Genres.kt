package com.ltu.m7019e.v23.themoviedb.database
import com.ltu.m7019e.v23.themoviedb.model.Movie
class Genres {
    val list = mutableListOf<String>()

    val comedy_movies = mutableListOf<Movie>()



    init{
        list.add("Comedy")
        list.add("Sci-Fi")
        list.add("Scary")



        //todo remove
        val movies = Movies()
        for (movie in movies.list) {
            for (genre in movie.genres) {
                when (genre) {
                    "Comedy" -> comedy_movies.add(movie)

                }
            }
        }
    }
}