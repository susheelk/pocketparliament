package tech.susheelkona.pocketparliament.android.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

import tech.susheelkona.pocketparliament.android.fragments.mps.MpListFragment;
import tech.susheelkona.pocketparliament.services.filters.FilterParameters;
import tech.susheelkona.pocketparliament.R;
import tech.susheelkona.pocketparliament.android.adapters.TabPagerAdapter;
import tech.susheelkona.pocketparliament.android.fragments.votes.VoteOverviewFragment;
import tech.susheelkona.pocketparliament.android.tasks.SharedPreferenceHelper;

public class VoteActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;

    private TabPagerAdapter adapter;
    private final SharedPreferenceHelper preferences = SharedPreferenceHelper.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

        Bundle args = getIntent().getExtras();
        int id = args.getInt(FilterParameters.ID);
        setTitle("Vote " + id);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        bindViews();
        setupTabs(id);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void bindViews(){
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupTabs(int id){
        int yeas = getIntent().getExtras().getInt("yeas");
        int nays = getIntent().getExtras().getInt("nays");
        adapter = new TabPagerAdapter(getSupportFragmentManager());
        adapter.add("Overview", VoteOverviewFragment.forVote(id));
        adapter.add("Voted For ("+yeas+")", MpListFragment.forVote(id, "Yea"));
        adapter.add("Voted Against ("+nays+")",MpListFragment.forVote(id, "Nay"));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
