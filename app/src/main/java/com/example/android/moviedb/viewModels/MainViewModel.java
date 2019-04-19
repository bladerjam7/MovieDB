package com.example.android.moviedb.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.moviedb.database.AppDatabase;
import com.example.android.moviedb.database.Favorites;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<Favorites>> favorites;


    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the DataBase");
        favorites = database.taskDao().loadAllFavorites();
    }


    public LiveData<List<Favorites>> getFavorites() {return favorites;}
}
