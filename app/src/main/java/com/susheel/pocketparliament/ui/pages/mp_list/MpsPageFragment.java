package com.susheel.pocketparliament.ui.pages.mp_list;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.susheel.pocketparliament.R;
import com.susheel.pocketparliament.ui.pages.AbstractPageFragment;

import java.lang.reflect.Constructor;

/**
 * A simple {@link Fragment} subclass.
 */
public class MpsPageFragment extends AbstractPageFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public MpsPageFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mps_page, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadNavigation("MPs");

        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        Class<? extends Fragment>[] fragments = new Class[]{FavouriteMpTabFragment.class, AllMpTabFragment.class};
        String[] pageTitles = new String[]{"Favourites", "All"};
        viewPager.setAdapter(new GenericViewPagerAdapter(getFragmentManager(), fragments, pageTitles));

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }


}



class GenericViewPagerAdapter extends FragmentStatePagerAdapter {

    private Class<? extends Fragment>[] fragments; // For better performance
    private String[] pageTitles; // Find a more efficient way to do this

    public GenericViewPagerAdapter(FragmentManager fm, Class[] fragments, String[] pageTitles) {
        super(fm);
        this.fragments = fragments;
        this.pageTitles = pageTitles;
    }

    @Override
    public Fragment getItem(int position) {
        Class<? extends Fragment> fragmentClass = fragments[position];
        try {
            Constructor<?> constructor = fragmentClass.getConstructor();
            return (Fragment) constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getCount() {
        return this.fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitles[position];
    }
}

