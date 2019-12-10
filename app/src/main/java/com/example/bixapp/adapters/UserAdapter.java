package com.example.bixapp.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bixapp.R;
import com.example.bixapp.model.User;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {

    private View.OnClickListener clickListener;
    private List<User> users = new ArrayList<>();
    private Resources resources;
    private Context context;

    public UserAdapter(Resources resources, Context context) {
        this.resources = resources;
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

//        final int resourceId = resources.getIdentifier(avatar, "drawable", context.getPackageName());
//        Bitmap bitmap = BitmapFactory.decodeResource(resources, resourceId);
//        RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(resources, bitmap);
//        if (bitmap != null) {
//            roundedDrawable.setCornerRadius(bitmap.getHeight());
//            userHolder.imgUser.setImageDrawable(roundedDrawable);
//        }

        System.out.println("jjjjjjjjjjjjjjjj");
        Glide.with(context)
                .load(avatar)
                .apply(RequestOptions.circleCropTransform())
                .into(userHolder.imgUser);

        //new DownloadImageTask(userHolder.imgUser, currentUser).execute(avatar);
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

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView userHolder;
        User currentUser;

        public DownloadImageTask(ImageView userHolder, User currentUser) {
            this.userHolder = userHolder;
            this.currentUser = currentUser;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            System.out.println(">>>> " + urldisplay);
            Bitmap bmp = null;
//            try {
//                InputStream in = new java.net.URL(urldisplay).openStream();
//                bmp = BitmapFactory.decodeStream(in);
//            } catch (Exception e) {
//                Log.e("Error", e.getMessage());
//                e.printStackTrace();
//            }
            return bmp;
        }

        protected void onPostExecute(Bitmap bitmap) {
//            if (bitmap != null) {
//                RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(resources, bitmap);
//                roundedDrawable.setCornerRadius(bitmap.getHeight());
//                userHolder.imgUser.setImageDrawable(roundedDrawable);
//            } else {
//                bitmap = BitmapFactory.decodeResource(resources, R.drawable.user);
//                RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(resources, bitmap);
//                roundedDrawable.setCornerRadius(bitmap.getHeight());
//                userHolder.imgUser.setImageDrawable(roundedDrawable);
//
//            }
//            notifyDataSetChanged();
            currentUser.setPhotoLoad(true);

        }
    }

}
