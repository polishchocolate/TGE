package com.kyler.mbq.tge;

import org.w3c.dom.NodeList;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kyler.mbq.tge.adapters.WelcomePagerAdapter;

public class TGE extends FragmentActivity {
	SharedPreferences mPreferences;

	// Used later
	Intent intent;

	Context context;

	NodeList nodelist;

	ProgressDialog pDialog;

	private long lastPressedTime;

	private static final int PERIOD = 2000;

	WebView wv;

	Notification notification;

	// MMMMMM TOAST
	Toast toast;

	TextView textview;

	private DrawerLayout mDrawerLayout;

	private ListView mDrawerList;

	SharedPreferences prefs;

	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;

	private CharSequence mTitle;

	private String[] mCategories;

	// Fragment welcome = new Welcome();

	private Process p;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.tge);

		mTitle = mDrawerTitle = getTitle();

		mCategories = getResources().getStringArray(R.array.MenuDrawerStuff);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		LayoutInflater inflater = getLayoutInflater();
		ViewGroup header = (ViewGroup) inflater.inflate(R.layout.header,
				mDrawerList, false);
		mDrawerList.addHeaderView(header, null, false);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mCategories));

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		getActionBar().setDisplayHomeAsUpEnabled(true);

		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(

		this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close)

		{
			public void onDrawerClosed(View view) {

				getActionBar().setTitle(mTitle);

				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()

			}

			public void onDrawerOpened(View drawerView) {

				getActionBar().setTitle(mDrawerTitle);

				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()

			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {

			selectItem(0);
		}

		ViewPager viewPager = (ViewPager) findViewById(R.id.welcomePager);

		// Set the ViewPagerAdapter into ViewPager
		viewPager.setAdapter(new WelcomePagerAdapter(
				getSupportFragmentManager()));
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			switch (event.getAction()) {
			case KeyEvent.ACTION_DOWN:
				if (event.getDownTime() - lastPressedTime < PERIOD) {
					finish();
				} else {
					Toast.makeText(getApplicationContext(),
							"Press again to exit.", Toast.LENGTH_SHORT).show();
					lastPressedTime = event.getEventTime();
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.tge, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (mDrawerToggle.onOptionsItemSelected(item)) {

			return true;
		}
		switch (item.getItemId()) {

		case android.R.id.home:
			Intent intent = new Intent(this, TGE.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;

		default:

		}
		;

		return super.onOptionsItemSelected(item);
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		switch (position) {

		case 0:
			// ft.replace(R.id.content_frame, welcome);
			break;

		case 1:
			try {
				Intent intent = new Intent("android.intent.action.MAIN");
				intent.setComponent(ComponentName
						.unflattenFromString("com.google.android.apps.books/com.google.android.apps.books.app.BooksActivity"));
				intent.addCategory("android.intent.category.LAUNCHER");
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				this.overridePendingTransition(R.anim.ltr, R.anim.rtl);
			} catch (ActivityNotFoundException e) {
				Toast.makeText(
						this.getApplicationContext(),
						"There was a problem loading the application: "
								+ "Google Books", Toast.LENGTH_LONG).show();
				AlertDialog.Builder downloadDialog = new AlertDialog.Builder(
						this);
				downloadDialog.setTitle(":(");
				downloadDialog
						.setMessage("C'mon man. You gotta install it first.");
				downloadDialog.setPositiveButton("Help me please",
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialogInterface, int i) {
								Uri uri = Uri
										.parse("market://details?id=com.google.android.apps.books");
								Intent intent = new Intent(Intent.ACTION_VIEW,
										uri);
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								try {
									TGE.this.startActivity(intent);
									intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								} catch (ActivityNotFoundException e) {
									TGE.this.showAlert("ERROR",
											"Google Play Store not found! wtf is going on");
								}
							}
						});
				downloadDialog.setNegativeButton("I'm better than u lol",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int i) {

								dialog.dismiss();
								finish();
							}
						});
				downloadDialog.show();
			}

			/*
			 * Intent books = new Intent(this, BooksActivity.class);
			 * books.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			 * startActivity(books);
			 */
			break;

		case 2:
			try {
				Intent intent = new Intent("android.intent.action.MAIN");
				intent.setComponent(ComponentName
						.unflattenFromString("com.android.calendar/com.android.calendar.AllInOneActivity"));
				intent.addCategory("android.intent.category.LAUNCHER");
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				this.overridePendingTransition(R.anim.ltr, R.anim.rtl);
			} catch (ActivityNotFoundException e) {
				Toast.makeText(
						this.getApplicationContext(),
						"There was a problem loading the application: "
								+ "Google Calendar", Toast.LENGTH_LONG).show();
				AlertDialog.Builder downloadDialog = new AlertDialog.Builder(
						this);
				downloadDialog.setTitle(":(");
				downloadDialog
						.setMessage("C'mon man. You gotta install it first.");
				downloadDialog.setPositiveButton("Help me please",
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialogInterface, int i) {
								Uri uri = Uri
										.parse("market://details?id=com.google.android.calendar");
								Intent intent = new Intent(Intent.ACTION_VIEW,
										uri);
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								try {
									TGE.this.startActivity(intent);
									intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								} catch (ActivityNotFoundException e) {
									TGE.this.showAlert("ERROR",
											"Google Play Store not found! wtf is going on");
								}
							}
						});
				downloadDialog.setNegativeButton("I'm better than u lol",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int i) {

								dialog.dismiss();
								finish();
							}
						});
				downloadDialog.show();
			}
			break;
		}

		ft.commit();

		mDrawerList.setItemChecked(position, true);

		mDrawerLayout.closeDrawer(mDrawerList);
	}

	protected void showAlert(String string, String string2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {

		super.onPostCreate(savedInstanceState);

		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {

		super.onConfigurationChanged(newConfig);

		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	public Process getP() {
		return p;
	}

	public void setP(Process p) {
		this.p = p;
	}

	public static class CategoriesFragment extends Fragment {

		public static final String ARG_CATEGORY = "category";

		public CategoriesFragment() {

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			View rootView = inflater.inflate(R.layout.fragment_categories,
					container, false);

			int i = getArguments().getInt(ARG_CATEGORY);

			String List = getResources()
					.getStringArray(R.array.MenuDrawerStuff)[i];

			getActivity().setTitle(List);

			return rootView;

		}
	}
}