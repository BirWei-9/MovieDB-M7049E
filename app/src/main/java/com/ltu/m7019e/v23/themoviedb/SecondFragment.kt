package com.ltu.m7019e.v23.themoviedb
import android.app.AlertDialog
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
            movie_genres.forEach{movie ->
                val movie_view = DataBindingUtil.inflate<GenreMovieItemBinding>(LayoutInflater.from(requireContext()), R.layout.genre_movie_item, movieContainer, false)
                movie_view.movie = movie


                create_movie_link_popup(movie_view.root, movie.imdb_link)
                movieContainer.addView(movie_view.root)

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
            val popupView =
                LayoutInflater.from(requireContext()).inflate(R.layout.popup_layout, null)
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

}