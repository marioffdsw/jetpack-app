package com.example.bixapp.views;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.example.bixapp.R;
import com.example.bixapp.adapters.DialogAdapter;
import com.example.bixapp.model.Comment;
import com.example.bixapp.viewmodels.CommentViewModel;

import java.util.List;

public class DialogComments extends DialogFragment {

    private List<Comment> comments;
    private ListView lvComments;
    private CommentViewModel commentViewModel;
    private int postId;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dlg_comments, null);
        lvComments = view.findViewById(R.id.lvComments);

        if (savedInstanceState != null) {
            postId = savedInstanceState.getInt("id");
        }


        final DialogAdapter adapter = new DialogAdapter(this.getActivity().getApplicationContext());

        //lvComments.setAdapter(adapter);


        commentViewModel = new CommentViewModel();
        commentViewModel = ViewModelProviders.of(this).get(CommentViewModel.class);
        commentViewModel.getComments().observe(this, new Observer<List<Comment>>() {
            @Override
            public void onChanged(@Nullable List<Comment> comments) {
                adapter.setComments(comments);
                lvComments.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        commentViewModel.loadComments(postId);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setTitle("Comentarios");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });

        return builder.create();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("postId", postId);
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
}