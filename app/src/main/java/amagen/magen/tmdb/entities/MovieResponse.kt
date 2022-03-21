package amagen.magen.tmdb.entities

import androidx.room.Entity
import java.util.*

@Entity
data class MovieResponse(
    val page: Long,
    val results: List<Movie>,
    val total_pages: Long,
    val total_results: Long,
    val date:Date=Date()
)