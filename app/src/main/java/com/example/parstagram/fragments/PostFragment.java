package com.example.parstagram.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.parstagram.Post;
import com.example.parstagram.PostAdapter;
import com.example.parstagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class PostFragment extends Fragment {

    private static final String TAG = "PostFragment";
    private RecyclerView rvPosts;
    private DividerItemDecoration dividerItemDecoration;
    private SwipeRefreshLayout swipeRefreshLayout;
    protected PostAdapter postAdapter;
    protected List<Post> allPosts;

    public PostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        allPosts                = new ArrayList<>();
        rvPosts                 = view.findViewById(R.id.rvPosts);
        swipeRefreshLayout      = view.findViewById(R.id.swipeRefresh);
        postAdapter             = new PostAdapter(getContext(), allPosts);


        // Listener for refresh listener
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryPosts();
            }
        });

        // Configure colors for refresh listener
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light));

        // Set the adapter on the recycler view
        rvPosts.setAdapter(postAdapter);

        // Set the layout manager on the recycler view
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getContext().getResources().getDrawable(R.drawable.divider_line));
        rvPosts.addItemDecoration(dividerItemDecoration);

        queryPosts();
    }


    /*
     * Name         : queryPosts
     * Parameters   : void
     * Description  : Query posts from the database in the background
     * Returns      : void
     */
    protected void queryPosts(){
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATED_AT);
        // Retrieve all the posts
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e != null){
                    Log.e(TAG, "Issue with getting posts\n");
                    return;
                }

                for (Post post: objects){
                    Log.i(TAG, "Post: " + post.getDescription() +  ", username: " + post.getUser().getUsername());
                }

                postAdapter.clear();
                allPosts.addAll(objects);
                // Stop showing progress bar for refreshing content
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}