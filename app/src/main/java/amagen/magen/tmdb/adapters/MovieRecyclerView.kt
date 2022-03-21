package amagen.magen.tmdb.adapters

import amagen.magen.tmdb.databinding.MovieLayoutBinding
import amagen.magen.tmdb.entities.Movie
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
const val base_url= "https://image.tmdb.org/t/p/original"

class MovieRecyclerView (private val movies:List<Movie>, private val clickCallBack:clickEventListener)
    :RecyclerView.Adapter<MovieRecyclerView.ViewHolder>() {

    lateinit var context:Context

    inner class ViewHolder(val binding:MovieLayoutBinding):RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                if(adapterPosition != RecyclerView.NO_POSITION){
                    clickCallBack.onMovieClicked(movies[adapterPosition])
                }
            }
        }

    }

    interface clickEventListener {
        fun onMovieClicked(movie:Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(MovieLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.binding.movieName.text=movie.title
        Picasso.get().load(base_url+movie.poster_path).into(holder.binding.movieImage)
    }

    override fun getItemCount()=movies.size
}