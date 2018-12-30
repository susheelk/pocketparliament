package com.susheel.pocketparliament.android.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.susheel.pocketparliament.R;
import com.susheel.pocketparliament.android.pages.votes.VotesPageFragment;
import com.susheel.pocketparliament.android.tasks.GetMemberParliamentTask;
import com.susheel.pocketparliament.services.PartyService;
import com.susheel.pocketparliament.android.pages.bills.BillsPageFragment;
import com.susheel.pocketparliament.android.pages.home.HomeFragment;
import com.susheel.pocketparliament.android.pages.mp_list.MpsPageFragment;
import com.twitter.sdk.android.core.Twitter;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

//    public static final HashMap<Integer, Class> pageFragmentMap = new HashMap<Integer, Class>(){{
//        put(R.id.home_menu_link, HomeFragment.class);
//    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: move this to application class
        Fresco.initialize(this);
        Twitter.initialize(getApplication());
        PartyService partyService = PartyService.getInstance();
        partyService.setInputStream(getResources().openRawResource(R.raw.parties));
        partyService.loadParties();
        new GetMemberParliamentTask().execute();
//        partyService.write();

        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Load the home page
        loadPage(new HomeFragment(), false);

        final NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                for(int x = 0; x < navigationView.getMenu().size(); x++) {
                        navigationView.getMenu().getItem(x).setChecked(false);
                }

                item.setChecked(true);
                switch (item.getItemId()) {
                    case R.id.home_menu_link: loadPage(new HomeFragment(), true);
                        break;
                    case R.id.mp_menu_link: loadPage(new MpsPageFragment(), true);
                        break;
                    case R.id.bills_menu_link: loadPage(new BillsPageFragment(), true);
                        break;
                    case R.id.votes_menu_link: loadPage(new VotesPageFragment(), true);

                }
                closeNavigationDrawer();
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            closeNavigationDrawer();
            return;
        }
        super.onBackPressed();
    }

    public void loadPage(Fragment fragment, boolean backStack) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content_frame, (Fragment) fragment);
        if (backStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_options, menu);
        MenuItem searchItem = menu.findItem(R.id.search_button);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//        searchView.setQueryHint("Search MPs, Bills, Committees...");
//
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        ComponentName componentName = new ComponentName(getApplicationContext(), SearchableActivity.class);
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_button:
                openSearchActivity();
                return true;

            case android.R.id.home:
                openDrawer();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void openSearchActivity(){
        Intent intent = new Intent(this, SearchableActivity.class);
        startActivity(intent);
    }

    protected void closeNavigationDrawer() {
        drawerLayout.closeDrawer(Gravity.LEFT);
    }

    public void openDrawer(){
        drawerLayout.openDrawer(GravityCompat.START);
    }
}