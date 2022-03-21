package amagen.magen.tmdb.database

import amagen.magen.tmdb.entities.FavMovie
import amagen.magen.tmdb.entities.Movie
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTableForMovie(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movies:List<Movie>)


    @Query("SELECT * FROM MOVIES_TABLE")
    fun getMovies(): List<Movie>

    @Query("SELECT * FROM MOVIES_TABLE ORDER BY popularity desc")
    fun getMoviesOrderedByPopularity():List<Movie>


    @Query("SELECT * FROM MOVIES_TABLE ORDER BY release_date desc")
    fun getMoviesOrderedByReleaseDate():List<Movie>

    @Query("SELECT*FROM FAVORITE_MOVIES")
    fun getFavoriteMovies():List<FavMovie>



}