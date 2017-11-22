package com.susheel.pocketparliament;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import com.susheel.pocketparliament.fragments.pages.AbstractPageFragment;
import com.susheel.pocketparliament.fragments.pages.HomeFragment;
import com.susheel.pocketparliament.fragments.pages.MpListFragment;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    public static final HashMap<Integer, Class> pageFragmentMap = new HashMap<Integer, Class>(){{
        put(R.id.home_menu_link, HomeFragment.class);
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Load the home page
        loadPage(new HomeFragment());

        final NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                switch (item.getItemId()) {
                    case R.id.home_menu_link: loadPage(new HomeFragment());
                        break;
                    case R.id.mp_menu_link: loadPage(new MpListFragment());
                        break;

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

    private void loadPage(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content_frame, (Fragment) fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    protected void closeNavigationDrawer() {
        drawerLayout.closeDrawer(Gravity.LEFT);
    }
}