import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ltu.m7019e.v23.themoviedb.R
import com.ltu.m7019e.v23.themoviedb.databinding.GenreItemListBinding
import com.ltu.m7019e.v23.themoviedb.model.Genre

class GenreAdapter() : ListAdapter<Genre, GenreAdapter.ViewHolder>(GenreListDiffCallback()) {

    class ViewHolder(private val binding: GenreItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        private val genreMovieListRV: RecyclerView
        val genreMovieListAdapter: GenreMovieListAdapter
        init {
            val context = itemView.context
            genreMovieListRV = itemView.findViewById(R.id.genre_movie_list_recycler_view)
            genreMovieListRV?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            genreMovieListAdapter = GenreMovieListAdapter()
            genreMovieListRV.adapter = genreMovieListAdapter
        }

        fun bind(genre: Genre) {
            binding.genresNames.text = genre.name
            // set up the RecyclerView with movies for this genre
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GenreItemListBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val genre = getItem(position)
        if (genre != null) {
            holder.genreMovieListAdapter.submitList(genre.movieList)
            holder.bind(genre)
        }
    }



    class GenreListDiffCallback : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem == newItem
        }

    }
}
