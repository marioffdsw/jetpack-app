package com.example.bixapp.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bixapp.R;
import com.example.bixapp.model.Post;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {

    private View.OnClickListener clickListener;
    private List<Post> posts = new ArrayList<>();
    private Resources resources;
    private Context context;

    public PostAdapter(Resources resources, Context context) {
        this.resources = resources;
        this.context = context;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_post, parent, false);
        return new PostHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder postHolder, int position) {
        Post currentPost = posts.get(position);
        postHolder.txtTitle.setText(currentPost.getTitle());
        postHolder.txtBody.setText(currentPost.getBody());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setUsers(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    class PostHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle;
        private TextView txtBody;


        public PostHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtBody = itemView.findViewById(R.id.txtBody);
            itemView.setTag(this);
            itemView.setOnClickListener(clickListener);
        }
    }

    public void setOnItemClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }



}
