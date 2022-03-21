package amagen.magen.tmdb.ui.singlemovie

import amagen.magen.tmdb.MainActivity
import amagen.magen.tmdb.entities.FavMovie
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SingleMovieViewModel():ViewModel() {

    private var _isFav=MutableLiveData<Boolean>()
    val isFavorite get() = _isFav


    fun checkIfMovieIsFavorite(id:Long) {
        viewModelScope.launch (Dispatchers.IO){
            _isFav.postValue(MainActivity.instance!!.db.favoriteDAO().isFavorite(id))
        }
    }
    fun deleteFromFavortie(id:Long){
        viewModelScope.launch(Dispatchers.IO) {
            MainActivity.instance!!.db.favoriteDAO().deleteFromFavoriteTable(id)
        }
    }
    fun addToFavorite(id:Long){
        viewModelScope.launch(Dispatchers.IO) {
            MainActivity.instance!!.db.favoriteDAO().addFavorite(FavMovie(id))
        }
    }
}
