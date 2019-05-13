package tech.susheelkona.pocketparliament.android.pages.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import tech.susheelkona.pocketparliament.android.adapters.TabPagerAdapter;
import tech.susheelkona.pocketparliament.android.fragments.NewsFragment;
import tech.susheelkona.pocketparliament.android.fragments.Refreshable;
import tech.susheelkona.pocketparliament.android.fragments.mps.MpListFragment;
import tech.susheelkona.pocketparliament.android.pages.AbstractPageFragment;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends AbstractPageFragment implements Refreshable {

    private ImageView wall;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabPagerAdapter adapter;

    private NewsFragment allNews;
    private NewsFragment followed;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(tech.susheelkona.pocketparliament.R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadNavigation("Pocket Parliament", true);

        AppBarLayout appBarLayout = (AppBarLayout) getView().findViewById(tech.susheelkona.pocketparliament.R.id.app_bar_layout);
//        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // Idek what this does but stack overflow came clutch
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if(verticalOffset == 0 || verticalOffset <= toolbar.getHeight() && !toolbar.getTitle().equals("")){
//                    toolbar.setTitle("");
//                } else if(!toolbar.getTitle().equals(toolbar)){
//                    toolbar.setTitle("Ttt");
//                }
//
//            }
//        });

        loadFollowed();
        wall = (ImageView) view.findViewById(tech.susheelkona.pocketparliament.R.id.main_image);

        int id = 0;

        switch (new Random().nextInt(4)+1){
            case 1: id = tech.susheelkona.pocketparliament.R.drawable.wall_2; break;
            case 2: id = tech.susheelkona.pocketparliament.R.drawable.wall_2; break;
            case 3: id = tech.susheelkona.pocketparliament.R.drawable.wall_3; break;
            case 4: id = tech.susheelkona.pocketparliament.R.drawable.wall_4; break;
            default: id = tech.susheelkona.pocketparliament.R.drawable.wall_2;
        }
        wall.setImageDrawable(getResources().getDrawable(id));
        addPages(view);
    }

    private void loadFollowed(){
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.replace(R.id.followed_mp_frame, MpListFragment.forFollowed());
//        transaction.replace(R.id.followed_bills_frame, BillsListFragment.forFollowed(true));
        transaction.commit();
    }

    private void addPages(View view) {
        viewPager = (ViewPager) view.findViewById(tech.susheelkona.pocketparliament.R.id.view_pager);
        adapter = new TabPagerAdapter(getChildFragmentManager());
//        adapter.add("News", BillsListFragment.forRecent());
        allNews = new NewsFragment();
        followed = (NewsFragment) NewsFragment.forFollowed();
        adapter.add("News", allNews);
        adapter.add("Following", followed);

        viewPager.setAdapter(adapter);
        tabLayout = (TabLayout) view.findViewById(tech.susheelkona.pocketparliament.R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void refresh() {
        if (allNews != null && allNews.isVisible()) {
            allNews.refresh();
        }
        if (followed != null && followed.isVisible()) {
            followed.refresh();
        }
    }
}
