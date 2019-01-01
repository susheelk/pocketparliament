package tech.susheelkona.pocketparliament.android.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Susheel Kona
 */

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    List<FragmentTitlePair> list;

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
        this.list = new ArrayList<>();
    }

    public void add(String title, Fragment fragment) {
        list.add(new FragmentTitlePair(fragment, title));
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getTitle();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        List<FragmentTitlePair> duplicate = new ArrayList<>();
        for(FragmentTitlePair pair: list) {
            duplicate.add(pair);
        }
        list.clear();
        list = new ArrayList<>(duplicate);
    }
}

class FragmentTitlePair {
    Fragment fragment;
    String title;

    FragmentTitlePair(Fragment fragment, String title) {
        this.fragment = fragment;
        this.title = title;
    }

    Fragment getFragment() {
        return fragment;
    }


    String getTitle() {
        return title;
    }

}