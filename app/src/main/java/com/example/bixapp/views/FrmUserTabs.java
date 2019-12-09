package com.example.bixapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.bixapp.R;
import com.example.bixapp.adapters.UserTabPagerAdapter;
import com.example.bixapp.model.User;

public class FrmUserTabs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.frm_user_tabs);
        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabs = findViewById(R.id.tabs);
        TextView title = findViewById(R.id.titleTab);

        User user = getIntent().getExtras().getParcelable("user");
        title.setText(user.getFullName());

        UserTabPagerAdapter sectionsPagerAdapter = new UserTabPagerAdapter(this, getSupportFragmentManager());
        sectionsPagerAdapter.setUserId(user.getId());
        viewPager.setAdapter(sectionsPagerAdapter);

        tabs.setupWithViewPager(viewPager);

    }
}