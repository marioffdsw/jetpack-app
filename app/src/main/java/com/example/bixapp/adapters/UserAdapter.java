package com.example.bixapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bixapp.R;
import com.example.bixapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {

    private View.OnClickListener clickListener;
    private List<User> users = new ArrayList<>();
    private Context context;

    public UserAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_user, parent, false);
        return new UserHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder userHolder, int position) {
        User currentUser = users.get(position);
        userHolder.txtName.setText(currentUser.getFullName());
        userHolder.txtEmail.setText(currentUser.getEmail());
        userHolder.txtPhone.setText(currentUser.getPhone());

        String avatar = currentUser.getLinks().get("avatar").href;

        Glide.with(context)
                .load(avatar)
                .apply(RequestOptions.circleCropTransform())
                .into(userHolder.imgUser);

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    class UserHolder extends RecyclerView.ViewHolder {
        private TextView txtName;
        private TextView txtEmail;
        private TextView txtPhone;
        private ImageView imgUser;


        public UserHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtPhone = itemView.findViewById(R.id.txtPhone);
            imgUser = itemView.findViewById(R.id.imgUser);
            itemView.setTag(this);
            itemView.setOnClickListener(clickListener);
        }
    }

    public void setOnItemClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

}
