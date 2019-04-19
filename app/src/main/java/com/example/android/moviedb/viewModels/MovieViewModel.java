package com.example.android.moviedb.viewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.moviedb.database.AppDatabase;
import com.example.android.moviedb.database.Favorites;


public class MovieViewModel extends ViewModel {

    private LiveData<Favorites> favorites;


    public MovieViewModel(AppDatabase database, int favoriteId) {
        favorites = database.taskDao().loadFavoritesById(favoriteId);
    }


    public LiveData<Favorites> getFavorites() {return favorites;}
}
