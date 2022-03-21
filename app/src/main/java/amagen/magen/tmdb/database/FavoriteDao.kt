package amagen.magen.tmdb.database

import amagen.magen.tmdb.entities.FavMovie
import amagen.magen.tmdb.entities.Movie
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavorite(favorite: FavMovie)
    @Query("delete from FAVORITE_MOVIES where id == :givenid")
    suspend fun deleteFromFavoriteTable(givenid:Long)
    @Query("SELECT * FROM FAVORITE_MOVIES WHERE id ==:givenid")
    suspend fun isFavorite(givenid: Long):Boolean

}