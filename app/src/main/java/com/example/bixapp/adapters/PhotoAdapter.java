package com.example.bixapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bixapp.R;
import com.example.bixapp.model.Photo;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends BaseAdapter {

    private final Context context;
    private List<Photo> lstPhotos;

    public PhotoAdapter(Context context) {
        this.context = context;
        lstPhotos = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return lstPhotos.size();
    }

    @Override
    public Object getItem(int i) {
        return lstPhotos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_photo, viewGroup, false);
        }

        ImageView img = view.findViewById(R.id.imgPhoto);
        TextView title = view.findViewById(R.id.txtTitle);

        final Photo item = (Photo) getItem(position);
        img.setImageResource(R.drawable.ico_album);
        title.setText(item.getTitle());

        Glide.with(context)
                .load(item.getThumbnail())
                .into(img);

        return view;

    }

    public void setPhotos(List<Photo> lstPhotos) {
        this.lstPhotos = lstPhotos;
        notifyDataSetChanged();
    }
}
