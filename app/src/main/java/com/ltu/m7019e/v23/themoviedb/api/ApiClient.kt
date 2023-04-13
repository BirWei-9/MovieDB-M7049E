package com.ltu.m7019e.v23.themoviedb.api
import android.util.Log
import com.ltu.m7019e.v23.themoviedb.api.response.ApiGenreResponse
import com.ltu.m7019e.v23.themoviedb.api.response.ApiPopMoviesResponse
import com.ltu.m7019e.v23.themoviedb.model.Genre
import com.ltu.m7019e.v23.themoviedb.model.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    private val API_KEY = "fa6841d3773a554209aa8b2a64ea2eee"
    private val PAGES = 2

    private val apiService: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }


    fun getPopMovies(page: Int, callback: (List<Movie>?, Throwable?) -> Unit){
        apiService.getPopularMovies(page = PAGES, ApiKey = API_KEY).enqueue(object : Callback<ApiPopMoviesResponse>{
            override fun onResponse(call: Call<ApiPopMoviesResponse>, response: Response<ApiPopMoviesResponse>) {
                if (response.isSuccessful){
                    val apiPopMovieResp = response.body()
                    Log.d("pop_movie_list", "api resp" + apiPopMovieResp)
                    val movieList = apiPopMovieResp?.popMovies?.map { movieObj ->
                        movieObj?.toMovie()
                    }
                    callback(movieList as List<Movie>?, null)
                }else{
                    callback(null, Throwable(response.message()))
                }

            }

            override fun onFailure(call: Call<ApiPopMoviesResponse>, t: Throwable) {
                callback(null, t)
            }

        })
    }
    fun getGenres(callback: (List<Genre>?, Throwable?) -> Unit){
        apiService.getMovieGenreList(ApiKey = API_KEY).enqueue(object : Callback<ApiGenreResponse>{
            override fun onResponse(call: Call<ApiGenreResponse>, response: Response<ApiGenreResponse>) {
                Log.d("ApiGenreResponse", "resp" + response)
                if (response.isSuccessful){
                    val apiGenreResp = response.body()
                    Log.d("genre_list", "api resp" + apiGenreResp)
                    val genreList = apiGenreResp?.genres?.map { genreObj ->
                        genreObj?.toGenre()
                    }
                    callback(genreList as List<Genre>?, null)
                }else{
                    callback(null, Throwable(response.message()))
                }

            }

            override fun onFailure(call: Call<ApiGenreResponse>, t: Throwable) {
                callback(null, t)
            }

        })
    }
    private fun Genre.toGenre(): Genre? {
        return if(this.id != null){
            Genre(
                id = this.id ?: 0,
                name = this.name ?: ""
            )
        } else{
            null
        }
    }


    private fun Movie.toMovie(): Movie? {
        return if(this.movieId != null){
            Movie(
                posterPath = this.posterPath ?: "",
                overview = this.overview ?: "",
                releaseDate = this.releaseDate ?: "",
                genres = this.genres,
                movieId = this.movieId ?: 0,
                title = this.title,
                popularity = this.popularity ?: 0f,
                voteAverage = this.voteAverage ?: 0f,
                imdb_link = ""
            )
        } else{
            null
        }
    }
}