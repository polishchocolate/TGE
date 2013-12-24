package com.kyler.mbq.tge.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kyler.mbq.tge.fragments.Books1;

public class BooksPagerAdapter extends FragmentPagerAdapter {

	Context context;
	final int PAGE_COUNT = 1;
	// Tab Titles
	private String tabtitles[] = new String[] { "Books" };

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
			// this.getActivity().finish();
			return b1;
		}

		return null;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return tabtitles[position];
	}
}