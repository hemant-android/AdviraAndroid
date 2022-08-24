package com.advira.advirafarm.buyer.ui.splash.roomDatatbase.Dao;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.advira.advirafarm.buyer.ui.product.categoryapi.Product_search;

import java.util.List;

@Dao
public interface SearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Product_search> moviesList);

    @Query("SELECT * FROM product_search")
    LiveData<List<Product_search>> getAllItem();

    @Query("DELETE FROM product_search")
    void deleteAll();

    @Query("SELECT * FROM product_search ORDER BY database_id ASC")
    DataSource.Factory<Integer,Product_search> getAllMovies();

    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert_movies_details(List<MoviesDetail> moviesDetailList);

    @Query("SELECT * FROM movies_detail")
    LiveData<List<MoviesDetail>> getAllMoviesDetails();*/
}
