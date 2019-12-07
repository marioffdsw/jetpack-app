package com.example.bixapp.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bixapp.R;
import com.example.bixapp.adapters.UserAdapter;
import com.example.bixapp.model.User;
import com.example.bixapp.viewmodels.UserViewModel;

import java.util.List;

public class FrmUsers extends AppCompatActivity {

    private UserViewModel userViewModel;
    private RecyclerView rvUser;
    private UserAdapter userAdapter;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_users);

        final ProgressBar pbarUsers = findViewById(R.id.pbar_users);
        rvUser = findViewById(R.id.rvUsers);

        rvUser.setLayoutManager(new LinearLayoutManager(this));
        rvUser.setHasFixedSize(true);

        userAdapter = new UserAdapter(getResources(), this);
        userAdapter.setOnItemClickListener(onItemClickListener);
        rvUser.setAdapter(userAdapter);

        userViewModel = new UserViewModel();
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                userAdapter.setUsers(users);
            }
        });

        userViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isLoading) {
                pbarUsers.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            }
        });

        initScrollListener();


    }

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            // viewHolder.getItemId();
            // viewHolder.getItemViewType();
            // viewHolder.itemView;
            User user = userViewModel.getUsers().getValue().get(position);
            Intent intent = new Intent(FrmUsers.this, FrmUserTabs.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }
    };


    private void initScrollListener() {
        rvUser.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == userAdapter.getItemCount() - 1) {
                        loadMore();
                    }
                }
            }
        });

    }

    private void loadMore() {
        Integer curPage = userViewModel.loadNextPage();
        Toast.makeText(this, "Pagina actual " + curPage, Toast.LENGTH_SHORT).show();
    }
}
