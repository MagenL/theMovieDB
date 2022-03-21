package amagen.magen.tmdb.ui.singlemovie

import amagen.magen.tmdb.MainActivity
import amagen.magen.tmdb.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import amagen.magen.tmdb.databinding.FragmentSingleMovieBinding
import amagen.magen.tmdb.entities.FavMovie
import amagen.magen.tmdb.entities.Movie
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.sin

const val base_url= "https://image.tmdb.org/t/p/original"
class SingleMovieFragment : Fragment() {

    private var _binding: FragmentSingleMovieBinding? = null

    private val binding get() = _binding!!
    private lateinit var singleMovieViewModel: SingleMovieViewModel

    private lateinit var movie:Movie

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.hide()
        _binding = FragmentSingleMovieBinding.inflate(inflater, container, false)
        val bundle = arguments?.getString("movie")
        if(bundle != null){
            movie = Gson().fromJson(bundle,Movie::class.java)
        }
        singleMovieViewModel = ViewModelProvider(this)[SingleMovieViewModel::class.java]
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO){
            singleMovieViewModel.checkIfMovieIsFavorite(movie.id)
        }


        initUI()

    }

    private fun initUI() {
        Glide.with(this).load(base_url + movie.backdrop_path).centerCrop().into(binding.ivMovie)
        singleMovieViewModel.isFavorite.observe(viewLifecycleOwner){
            if(it !=null){
                binding.fbLikeButton.setColorFilter(Color.RED)
                binding.fbLikeButton.isSelected=it
            }
        }
        binding.fbLikeButton.setOnClickListener {
            onLikeORUnlikeClicked(it)
        }
        binding.tvMovieName.text = movie.title
        binding.tvSummaryText.text = movie.overview
        binding.tvPopularity.text =
            resources.getString(R.string.popularity_f, movie.popularity!!.toString());
        binding.tvReleaseDate.text =
            resources.getString(R.string.popularity_f, movie.release_date!!.toString());
    }

    private fun onLikeORUnlikeClicked(it: View) {
        it.isSelected = !it.isSelected
        if (it.isSelected) {
            binding.fbLikeButton.setColorFilter(Color.RED)
            singleMovieViewModel.addToFavorite(movie.id)
        } else {
            binding.fbLikeButton.setColorFilter(Color.BLACK)
            singleMovieViewModel.deleteFromFavortie(movie.id)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}