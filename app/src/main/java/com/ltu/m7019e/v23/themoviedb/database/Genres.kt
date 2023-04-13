package com.ltu.m7019e.v23.themoviedb.database
import android.util.Log
import com.ltu.m7019e.v23.themoviedb.model.Genre
import com.ltu.m7019e.v23.themoviedb.model.Movie
class Genres {
    var list = mutableListOf<Pair<String, Int>>()


    fun addGenres(genres: List<Genre>): MutableList<Pair<String, Int>> {
        val tempList = mutableListOf<Pair<String, Int>>()
        genres.forEach { apiGenre ->
            val pair = Pair(apiGenre.name ?: "", apiGenre.id ?: 0)
            tempList.add(pair)
        }
        return tempList
    }

    init{

    }

}