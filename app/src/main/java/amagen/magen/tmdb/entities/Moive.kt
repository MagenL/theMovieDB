package amagen.magen.tmdb.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

const val MOVIES_TABLE="MOVIES_TABLE"

@Entity(tableName = MOVIES_TABLE)
data class Movie (
    val adult: Boolean?,
    val backdrop_path: String?,
    @PrimaryKey
    val id: Long,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val release_date: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Long?,
)
