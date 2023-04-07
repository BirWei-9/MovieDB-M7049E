package com.ltu.m7019e.v23.themoviedb
import android.os.Bundle
import com.ltu.m7019e.v23.themoviedb.database.Genres
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.ltu.m7019e.v23.themoviedb.database.Movies
import com.ltu.m7019e.v23.themoviedb.databinding.GenreMovieItemBinding
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

        val movie = Movies()
        val genre = Genres()

        val genreContainer = view.findViewById<LinearLayout>(R.id.secound_frag_layout)
        genre.list.forEach { genre ->
            val genre_view = LayoutInflater.from(requireContext()).inflate(R.layout.genre_item_list,genreContainer,false)
            val movie_genres = movie.list.filter { it.genres.contains(genre) }
            if (movie_genres.isEmpty()){
                genreContainer.removeView(genre_view)
            } else {
                genre_view.findViewById<TextView>(R.id.genres_names)?.text = genre
                genreContainer.addView(genre_view)
            }

            val movieContainer = genre_view.findViewById<LinearLayout>(R.id.movie_genre_list)
            movie.list.forEach{movie ->
                val movie_view = DataBindingUtil.inflate<GenreMovieItemBinding>(LayoutInflater.from(requireContext()), R.layout.genre_movie_item, movieContainer, false)
                movie_view.movie = movie
                movieContainer.addView(movie_view.root)

            }

        }


        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

}