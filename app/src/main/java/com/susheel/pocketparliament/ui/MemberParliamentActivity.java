package com.susheel.pocketparliament.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.susheel.pocketparliament.R;
import com.susheel.pocketparliament.model.MemberParliament;
import com.susheel.pocketparliament.services.filters.Filter;
import com.susheel.pocketparliament.services.filters.FilterParameters;
import com.susheel.pocketparliament.services.filters.MemberParliamentFilter;
import com.susheel.pocketparliament.ui.adapters.TabPagerAdapter;
import com.susheel.pocketparliament.ui.fragments.MpListFragment;
import com.susheel.pocketparliament.ui.tasks.AsyncResponseListener;
import com.susheel.pocketparliament.ui.tasks.ColorUtils;
import com.susheel.pocketparliament.ui.tasks.GetMemberParliamentTask;

import java.util.List;

public class MemberParliamentActivity extends AppCompatActivity {

    // Views
    private TabLayout tabLayout;
    private ViewGroup titleBarLayout;
    private TextView nameTextView;
    private SimpleDraweeView image;
    private TextView blurb;

    private MemberParliament memberParliament;

    private GetMemberParliamentTask task;
    private TabPagerAdapter adapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_parliament);
        bindViews();
        setupTabs();

        Bundle arguments = getIntent().getExtras();

        // Set color
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int color = arguments.getInt(FilterParameters.COLOR);
        window.setStatusBarColor(ColorUtils.darken(color));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
        getSupportActionBar().setTitle("");
        tabLayout.setBackgroundColor(color);
        titleBarLayout.setBackgroundColor(color);

        getData(arguments.getString(FilterParameters.URL));
    }

    private void bindViews() {
        nameTextView = (TextView) findViewById(R.id.name);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        titleBarLayout = (ViewGroup) findViewById(R.id.title_bar);
        image = (SimpleDraweeView) findViewById(R.id.drawee);
        blurb = (TextView) findViewById(R.id.blurb);
    }

    private void updateView() {
        this.getSupportActionBar().setTitle(memberParliament.getName());
        nameTextView.setText(memberParliament.getName());
        image.setImageURI(memberParliament.getImageUrl());
        image.getHierarchy().setActualImageFocusPoint(new PointF(0.5f, 0.35f));
        blurb.setText(memberParliament.getBlurb());
    }

    private void setupTabs(){
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        adapter = new TabPagerAdapter(getSupportFragmentManager());
        adapter.add("Overview", MpListFragment.forAll());
        viewPager.setAdapter(adapter);


        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void getData(String url) {
        Filter<MemberParliament> filter = new MemberParliamentFilter();
        filter.addConstraint(FilterParameters.URL, url);
        task = new GetMemberParliamentTask();
        task.setAsyncResponseListener(new AsyncResponseListener<List<MemberParliament>>() {
            @Override
            public void onTaskSuccess(Class source, List<MemberParliament> data) {
                memberParliament = data.get(0);
                updateView();
            }

            @Override
            public void onTaskError(Class source, String message) {

            }
        });
        task.execute(filter);
    }

}
