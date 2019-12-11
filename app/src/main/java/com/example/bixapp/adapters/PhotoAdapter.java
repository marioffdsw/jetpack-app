package com.example.bixapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.bixapp.R;
import com.example.bixapp.model.Photo;

import java.util.List;

public class PhotoAdapter extends BaseAdapter {

    private final Context context;
    private List<Photo> lstPhotos;

    public PhotoAdapter(Context context) {
        this.context = context;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(R.drawable.ico_album);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(240, 240));
        return imageView;

    }

    public void setPhotos(List<Photo> lstPhotos) {
        this.lstPhotos = lstPhotos;
    }
}
