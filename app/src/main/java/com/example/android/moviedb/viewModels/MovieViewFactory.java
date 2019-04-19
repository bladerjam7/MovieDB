package com.example.android.moviedb.viewModels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.android.moviedb.database.AppDatabase;

public class MovieViewFactory extends ViewModelProvider.NewInstanceFactory {


    private final AppDatabase mDb;
    private final int mFavoriteId;


    public MovieViewFactory(AppDatabase database, int taskId) {
        mDb = database;
        mFavoriteId = taskId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MovieViewModel(mDb, mFavoriteId);
    }
}
