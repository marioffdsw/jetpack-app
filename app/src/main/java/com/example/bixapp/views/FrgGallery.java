package com.example.bixapp.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bixapp.R;

public class FrgGallery extends Fragment {

    private int userId;

    public static FrgGallery newInstance() {
        return new FrgGallery();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frg_gallery, container, false);

        FrgAlbum frg = FrgAlbum.newInstance();
        frg.setUserId(userId);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.gallery_container, frg);
        ft.commit();

        return view;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
