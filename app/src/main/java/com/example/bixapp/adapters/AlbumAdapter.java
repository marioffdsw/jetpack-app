package com.example.bixapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bixapp.R;
import com.example.bixapp.model.Album;

import java.util.ArrayList;
import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumHolder> {

    private View.OnClickListener clickListener;
    private List<Album> posts = new ArrayList<>();

    public AlbumAdapter() {
    }

    @NonNull
    @Override
    public AlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_album, parent, false);
        return new AlbumHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumHolder albumHolder, int position) {
        Album currentAlbum = posts.get(position);
        albumHolder.txtName.setText(currentAlbum.getTitle());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setUsers(List<Album> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    class AlbumHolder extends RecyclerView.ViewHolder {
        private TextView txtName;


        public AlbumHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            itemView.setTag(this);
            itemView.setOnClickListener(clickListener);
        }
    }


}
