package amagen.magen.tmdb.ui.movieList

import amagen.magen.tmdb.MainActivity
import amagen.magen.tmdb.R
import amagen.magen.tmdb.adapters.MovieRecyclerView
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import amagen.magen.tmdb.databinding.FragmentMovieListBinding
import amagen.magen.tmdb.entities.Movie
import amagen.magen.tmdb.ui.singlemovie.SingleMovieFragment
import android.annotation.SuppressLint
import android.view.MenuItem
import android.widget.GridLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieListFragment : Fragment(), MovieRecyclerView.clickEventListener {

    private var _binding: FragmentMovieListBinding? = null
    private lateinit var movieListViewModel: MovieListViewModel

    private lateinit var myMovies:List<Movie>

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        movieListViewModel = ViewModelProvider(this)[MovieListViewModel::class.java]
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvMovies.layoutManager = GridLayoutManager(requireContext(),2)

        (activity as AppCompatActivity).supportActionBar?.show()
        movieListViewModel.movies.observe(viewLifecycleOwner){
            myMovies=it
            binding.rvMovies.adapter = MovieRecyclerView(myMovies,this@MovieListFragment)

        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_popularity ->{
                lifecycleScope.launch (Dispatchers.IO){
                    myMovies= MainActivity.instance!!.db.movieDAO().getMoviesOrderedByPopularity()
                    lifecycleScope.launch (Dispatchers.Main){
                        binding.rvMovies.adapter = MovieRecyclerView(myMovies,this@MovieListFragment)
                    }
                }
                true
            }
            R.id.action_new->{
                lifecycleScope.launch (Dispatchers.IO){
                    myMovies= MainActivity.instance!!.db.movieDAO().getMoviesOrderedByReleaseDate()
                    lifecycleScope.launch (Dispatchers.Main){
                        binding.rvMovies.adapter = MovieRecyclerView(myMovies,this@MovieListFragment)
                    }
                }
                true
            }
            R.id.action_fav->{
                lifecycleScope.launch (Dispatchers.IO){
                    val myFavMovies = MainActivity.instance!!.db.movieDAO().getFavoriteMovies()
                    val listOfMovies = ArrayList<Movie>()
                    myFavMovies.forEach {fav->
                        myMovies.forEach { mov->
                            if(fav.id==mov.id){
                                listOfMovies.add(mov)
                            }
                        }
                    }
                    lifecycleScope.launch (Dispatchers.Main){
                        binding.rvMovies.adapter = MovieRecyclerView(listOfMovies,this@MovieListFragment)
                    }

                }

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMovieClicked(movie: Movie) {
        val bundle = Bundle()
        bundle.putString("movie", Gson().toJson(movie))
        val singleMovieFragment = SingleMovieFragment()
        singleMovieFragment.arguments = bundle
        parentFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_main,singleMovieFragment).addToBackStack(null).commit()
    }
}