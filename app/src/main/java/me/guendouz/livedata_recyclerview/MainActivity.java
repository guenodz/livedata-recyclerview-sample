package me.guendouz.livedata_recyclerview;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.List;

import me.guendouz.livedata_recyclerview.db.Post;

public class MainActivity extends AppCompatActivity implements PostsAdapter.OnDeleteButtonClickListener {

    private PostsAdapter postsAdapter;
    private PostViewModel postViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        postsAdapter = new PostsAdapter(this, this);

        postViewModel = ViewModelProviders.of(this).get(PostViewModel.class);
        postViewModel.getAllPosts().observe(this, posts -> postsAdapter.setData(posts));

        RecyclerView recyclerView = findViewById(R.id.rvPostsLis);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(postsAdapter);
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
        } else if (item.getItemId() == R.id.searchPost){
            //LiveData<List<Post>> postList = postViewModel.searchPost("woman");
            //postViewModel.searchPost("woman").observe(this,posts -> postsAdapter.setData(posts));
            List<Post> tempList = postViewModel.searchPost("woman");
            Log.i("tempList :",tempList.get(0).getContent().toString());
            return true;
        }
            return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDeleteButtonClicked(Post post) {
        postViewModel.deletePost(post);

    }
}
