package com.kyler.mbq.tge.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kyler.mbq.tge.fragments.LearnMore;
import com.kyler.mbq.tge.fragments.Welcome;

public class WelcomePagerAdapter extends FragmentPagerAdapter {

        final int PAGE_COUNT = 2;
        // Tab Titles
        private String tabtitles[] = new String[] { "Welcome", "Learn More"};
        Context context;

        public WelcomePagerAdapter(FragmentManager fm) {
                super(fm);
        }

        @Override
        public int getCount() {
                return PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
                switch (position) {

                case 0:
                        Welcome w1 = new Welcome();
                    //  this.getActivity().finish();
                        return w1;
                        
                case 1:
                    LearnMore lm = new LearnMore();
                //  this.getActivity().finish();
                    return lm;                        
                }
                
                return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
                return tabtitles[position];
        }
}
