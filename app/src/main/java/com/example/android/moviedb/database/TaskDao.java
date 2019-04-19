package com.example.android.moviedb.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM favorites")
    LiveData<List<Favorites>> loadAllFavorites();

    @Insert
    void insertFavorites(Favorites favorites);

    @Delete
    void deleteFavorites(Favorites favorites);

    @Query("SELECT * FROM favorites WHERE id = :id")
    LiveData<Favorites> loadFavoritesById(int id);

    @Query("DELETE FROM favorites")
    public void nukeTable();



}
