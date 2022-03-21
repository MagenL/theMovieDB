package amagen.magen.tmdb.network

import amagen.magen.tmdb.entities.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class TmdbRepo {
    companion object{
        suspend fun getMoviesByPopularity():List<Movie>{
            return withContext(Dispatchers.IO){
                TmdbService.create().fetchMovies().results
            }
        }
    }
}