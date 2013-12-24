package com.kyler.mbq.tge.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.kyler.mbq.tge.R;
import com.kyler.mbq.tge.adapters.BooksPagerAdapter;

public class BooksActivity extends FragmentActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.books);
		
        ViewPager viewPager = (ViewPager) findViewById(R.id.booksPager);

        viewPager.setAdapter(new BooksPagerAdapter(
                        getSupportFragmentManager()));
	}

}
