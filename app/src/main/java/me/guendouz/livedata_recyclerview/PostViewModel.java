package me.guendouz.livedata_recyclerview;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.guendouz.livedata_recyclerview.db.Post;
import me.guendouz.livedata_recyclerview.db.PostDao;
import me.guendouz.livedata_recyclerview.db.PostsDatabase;

/**
 * Created by guendouz on 15/02/2018.
 */

public class PostViewModel extends AndroidViewModel {

    private PostDao postDao;
    private ExecutorService executorService;

    public PostViewModel(@NonNull Application application) {
        super(application);
        postDao = PostsDatabase.getInstance(application).postDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    LiveData<List<Post>> getAllPosts() {
        return postDao.findAll();
    }

    void savePost(Post post) {
        executorService.execute(() -> postDao.save(post));
    }

    void deletePost(Post post) {
        executorService.execute(() -> postDao.delete(post));
    }
}
