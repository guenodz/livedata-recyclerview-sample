package me.guendouz.livedata_recyclerview.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by guendouz on 15/02/2018.
 */

@Dao
public interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAll(List<Post> posts);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(Post post);

    @Update
    void update(Post post);

    @Delete
    void delete(Post post);

    @Query("SELECT * FROM Post")
    LiveData<List<Post>> findAll();
}
