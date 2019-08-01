package me.guendouz.livedata_recyclerview.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

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

    @Query("SELECT * FROM posts_db")
    LiveData<List<Post>> findAll();

    @Query("SELECT * FROM posts_db WHERE post_title LIKE '%' || :query || '%' OR post_content LIKE '%' || :query || '%'")
    LiveData<List<Post>> findSearchValue(String query);
}
