package amagen.magen.tmdb.database

import amagen.magen.tmdb.entities.FavMovie
import amagen.magen.tmdb.entities.Movie
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

const val DB_VERSION = 2
const val DB_NAME = "MOVIE_DB"

@Database(entities = [FavMovie::class, Movie::class], version = DB_VERSION)
abstract class ApplicationDB:RoomDatabase() {
    companion object{
        fun create(context: Context):ApplicationDB=
            Room.databaseBuilder(context,ApplicationDB::class.java,
            DB_NAME).build()
    }
    abstract fun movieDAO():MovieDao
    abstract fun favoriteDAO():FavoriteDao

}