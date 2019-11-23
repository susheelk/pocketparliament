package tech.susheelkona.pocketparliament.android.activities;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import tech.susheelkona.pocketparliament.R;
import tech.susheelkona.pocketparliament.SearchResultsFragment;

public class SearchableActivity extends AppCompatActivity {


    private SearchView searchView;
    private MenuItem searchItem;

    private ProgressBar progressBar;

    SearchResultsFragment resultsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        setTitle("");

        bindViews();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setElevation(0);
    }

    private void bindViews() {
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_activity_options, menu);
        searchItem = menu.findItem(R.id.search_button);
        searchItem.expandActionView();
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Search MPs, Bills...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                progressBar.setVisibility(View.VISIBLE);
                onUpdateText(query);
                progressBar.setVisibility(View.GONE);

                return false;
            }
        });
//        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
//            @Override
//            public boolean onMenuItemActionExpand(MenuItem item) {
//                return false;
//            }
//
//            @Override
//            public boolean onMenuItemActionCollapse(MenuItem item) {
//                return false;
//            }
//        });
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return false;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                onBackPressed();
                item.setVisible(false);
                return true;
            }
        });
        return true;
    }

    public void onUpdateText(String query){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        resultsFragment = SearchResultsFragment.forSearch(query, resultsFragment == null ? 0 : resultsFragment.getSelectedPage());
        transaction.replace(R.id.bills_content_frame, resultsFragment);
//        transaction.replace(R.id.mps_content_frame, MpListFragment.forSearch(query));
        transaction.commit();

    }
}
