package com.ltu.m7019e.v23.themoviedb
import GenreAdapter
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ltu.m7019e.v23.themoviedb.databinding.FragmentSecondBinding
import com.ltu.m7019e.v23.themoviedb.databinding.GenreItemListBinding
import com.ltu.m7019e.v23.themoviedb.viewmodel.GenreFragmentViewModdelFactory
import com.ltu.m7019e.v23.themoviedb.viewmodel.GenreFragmentViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {
    private lateinit var viewModel: GenreFragmentViewModel
    private lateinit var viewModelFactory: GenreFragmentViewModdelFactory

    private var _binding: FragmentSecondBinding? = null;
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSecondBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application

        viewModelFactory = GenreFragmentViewModdelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(GenreFragmentViewModel::class.java)


        val adapter = GenreAdapter()
        binding.genreListRv.adapter = adapter
        viewModel.genreList.observe(
            viewLifecycleOwner
        ) { genreList ->
            genreList?.let {
                adapter.submitList(genreList)

                }
            }


        viewModel.navigateToMovieDetail.observe(viewLifecycleOwner) { movie ->
            movie?.let{
                val dialog = AlertDialog.Builder(requireContext())
                    .setTitle(movie.title)
                    .setMessage("IMDB link: ${movie.imdb_link}:: press OK to go to the movie page")
                    .setPositiveButton("OK") { _, _ ->
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(movie.imdb_link))
                        startActivity(intent)
                    }
                    .setNegativeButton("Cancel", null)
                    .create()

                dialog.show()
                viewModel.onMovieDetailNavigated()
            }
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*val genreContainer = view.findViewById<LinearLayout>(R.id.secound_frag_layout)
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
*/

        //view.findViewById<Button>(R.id.button_second).setOnClickListener {
        //    findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        //}
    }

}