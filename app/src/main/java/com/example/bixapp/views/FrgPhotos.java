package com.example.bixapp.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.bixapp.R;
import com.example.bixapp.adapters.PhotoAdapter;
import com.example.bixapp.model.Photo;
import com.example.bixapp.viewmodels.PhotoViewModel;

import java.sql.SQLOutput;
import java.util.List;

public class FrgPhotos extends Fragment {

    private GridView gvPhotos;
    private PhotoAdapter photoAdapter;
    private PhotoViewModel photoViewModel;
    private int albumId;
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Toast.makeText(getActivity(), "imagen click", Toast.LENGTH_SHORT).show();
            Photo photo = photoViewModel.getPhotos().getValue().get(i);
            Intent intent = new Intent(getContext(), FrmPhotoAlbum.class);
            intent.putExtra("photo", photo);
            startActivity(intent);
        }
    };

    public static FrgPhotos newInstance() {
        return new FrgPhotos();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frg_photos, container, false);
        gvPhotos = view.findViewById(R.id.gvPhotos);

        photoAdapter = new PhotoAdapter(this.getActivity());
        gvPhotos.setAdapter(photoAdapter);
        gvPhotos.setOnItemClickListener(onItemClickListener);


        photoViewModel = new PhotoViewModel();
        photoViewModel = ViewModelProviders.of(this).get(PhotoViewModel.class);
        photoViewModel.getPhotos().observe(this, new Observer<List<Photo>>() {
            @Override
            public void onChanged(@Nullable List<Photo> photos) {
                photoAdapter.setPhotos(photos);
            }
        });

        gvPhotos.setOnItemClickListener(onItemClickListener);
        photoViewModel.loadPhotos(albumId);

        return view;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }
}
