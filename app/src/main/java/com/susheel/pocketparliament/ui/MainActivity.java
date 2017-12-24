package com.susheel.pocketparliament.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.susheel.pocketparliament.R;
import com.susheel.pocketparliament.services.PartyService;
import com.susheel.pocketparliament.ui.pages.home.HomeFragment;
import com.susheel.pocketparliament.ui.pages.mp_list.MpsPageFragment;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

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
        PartyService partyService = PartyService.getInstance();
        partyService.setInputStream(getResources().openRawResource(R.raw.parties));
        partyService.loadParties();
//        partyService.write();

        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Load the home page
        loadPage(new HomeFragment(), false);

        final NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                switch (item.getItemId()) {
                    case R.id.home_menu_link: loadPage(new HomeFragment(), true);
                        break;
                    case R.id.mp_menu_link: loadPage(new MpsPageFragment(), true);
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

    public void loadPage(Fragment fragment, boolean backStack) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content_frame, (Fragment) fragment);
        if (backStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }



    protected void closeNavigationDrawer() {
        drawerLayout.closeDrawer(Gravity.LEFT);
    }
}