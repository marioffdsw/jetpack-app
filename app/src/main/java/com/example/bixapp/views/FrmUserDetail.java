package com.example.bixapp.views;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bixapp.R;
import com.example.bixapp.adapters.UserTabPagerAdapter;

public class FrmUserDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_user_detail);

        UserTabPagerAdapter tabsPagerAdapter = new UserTabPagerAdapter(this, getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.user_view_pager);
        viewPager.setAdapter(tabsPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

    }
}
