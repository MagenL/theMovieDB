package amagen.magen.tmdb.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val FAVORITE_MOVIES="FAVORITE_MOVIES"
@Entity(tableName = FAVORITE_MOVIES)
data class FavMovie (
    @PrimaryKey
    val id: Long?
)