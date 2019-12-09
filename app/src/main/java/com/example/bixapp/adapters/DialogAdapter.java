package com.example.bixapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bixapp.R;
import com.example.bixapp.model.Comment;

import java.util.List;

public class DialogAdapter extends BaseAdapter {

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    private List<Comment> comments;
    private Context context;

    public DialogAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int i) {
        return comments.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        view = null;
        if (view == null) {
            view = mInflater.inflate(R.layout.row_comment, null);
            Comment comment = comments.get(position);

            TextView name = view.findViewById(R.id.txtName);
            TextView body = view.findViewById(R.id.txtBody);

            name.setText(comment.getName());
            body.setText(comment.getBody());

        }
        return view;
    }
}
