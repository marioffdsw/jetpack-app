package com.example.bixapp.views;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bixapp.R;
import com.example.bixapp.viewmodels.PostViewModel;

public class FrgPost extends Fragment {

    private PostViewModel mViewModel;

    public static FrgPost newInstance() {
        return new FrgPost();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_post, container, false);
        loadPostData();
        return view;

    }

    private void loadPostData() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PostViewModel.class);
        // TODO: Use the ViewModel
    }

}
