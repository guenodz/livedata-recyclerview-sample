package me.guendouz.livedata_recyclerview;

import android.app.Dialog;
import android.content.Context;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import me.guendouz.livedata_recyclerview.db.Post;
import me.guendouz.livedata_recyclerview.helper.Helper;
import me.guendouz.livedata_recyclerview.listener.ButtonClickListener;
import me.guendouz.livedata_recyclerview.listener.DialogButtonClickListener;

public class MainActivity extends AppCompatActivity implements ButtonClickListener {

    private PostsAdapter postsAdapter;
    private PostViewModel postViewModel;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.rvPostsLis);

        postsAdapter = new PostsAdapter(this, this);
        setAdapter();
        //get Viewmodel
        postViewModel = ViewModelProviders.of(this).get(PostViewModel.class);
        // Create the observer which updates the UI.
        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        postViewModel.getAllPosts().observe(this, posts -> postsAdapter.setData(posts));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.addPost) {
            postViewModel.savePost(new Post("This is a post title", "This is a post content"));
            return true;
        } else if (item.getItemId() == R.id.searchPost) {
            new Helper().showCustomDialog(getString(R.string.search_message_dialog), getString(R.string.search), getContext(), new DialogButtonClickListener() {
                @Override
                public void onDialogButtonClicked(String query, Dialog dialog) {
                    dialog.dismiss();
                    postViewModel.searchPost(query).observe((LifecycleOwner) getContext(), posts -> postsAdapter.setData(posts));
                }
            });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    void setAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(postsAdapter);
    }

    @Override
    public void onDeleteButtonClicked(Post post) {
        postViewModel.deletePost(post);
    }

    protected Context getContext() {
        return MainActivity.this;
    }
}
