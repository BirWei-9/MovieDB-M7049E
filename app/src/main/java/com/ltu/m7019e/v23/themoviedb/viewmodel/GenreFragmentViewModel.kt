package com.ltu.m7019e.v23.themoviedb.viewmodel
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ltu.m7019e.v23.themoviedb.api.ApiClient
import com.ltu.m7019e.v23.themoviedb.model.Genre
import com.ltu.m7019e.v23.themoviedb.model.Movie

class GenreFragmentViewModel(application: Application) : AndroidViewModel(application) {
    val IMDB_BASE_LINK = "https://www.imdb.com/title/"

    private val _movieList = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>>
        get() {
            return _movieList
        }
    private val _genreList = MutableLiveData<List<Genre>>()
    val genreList: LiveData<List<Genre>>
        get() {
            return _genreList
        }

    private val _navigateToMovieDetail = MutableLiveData<Movie?>()
    val navigateToMovieDetail: MutableLiveData<Movie?>
        get() {
            return _navigateToMovieDetail
        }


    init {

        getMoviesApiCall { movieList ->
            Log.d("inside_movie_id_loop", "movie list after api: " + movieList)
            movieList.forEach {movieInList->
                getMovieLinkApiCall(movieInList.movieId){ link ->
                    if (link != null) {
                        movieInList.imdb_link = IMDB_BASE_LINK + link
                    }
                }
                _movieList.postValue(movieList)
                Log.d("after_loop", "movie list after api: " + movieList)

                getGenresApiCall { genresList ->
                    var listOfGenre = mutableListOf<Genre>()

                    for (genre in genresList) {
                        val movie_genres = movieList.filter { it.genres.contains(genre.id) }
                        if (!movie_genres.isEmpty()) {
                            listOfGenre.add(genre)
                            genre.movieList = movie_genres
                        }
                    }
                    _genreList.postValue(listOfGenre)
                }
            }
        }
    }


    fun onGenreMovieClicked(movie: Movie) {
        _navigateToMovieDetail.value = movie
    }

    fun onMovieDetailNavigated() {
        _navigateToMovieDetail.value = null;
    }





    private fun getMoviesApiCall(callback: (List<Movie>) -> Unit) {
        val movieApiClient = ApiClient()
        movieApiClient.getPopMovies(page = 3) { movieList, error ->
            if (error != null ) {
                Log.d("error_movie_list", "movie list error : " + error)
            } else if (movieList != null) {
                Log.d("movie_list", "add movies to list: " + movieList)
                callback(movieList)
            }
        }
    }

    private fun getGenresApiCall(callback: (List<Genre>) -> Unit) {
        val genreApiClient = ApiClient()
        genreApiClient.getGenres() { genreList, error ->
            if (error != null ) {
                Log.d("error_movie_list", "movie list error : " + error)
            } else if (genreList != null) {
                Log.d("movie_list", "add movies to list: " + genreList)
                callback(genreList)
            }
        }
    }
    private fun getMovieLinkApiCall(id: Int, callback: (String?) -> Unit) {
        val movieApiClient = ApiClient()
        movieApiClient.getMovieLink(id) { link, error ->
            if (error != null ) {
                Log.d("error_link", "movie list error : " + error)
            } else if (link != null) {
                Log.d("error_get_link", "add movies to list: " + link)
                callback(link)
            }
        }
    }


    /*fun create_movie_link_popup(popupView : View, imdb_link: String){
        popupView.findViewById<ImageView>(R.id.movie_poster)?.setOnClickListener {
            // inflate the popup dialog layout
            val builder = AlertDialog.Builder(requireContext())
            val popupView = LayoutInflater.from(requireContext()).inflate(R.layout.popup_layout, null)
            builder.setView(popupView)
            val dialog = builder.create()
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //needed for background

            dialog.show()

            click_imdb_link(dialog, imdb_link)
        }

    }

    fun click_imdb_link(popupView: AlertDialog, imdb_link: String){
        popupView.findViewById<ImageView>(R.id.link_button)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(imdb_link))
            requireContext().startActivity(intent)
        }
    }*/

}