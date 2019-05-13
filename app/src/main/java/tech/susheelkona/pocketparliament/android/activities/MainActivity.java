package tech.susheelkona.pocketparliament.android.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
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

import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import tech.susheelkona.pocketparliament.android.background.BackgroundFetchWorker;
import tech.susheelkona.pocketparliament.android.fragments.Refreshable;
import tech.susheelkona.pocketparliament.android.pages.votes.VotesPageFragment;
import tech.susheelkona.pocketparliament.R;
import tech.susheelkona.pocketparliament.android.tasks.GetMemberParliamentTask;
import tech.susheelkona.pocketparliament.services.PartyService;
import tech.susheelkona.pocketparliament.android.pages.bills.BillsPageFragment;
import tech.susheelkona.pocketparliament.android.pages.home.HomeFragment;
import tech.susheelkona.pocketparliament.android.pages.mp_list.MpsPageFragment;
import com.twitter.sdk.android.core.Twitter;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    static String TAG = "NOTIF_WORKER";

//    public static final HashMap<Integer, Class> pageFragmentMap = new HashMap<Integer, Class>(){{
//        put(R.id.home_menu_link, HomeFragment.class);
//    }};

    private Refreshable currentPage;

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
        createNotificationChannels();
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

                Fragment currentFragment = new Fragment();
                boolean backStack = false;
                switch (item.getItemId()) {
                    case R.id.home_menu_link:
                        currentFragment = new HomeFragment();
                        backStack = true;
                        break;
                    case R.id.mp_menu_link:
                        currentFragment = new MpsPageFragment();
                        backStack = true;
                        break;
                    case R.id.bills_menu_link:
                        currentFragment = new BillsPageFragment();
                        backStack = true;
                        break;
                    case R.id.votes_menu_link:
                        currentFragment = new VotesPageFragment();
                        backStack = true;
                        break;
                }

                loadPage(currentFragment, backStack);
                addRefreshable(currentFragment);
                closeNavigationDrawer();
                return true;
            }
        });
        scheduleCheckTask();
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

    private void addRefreshable(Fragment fragment) {
        if (fragment instanceof Refreshable) {
            currentPage = (Refreshable) fragment;
        }
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

            case R.id.refresh_button:
                if (currentPage != null){
                    currentPage.refresh();
                }
                return true;

            case R.id.settings_button:
                openNotificationSettings();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void openSearchActivity(){
        Intent intent = new Intent(this, SearchableActivity.class);
        startActivity(intent);
    }

    protected void openNotificationSettings() {
        Intent intent = new Intent();
        intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName());
        } else {
            intent.putExtra("app_package", getPackageName());
            intent.putExtra("app_uid", getApplicationInfo().uid);
        }
        startActivity(intent);
    }

    protected void closeNavigationDrawer() {
        drawerLayout.closeDrawer(Gravity.LEFT);
    }

    public void openDrawer(){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(getString(R.string.news_notification_chanel), "News", importance);
            channel.setDescription("Bills");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void scheduleCheckTask(){
        WorkManager manager = WorkManager.getInstance();
        if(manager.getWorkInfosByTagLiveData(TAG).getValue() == null){
            PeriodicWorkRequest.Builder workRequestBuild = new
                    PeriodicWorkRequest.Builder(BackgroundFetchWorker.class, 1, TimeUnit.HOURS);
            workRequestBuild.addTag(TAG);
            WorkManager.getInstance().enqueue(workRequestBuild.build());
        }
    }
}