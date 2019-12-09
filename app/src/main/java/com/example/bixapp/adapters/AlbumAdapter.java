package com.example.bixapp.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bixapp.R;
import com.example.bixapp.model.Album;

import java.util.ArrayList;
import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumHolder> {

    private View.OnClickListener clickListener;
    private List<Album> posts = new ArrayList<>();
    private Resources resources;
    private Context context;

    public AlbumAdapter(Resources resources, Context context) {
        this.resources = resources;
        this.context = context;
    }

    @NonNull
    @Override
    public AlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_user, parent, false);
        return new AlbumHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumHolder albumHolder, int position) {
        Album currentAlbum = posts.get(position);
        albumHolder.txtName.setText(currentAlbum.getTitle());
        albumHolder.txtEmail.setText("sdasd");
        //albumHolder.txtPhone.setText(currentAlbum.getId());

        //new DownloadImageTask(albumHolder, currentUser).execute(avatar);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setUsers(List<Album> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    class AlbumHolder extends RecyclerView.ViewHolder {
        private TextView txtName;
        private TextView txtEmail;
        private TextView txtPhone;
        private ImageView imgUser;


        public AlbumHolder(@NonNull View itemView) {
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
