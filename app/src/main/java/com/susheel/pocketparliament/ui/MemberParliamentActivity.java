package com.susheel.pocketparliament.ui;

import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.susheel.pocketparliament.R;
import com.susheel.pocketparliament.model.MemberParliament;
import com.susheel.pocketparliament.services.filters.Filter;
import com.susheel.pocketparliament.services.filters.FilterParameters;
import com.susheel.pocketparliament.services.filters.MemberParliamentFilter;
import com.susheel.pocketparliament.ui.adapters.TabPagerAdapter;
import com.susheel.pocketparliament.ui.fragments.bills.BillsListFragment;
import com.susheel.pocketparliament.ui.fragments.mps.MpOverviewFragment;
import com.susheel.pocketparliament.ui.tasks.AsyncResponseListener;
import com.susheel.pocketparliament.ui.tasks.ColorUtils;
import com.susheel.pocketparliament.ui.tasks.GetMemberParliamentTask;
import com.susheel.pocketparliament.ui.tasks.SharedPreferenceHelper;

import java.util.List;

public class MemberParliamentActivity extends AppCompatActivity {

    // Views
    private TabLayout tabLayout;
    private ViewGroup titleBarLayout;
    private TextView nameTextView;
    private SimpleDraweeView image;
    private TextView blurb;
    private ProgressBar progressBar;
    private CheckBox checkBox;

    private MemberParliament memberParliament;

    private GetMemberParliamentTask task;
    private TabPagerAdapter adapter;
    private ViewPager viewPager;

    private final SharedPreferenceHelper preferences = SharedPreferenceHelper.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_parliament);
        bindViews();

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

//        preferences = SharedPreferenceHelper.getInstance();
    }

    private void bindViews() {
        nameTextView = (TextView) findViewById(R.id.name);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        titleBarLayout = (ViewGroup) findViewById(R.id.title_bar);
        image = (SimpleDraweeView) findViewById(R.id.drawee);
        blurb = (TextView) findViewById(R.id.blurb);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        checkBox = (CheckBox) findViewById(R.id.follow_box);
        checkBox.setVisibility(View.INVISIBLE);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text;
                text = checkBox.isChecked() ? "You are now following " : "You have now unfollowed ";
                preferences.toggleFollowMemberParliament(memberParliament, getApplicationContext());
                Toast.makeText(getApplicationContext(), text+memberParliament.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateView() {
        this.getSupportActionBar().setTitle(memberParliament.getName());
        nameTextView.setText(memberParliament.getName());
        image.setImageURI(memberParliament.getImageUrl());
        image.getHierarchy().setActualImageFocusPoint(new PointF(0.5f, 0.35f));
        blurb.setText(memberParliament.getBlurb());
    }

    private void setupTabs(){
        adapter = new TabPagerAdapter(getSupportFragmentManager());

        adapter.add("Overview", MpOverviewFragment.getForMemberParliament(memberParliament));
        adapter.add("Bills", BillsListFragment.forSponsor(memberParliament.getName()));
        viewPager.setAdapter(adapter);
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
                progressBar.setVisibility(View.INVISIBLE);
                viewPager.setVisibility(View.VISIBLE);
                setupTabs();
                updateView();

                List<String> followed = SharedPreferenceHelper.getInstance().getFollowedMemberParliaments(getApplicationContext());
                Log.i("FOLLOWED_SIZE", "f"+followed.get(0));
                checkBox.setChecked(followed != null ? SharedPreferenceHelper.getInstance().isFollowed(memberParliament, getApplicationContext()) : false);
                checkBox.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTaskError(Class source, String message) {

            }
        });
        progressBar.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.INVISIBLE);
        task.execute(filter);
    }

}
