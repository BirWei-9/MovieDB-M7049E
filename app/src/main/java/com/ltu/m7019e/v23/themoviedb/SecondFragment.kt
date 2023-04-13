package com.ltu.m7019e.v23.themoviedb
import android.app.AlertDialog
import android.content.ComponentCallbacks
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.ltu.m7019e.v23.themoviedb.database.Genres
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.ltu.m7019e.v23.themoviedb.api.ApiClient
import com.ltu.m7019e.v23.themoviedb.api.ApiService
import com.ltu.m7019e.v23.themoviedb.database.Movies
import com.ltu.m7019e.v23.themoviedb.databinding.GenreMovieItemBinding
import com.ltu.m7019e.v23.themoviedb.model.Genre
import com.ltu.m7019e.v23.themoviedb.model.Movie

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val genreContainer = view.findViewById<LinearLayout>(R.id.secound_frag_layout)
        val movie = Movies()
        getGenreApiCall { genreList ->
            val genre = Genres()
            Log.d("Error_Af", "before" + genreList)
            genre.list = genre.addGenres(genreList)
            Log.d("Error_Af", "after" + genre.list)
            genre.list.forEach { genre ->
                val genre_view = LayoutInflater.from(requireContext())
                    .inflate(R.layout.genre_item_list, genreContainer, false)
                val movie_genres = movie.list.filter { it.genres.contains(genre.second) }
                if (movie_genres.isEmpty()) {
                    genreContainer.removeView(genre_view)
                } else {
                    genre_view.findViewById<TextView>(R.id.genres_names)?.text = genre.first
                    genreContainer.addView(genre_view)
                }

                val movieContainer = genre_view.findViewById<LinearLayout>(R.id.movie_genre_list)
                movie_genres.forEach { movie ->
                    val movie_view = DataBindingUtil.inflate<GenreMovieItemBinding>(
                        LayoutInflater.from(requireContext()),
                        R.layout.genre_movie_item,
                        movieContainer,
                        false
                    )
                    movie_view.movie = movie


                    create_movie_link_popup(movie_view.root, movie.imdb_link)
                    movieContainer.addView(movie_view.root)

                }

            }
        }


        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    fun create_movie_link_popup(popupView : View, imdb_link: String){
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
    }
    private fun getMoviesApiCall(callback: (List<Movie>)->Unit){
        val apiClient = ApiClient()
        apiClient.getPopMovies(page = 1) { movieList, error ->
            if(error != null){
                Log.d("Error_Movies", "list" + movieList)
            } else if(movieList != null){
                Log.d("Movie_list", "list" + movieList)
                callback(movieList)
            }
        }
    }

    private fun getGenreApiCall(callback: (List<Genre>)->Unit){
        val apiClient = ApiClient()
        apiClient.getGenres() { genreList, error ->
            if(error != null){
                Log.d("Error_Genre", "list" + genreList)
            } else if(genreList != null){
                Log.d("Genre_list", "list" + genreList)
                callback(genreList)
            }
        }
    }

}