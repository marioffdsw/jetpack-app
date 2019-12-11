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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bixapp.R;
import com.example.bixapp.adapters.PhotoAdapter;
import com.example.bixapp.model.Photo;
import com.example.bixapp.viewmodels.PhotoViewModel;

import java.util.List;

public class FrgPhotos extends Fragment {

    private ListView lvPhotos;

    private PhotoAdapter photoAdapter;
    private PhotoViewModel photoViewModel;
    private int albumId;
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
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
        final ProgressBar pbar = view.findViewById(R.id.pbar_photos);
        final TextView txtEmpty = view.findViewById(R.id.txtEmpty);

        lvPhotos = view.findViewById(R.id.lvPhotos);

        photoAdapter = new PhotoAdapter(this.getActivity());
        lvPhotos.setAdapter(photoAdapter);
        lvPhotos.setOnItemClickListener(onItemClickListener);

        photoViewModel = new PhotoViewModel();
        photoViewModel = ViewModelProviders.of(this).get(PhotoViewModel.class);
        photoViewModel.getPhotos().observe(this, new Observer<List<Photo>>() {
            @Override
            public void onChanged(@Nullable List<Photo> photos) {
                photoAdapter.setPhotos(photos);
            }
        });

        photoViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isLoading) {
                pbar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                txtEmpty.setVisibility(!isLoading && photoViewModel.getPhotos().getValue().isEmpty() ?
                        View.VISIBLE : View.GONE);
            }
        });

        lvPhotos.setOnItemClickListener(onItemClickListener);
        photoViewModel.loadPhotos(albumId);

        return view;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }
}
