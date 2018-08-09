package com.susheel.pocketparliament.ui;

import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;

import com.susheel.pocketparliament.R;
import com.susheel.pocketparliament.model.legislation.Bill;
import com.susheel.pocketparliament.ui.adapters.TabPagerAdapter;
import com.susheel.pocketparliament.ui.fragments.bills.BillEventsFragment;
import com.susheel.pocketparliament.ui.fragments.bills.BillOverviewFragment;
import com.susheel.pocketparliament.ui.tasks.ColorUtils;
import com.susheel.pocketparliament.ui.tasks.SharedPreferenceHelper;

public class BillActivity extends AppCompatActivity {

    public static final String NUMBER = "number";
    public static final String TITLE = "title";
    public static final String ID = "id";

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private CheckBox followBox;

    private TabPagerAdapter adapter;
    private final SharedPreferenceHelper preferences = SharedPreferenceHelper.getInstance();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Bill bill = new Bill();
        bill.setNumber(getIntent().getExtras().getString(BillActivity.NUMBER));

        getMenuInflater().inflate(R.menu.bill_activity_menu, menu);
        MenuItem item = menu.findItem(R.id.follow_box);
        followBox = (CheckBox) MenuItemCompat.getActionView(item);
        followBox.setButtonDrawable(R.drawable.star_checkbox_selector);
        followBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bill.setNumber(getIntent().getExtras().getString(BillActivity.NUMBER));
                preferences.toggleFollowBill(bill, getApplicationContext());
            }
        });
        followBox.setChecked(preferences.isFollowed(bill, getApplicationContext()));
        return true;
    }
}
