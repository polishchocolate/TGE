package com.kyler.mbq.tge.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.kyler.mbq.tge.R;

public class Books1 extends Fragment {

	View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setHasOptionsMenu(true);

		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

		getActivity().getActionBar().setHomeButtonEnabled(true);

		View view = inflater.inflate(R.layout.books_frag_layout, container,
				false);

		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

		inflater.inflate(R.menu.books_menu, menu);

		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.PSbooks:
			Uri uri = Uri
					.parse("market://details?id=com.google.android.apps.books");
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			getActivity().setTheme(android.R.style.Theme_Holo_Light_Dialog);
			return true;

		case android.R.id.home:
			getActivity().onBackPressed();
			break;
		default:
			break;
		}

		return false;
	}
}