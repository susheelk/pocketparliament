package com.susheel.pocketparliament.ui;

import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

import com.susheel.pocketparliament.R;
import com.susheel.pocketparliament.ui.adapters.TabPagerAdapter;
import com.susheel.pocketparliament.ui.fragments.bills.BillEventsFragment;
import com.susheel.pocketparliament.ui.fragments.bills.BillOverviewFragment;
import com.susheel.pocketparliament.ui.tasks.ColorUtils;

public class BillActivity extends AppCompatActivity {

    public static final String NUMBER = "number";
    public static final String TITLE = "title";
    public static final String ID = "id";

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;

    private TabPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        Bundle args = getIntent().getExtras();
        setTitle("Bill "+args.getString(BillActivity.NUMBER));

        int color = 0;
        if(args.getString(BillActivity.NUMBER).startsWith("S")){
            color = getResources().getColor(R.color.senate);
        } else if (args.getString(BillActivity.NUMBER).startsWith("C")) {
            color = getResources().getColor(R.color.colorPrimary);
        }
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ColorUtils.darken(color));
        bindViews();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabLayout.setBackgroundColor(color);
        setupTabs(args.getInt(BillActivity.ID));
    }

    private void bindViews(){
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupTabs(int id){
        adapter = new TabPagerAdapter(getSupportFragmentManager());
        adapter.add("Overview", BillOverviewFragment.forBill(id));
        adapter.add("Events", BillEventsFragment.forBill(id));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
