package com.example.bixapp.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bixapp.R;
import com.example.bixapp.model.Post;
import com.example.bixapp.model.User;

import java.io.InputStream;
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
                .inflate(R.layout.user_row, parent, false);
        return new PostHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder userHolder, int position) {
        Post currentPost = posts.get(position);
        userHolder.txtName.setText(currentPost.getTitle());
        userHolder.txtEmail.setText(currentPost.getBody());
        userHolder.txtPhone.setText(currentPost.getId());

        String avatar = currentPost.getLinks().get("avatar").href;

        final int resourceId = resources.getIdentifier(avatar, "drawable",context.getPackageName());
        Bitmap bitmap = BitmapFactory.decodeResource(resources,resourceId);
        RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(resources, bitmap);
        if(bitmap != null){
        roundedDrawable.setCornerRadius(bitmap.getHeight());
        userHolder.imgUser.setImageDrawable(roundedDrawable);

        }

        //new DownloadImageTask(userHolder, currentUser).execute(avatar);
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
        private TextView txtName;
        private TextView txtEmail;
        private TextView txtPhone;
        private ImageView imgUser;


        public PostHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtPhone = itemView.findViewById(R.id.txtPhone);
            imgUser = itemView.findViewById(R.id.imgUser);
            itemView.setTag(this);
            itemView.setOnClickListener(clickListener);
        }
    }

    public void setOnItemClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }



}
