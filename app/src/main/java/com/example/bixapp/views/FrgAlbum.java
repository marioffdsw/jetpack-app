package com.example.bixapp.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bixapp.R;
import com.example.bixapp.adapters.AlbumAdapter;
import com.example.bixapp.model.Album;
import com.example.bixapp.viewmodels.AlbumViewModel;

import java.util.List;

public class FrgAlbum extends Fragment {

    private RecyclerView rvAlbum;
    private AlbumAdapter albumAdapter;
    private boolean isLoading = false;
    private int userId;
    private Context context;
    private ViewGroup container;

    private AlbumViewModel albumViewModel;
    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            Album album = albumViewModel.getAlbums().getValue().get(position);
            System.out.println("jjjjj");
            Toast.makeText(getActivity(), "Album id" + album.getId(), Toast.LENGTH_SHORT).show();

            FrgPhotos nextFrag = FrgPhotos.newInstance();
            nextFrag.setAlbumId(album.getId());



            System.out.println(container);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(container.getId(), nextFrag, "findThisFragment")
                    .addToBackStack(null)
                    .commit();


        }
    };

    public FrgAlbum() {
    }

    public static FrgAlbum newInstance() {
        return new FrgAlbum();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frg_album, container, false);
        this.container = container;

        rvAlbum = view.findViewById(R.id.rvAlbum);
        rvAlbum.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvAlbum.setHasFixedSize(true);

        albumAdapter = new AlbumAdapter();
        albumAdapter.setOnItemClickListener(onItemClickListener);
        rvAlbum.setAdapter(albumAdapter);

        albumViewModel = new AlbumViewModel();
        albumViewModel = ViewModelProviders.of(this).get(AlbumViewModel.class);
        albumViewModel.getAlbums().observe(this, new Observer<List<Album>>() {
            @Override
            public void onChanged(@Nullable List<Album> album) {
                albumAdapter.setUsers(album);
            }
        });

        albumViewModel.loadPosts(userId);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
