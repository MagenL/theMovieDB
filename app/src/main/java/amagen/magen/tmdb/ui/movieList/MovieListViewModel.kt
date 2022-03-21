package amagen.magen.tmdb.ui.movieList

import amagen.magen.tmdb.MainActivity
import amagen.magen.tmdb.entities.Movie
import amagen.magen.tmdb.network.TmdbRepo
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieListViewModel: ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies  get() = _movies

    init {
        fetchMovieFromDB()
    }

    private fun fetchMovieFromDB() {
        viewModelScope.launch (Dispatchers.IO){

            val moviesFromDB = MainActivity.instance!!.db.movieDAO().getMovies()
            if(moviesFromDB.isEmpty()){
                fetchMovieFromTMDB()
            }else{
                _movies.postValue(moviesFromDB)
            }
        }
    }

    private fun fetchMovieFromTMDB() {
        viewModelScope.launch (Dispatchers.IO){
            val moviesFromTmdbRepo = TmdbRepo.getMoviesByPopularity()
            println(moviesFromTmdbRepo)
            MainActivity.instance!!.db.movieDAO().addMovies(moviesFromTmdbRepo)
            _movies.postValue(moviesFromTmdbRepo)
        }
    }



}