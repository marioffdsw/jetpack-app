package com.example.bixapp.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bixapp.R;
import com.example.bixapp.adapters.PostAdapter;
import com.example.bixapp.model.Post;
import com.example.bixapp.viewmodels.PostViewModel;

import java.util.List;

public class FrgPost extends Fragment {

    private RecyclerView rvPost;
    private PostAdapter postAdapter;
    private int userId;
    private ProgressBar pbar;
    private TextView txtEmpty;

    private PostViewModel postViewModel;

    public static FrgPost newInstance() {
        return new FrgPost();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_post, container, false);
        pbar = view.findViewById(R.id.pbar_posts);
        rvPost = view.findViewById(R.id.rvPosts);
        txtEmpty = view.findViewById(R.id.txtEmpty);
        rvPost.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvPost.setHasFixedSize(true);

        return view;
    }

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            Post post = postViewModel.getPosts().getValue().get(position);
            DialogComments dialogComments = new DialogComments();
            dialogComments.setPostId(post.getId());
            dialogComments.show(getFragmentManager(), "COMMENTS");
        }
    };


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        postAdapter = new PostAdapter(getResources(), getActivity());
        postAdapter.setOnItemClickListener(onItemClickListener);
        rvPost.setAdapter(postAdapter);
        postViewModel = new PostViewModel();
        postViewModel = ViewModelProviders.of(this).get(PostViewModel.class);
        postViewModel.getPosts().observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(@Nullable List<Post> posts) {
                postAdapter.setUsers(posts);
            }
        });
        postViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isLoading) {
                pbar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                txtEmpty.setVisibility(!isLoading && postViewModel.getPosts().getValue().isEmpty() ?
                        View.VISIBLE : View.GONE);
            }
        });

        postViewModel.loadPosts(userId);
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
