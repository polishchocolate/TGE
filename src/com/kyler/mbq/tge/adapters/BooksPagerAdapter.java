package com.kyler.mbq.tge.adapters;

import com.kyler.mbq.tge.fragments.Books1;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class BooksPagerAdapter extends FragmentPagerAdapter {

        final int PAGE_COUNT = 1;
        // Tab Titles
        private String tabtitles[] = new String[] { "Books"};
        Context context;

        public BooksPagerAdapter(FragmentManager fm) {
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
                        Books1 b1 = new Books1();
                    //  this.getActivity().finish();
                        return b1;
                }
                
                return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
                return tabtitles[position];
        }
}