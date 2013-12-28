package com.kyler.mbq.tge;

import java.util.ArrayList;

import org.w3c.dom.NodeList;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kyler.mbq.tge.activities.CheatSheet;
import com.kyler.mbq.tge.adapters.WelcomePagerAdapter;
import com.kyler.mbq.tge.preferences.PreferencesActivity;

import de.cketti.library.changelog.ChangeLog;

@SuppressLint("WorldReadableFiles")
public class TGE extends FragmentActivity {
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

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);

			view.isHorizontalFadingEdgeEnabled();
		}
	}

	private static final int PERIOD = 2000;

	Context context;

	// Used later
	Intent intent;

	TextView text;

	public ListView mList;

	public static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;

	private long lastPressedTime;

	private String[] mCategories;

	private DrawerLayout mDrawerLayout;

	private DrawerLayout mDrawerLayout2;

	@SuppressWarnings("unused") // y
	private CharSequence mDrawerTitle;

	private ActionBarDrawerToggle mDrawerToggle;

	SharedPreferences mPreferences;

	private CharSequence mTitle;

	NodeList nodelist;

	Notification notification;

	private Process p;

	ProgressDialog pDialog;

	SharedPreferences prefs;

	Activity callerActivity;

	// Fragment welcome = new Welcome();

	TextView textview;

	Toast toast;

	WebView wv;

	private ListView mDrawerList;

	private ListView mDrawerList2;

	ImageView iv;

	private PackageManager pm;

	public Process getP() {
		return p;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {

		super.onConfigurationChanged(newConfig);

		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	public void onConfigurationChanged2(Configuration newConfig2) {
		try {
			super.onConfigurationChanged(newConfig2);
			if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

			} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

			}
		} catch (Exception ex) {
		}
	}

	@SuppressLint("CutPasteId")
	// wtf
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.tge);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		mTitle = mDrawerTitle = getTitle();

		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		mCategories = getResources().getStringArray(R.array.MenuDrawerStuff);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		mDrawerList2 = (ListView) findViewById(R.id.list);

		setmDrawerLayout2((DrawerLayout) findViewById(R.id.drawer_layout));

		LayoutInflater inflater = getLayoutInflater();

		text = new TextView(this);

		/*
		 * final ViewGroup header = (ViewGroup)
		 * inflater.inflate(R.layout.header, mDrawerList, false);
		 * mDrawerList.addHeaderView(header, null, false);
		 */

		final ViewGroup footer = (ViewGroup) inflater.inflate(
				R.layout.version_in_preferences, mDrawerList, false);
		mDrawerList.addFooterView(footer, null, false);

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
			@Override
			public void onDrawerClosed(View view) {

				getActionBar().setTitle("Welcome!");

				Drawable icon = null;
				getActionBar().setIcon(icon);

				invalidateOptionsMenu();

			}

			@Override
			public void onDrawerOpened(View drawerView) {

				getActionBar().setTitle("Select an item.");

				Drawable icon = null;
				getActionBar().setIcon(icon);

				invalidateOptionsMenu();

			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {

			selectItem(0);
		}

		ViewPager viewPager = (ViewPager) findViewById(R.id.welcomePager);

		viewPager.setAdapter(new WelcomePagerAdapter(
				getSupportFragmentManager()));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.tge, menu);

		return super.onCreateOptionsMenu(menu);
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

	public void showChangelog() {
		new ChangeLog(this).getFullLogDialog().show();

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (mDrawerToggle.onOptionsItemSelected(item)) {

			return true;
		}
		switch (item.getItemId()) {

		case R.id.settings:
			Intent prefs = new Intent(TGE.this, PreferencesActivity.class);
			startActivity(prefs);
			break;

		case R.id.CL:
			showChangelog();
			break;

		case R.id.launchApp:
			voice();
			break;

		case R.id.X:
			super.finish();
			break;

		case R.id.cs:
			Intent CHEATER = new Intent(this, CheatSheet.class);
			startActivity(CHEATER);
			break;

		default:

		}
		;

		return super.onOptionsItemSelected(item);
	}

	public void voice() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
				"Which app would you like to open?");
		startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == VOICE_RECOGNITION_REQUEST_CODE
				&& resultCode == RESULT_OK) {
			ArrayList<String> gapps = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			mDrawerList2.setAdapter(new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, gapps));

			// mDrawerLayout2.openDrawer(mDrawerList2);

			if (gapps.contains("Books")) {
				Intent intent = new Intent("android.intent.action.MAIN");
				intent.setComponent(ComponentName
						.unflattenFromString("com.google.android.apps.books/com.google.android.apps.books.app.BooksActivity"));
				intent.addCategory("android.intent.category.LAUNCHER");
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);

				// Standard error stuff

			} else if (resultCode == RecognizerIntent.RESULT_AUDIO_ERROR) {
				showToastMessage("Audio Error");
			} else if (resultCode == RecognizerIntent.RESULT_CLIENT_ERROR) {
				showToastMessage("Client Error");
			} else if (resultCode == RecognizerIntent.RESULT_NETWORK_ERROR) {
				showToastMessage("Network Error");
			} else if (resultCode == RecognizerIntent.RESULT_NO_MATCH) {
				showToastMessage("No Match");
			} else if (resultCode == RecognizerIntent.RESULT_SERVER_ERROR) {
				showToastMessage("Server Error");

				// End of standard error stuff
			}

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void showToastMessage(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	public void informationMenu() {
		startActivity(new Intent("android.intent.action.INFOSCREEN"));
	}

	public void voiceinputbuttons() {
		mDrawerList2 = (ListView) findViewById(R.id.list);
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
	public boolean onPrepareOptionsMenu(Menu menu) {

		return super.onPrepareOptionsMenu(menu);
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
				downloadDialog.setMessage("App not installed.");
				downloadDialog.setPositiveButton("Help me please",
						new DialogInterface.OnClickListener() {
							@Override
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
				downloadDialog.setNegativeButton("No thanks",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int i) {

								dialog.dismiss();
								// finish();
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
				downloadDialog.setMessage("App not installed.");
				downloadDialog.setPositiveButton("Help me please",
						new DialogInterface.OnClickListener() {
							@Override
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
				downloadDialog.setNegativeButton("No thanks",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int i) {

								dialog.dismiss();
								// finish();
							}
						});
				downloadDialog.show();
			}
			break;

		/*
		 * case 3: try { Intent intent = new
		 * Intent("android.intent.action.MAIN");
		 * intent.setComponent(ComponentName .unflattenFromString(
		 * "com.google.android.apps.chrome.ChromeApplication/com.google.android.apps.chrome.Main"
		 * )); intent.addCategory("android.intent.category.LAUNCHER");
		 * intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		 * startActivity(intent); this.overridePendingTransition(R.anim.ltr,
		 * R.anim.rtl); } catch (ActivityNotFoundException e) { Toast.makeText(
		 * this.getApplicationContext(),
		 * "There was a problem loading the application: " +
		 * "Google Chrome Beta", Toast.LENGTH_LONG).show(); AlertDialog.Builder
		 * downloadDialog = new AlertDialog.Builder( this);
		 * downloadDialog.setTitle(":("); downloadDialog
		 * .setMessage("C'mon man. You gotta install it first.");
		 * downloadDialog.setPositiveButton("Help me please", new
		 * DialogInterface.OnClickListener() { public void onClick(
		 * DialogInterface dialogInterface, int i) { Uri uri = Uri
		 * .parse("market://details?id=com.chrome.beta"); Intent intent = new
		 * Intent(Intent.ACTION_VIEW, uri);
		 * intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); try {
		 * TGE.this.startActivity(intent);
		 * intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); } catch
		 * (ActivityNotFoundException e) { TGE.this.showAlert("ERROR",
		 * "Google Play Store not found! wtf is going on"); } } });
		 * downloadDialog.setNegativeButton("I'm better than u lol", new
		 * DialogInterface.OnClickListener() { public void
		 * onClick(DialogInterface dialog, int i) {
		 * 
		 * dialog.dismiss(); finish(); } }); downloadDialog.show(); } break;
		 */
		}

		ft.commit();

		mDrawerList.setItemChecked(position, true);

		mDrawerLayout.closeDrawer(mDrawerList);
	}

	protected void showAlert(String string, String string2) {

	}

	public void setP(Process p) {
		this.p = p;
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	public PackageManager getPm() {
		return pm;
	}

	public void setPm(PackageManager pm) {
		this.pm = pm;
	}

	public DrawerLayout getmDrawerLayout2() {
		return mDrawerLayout2;
	}

	public void setmDrawerLayout2(DrawerLayout mDrawerLayout2) {
		this.mDrawerLayout2 = mDrawerLayout2;
	}
}