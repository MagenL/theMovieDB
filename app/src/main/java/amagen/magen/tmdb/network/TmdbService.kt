package amagen.magen.tmdb.network



import amagen.magen.tmdb.entities.Movie
import amagen.magen.tmdb.entities.MovieResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query



const val BASE_URL = "https://api.themoviedb.org/"

const val API_KEY = "2c46288716a18fb7aadcc2a801f3fc6b"
interface TmdbService {
//page=1&&sort_by=popularity.desc
    @GET("3/discover/movie")
    suspend fun fetchMovies(
    @Query("page")page:Int=1,
    @Query("sort_by")pop:String="popularity.desc",
    @Query("api_key")api_key:String=API_KEY
    ):MovieResponse

    companion object {
        fun create(): TmdbService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TmdbService::class.java)
        }
    }


}