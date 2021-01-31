package com.example.android.miwok;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MiwokFragmentPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> fragments = new ArrayList<Fragment>() {
        {
            add(new NumbersFragment());
            add(new FamilyFragment());
            add(new ColorsFragment());
            add(new PhrasesFragment());
        }
    };

    public MiwokFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Numbers";
        }
        else if (position == 1) {
            return "Family";
        }
        else if (position == 2) {
            return "Colors";
        }
        else {
            return "Phrases";
        }
    }
}
