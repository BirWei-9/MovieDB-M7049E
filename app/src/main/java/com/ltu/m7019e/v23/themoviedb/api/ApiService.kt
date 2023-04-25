package com.ltu.m7019e.v23.themoviedb.api
import com.ltu.m7019e.v23.themoviedb.api.response.ApiGenreResponse
import com.ltu.m7019e.v23.themoviedb.api.response.ApiMovieLinkResponse
import com.ltu.m7019e.v23.themoviedb.api.response.ApiPopMoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


public interface ApiService {

        @GET("movie/popular")
        fun getPopularMovies(
            @Query("page") page: Int,
            @Query("api_key") ApiKey: String

        ): Call<ApiPopMoviesResponse>

        @GET("genre/movie/list")
        fun getMovieGenreList(
            @Query("api_key") ApiKey: String,

        ): Call<ApiGenreResponse>

        @GET("movie/{movie_id}")
        fun getMovieImdbLink(
            @Path("movie_id") movieId: Int,
            @Query("api_key") ApiKey: String,

            ): Call<ApiMovieLinkResponse>
}
