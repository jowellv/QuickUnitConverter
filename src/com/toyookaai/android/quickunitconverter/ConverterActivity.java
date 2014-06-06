package com.toyookaai.android.quickunitconverter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ConverterActivity extends ActionBarActivity implements
		ActionBar.TabListener {
	
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;
	
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	private static final String DIALOG_ABOUT = "about";

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_converter);

		// Set up the action bar.
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setHomeButtonEnabled(true);
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.converter, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings || id == android.R.id.home) {
			FragmentManager fm = this
					.getSupportFragmentManager();
			AboutFragment dialog = new AboutFragment();
			dialog.show(fm, DIALOG_ABOUT);
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());

	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		
		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}
		
		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			return UnitFragment.newInstance(position + 1);
		}

		@Override
		public int getCount() {
			// show 5 pages
			return 5;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.length).toUpperCase(l);
			case 1:
				return getString(R.string.mass).toUpperCase(l);
			case 2:
				return getString(R.string.temp).toUpperCase(l);
			case 3:
				return getString(R.string.area).toUpperCase(l);
			case 4:
				return getString(R.string.volume).toUpperCase(l);
			}
			return null;
		}
	}
	

	/**
	 * A main and only view fragment.
	 */
	public static class UnitFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		private EditText mLengthInput;
		private BigDecimal mResult = new BigDecimal("0.0");
		private ArrayList<String> mConversionUnits;
		private ArrayList<String> mUnitNames;
		private Button mUnitSelect;
		private static final String DIALOG_UNIT = "unit";
		private static final int REQUEST_UNIT = 0;
		private String mCurrentUnit;
		private UnitAdapter adapter;
		
		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static UnitFragment newInstance(int sectionNumber) {
			UnitFragment fragment = new UnitFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public UnitFragment() {
		}
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			
			//Initializing the first view and with default values
			View rootView;
			int page = getArguments().getInt(ARG_SECTION_NUMBER);
			mCurrentUnit = getCurrentUnit(page, null);
			rootView = inflater.inflate(R.layout.fragment_converter, container, false);
			ListView listView = (ListView) rootView.findViewById(R.id.unitList);
			mConversionUnits = new ArrayList<String>(UnitWizard.get(getActivity()).getResult(mResult, mCurrentUnit));
			adapter = new UnitAdapter(mConversionUnits);
			
			listView.setAdapter(adapter);
			//Setting up unit button label and function	
			mUnitNames = UnitWizard.get(getActivity()).getUnitNames(mCurrentUnit);
			mUnitSelect = (Button) rootView.findViewById(R.id.select_unit);
			mUnitSelect.setText(mCurrentUnit); // change later to mUnitNames.get(0) for other languages
			mUnitSelect.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					FragmentManager fm = getActivity().getSupportFragmentManager();
					UnitPickerFragment dialog = UnitPickerFragment.newInstance(mUnitNames, mCurrentUnit);
					dialog.setTargetFragment(UnitFragment.this, REQUEST_UNIT);
					dialog.show(fm, DIALOG_UNIT);
				}
			});
			
			
			mLengthInput = (EditText)rootView.findViewById(R.id.length_input);
			// Allows a negative input for only the temperature
			// or a positive decimal for anything else.
			if(mCurrentUnit.equals("\u2103") || mCurrentUnit.equals("\u2109") || mCurrentUnit.equals("K")) {
				mLengthInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
			} else {
				mLengthInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

			}
			//Sends the current unit and value to unit wizard. Sets the returned values to the new list.
			//Updates in real-time.
			mLengthInput.addTextChangedListener(new TextWatcher() {
				public void onTextChanged(CharSequence c, int start, int count, int after) {
					try {
						mResult = new BigDecimal(c.toString());
					} catch (NumberFormatException e) {
						mResult = new BigDecimal("0.0");
					}
					mConversionUnits.clear(); // Required to clear, not just reassign, the list for the adapter
											  //to refresh the correct values.
					mConversionUnits.addAll(UnitWizard.get(getActivity()).getResult(mResult, mCurrentUnit));
					adapter.notifyDataSetChanged();
				}
				
				public void beforeTextChanged(CharSequence c, int start, int count, int after) {
					// nothing here
				}
				public void afterTextChanged(Editable c) {
					//nothing here
				}
			});
			// Allows users to long click list item to switch units. Resets value to 0.
			listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			      @Override
			      public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id) {
			    	  String u= (String) parent.getItemAtPosition(position);
			    	  String output[] = u.split(":");
			    	  String selectedUnit = output[1].trim();
			    	  mUnitSelect.setText(selectedUnit);
			    	  mCurrentUnit = getCurrentUnit(getArguments().getInt(ARG_SECTION_NUMBER) , selectedUnit);
			    	  mLengthInput.setText(null);
			    	  return true;
			      }
			});
			return rootView;
		}
		
		
		private class UnitAdapter extends ArrayAdapter<String> {

			public UnitAdapter(ArrayList<String> units) {
				super(getActivity(), 0, units);
			}
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null ) {
					convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_unit, null);
				}
				String u = getItem(position);
				TextView resultTextView = (TextView)convertView.findViewById(R.id.unit_list_item_resultTextView);
				resultTextView.setText(u);
				int currentUnitPosition = UnitWizard.get(getActivity()).getUnitIndex(mCurrentUnit);
				if (position == currentUnitPosition) {
					resultTextView.setBackgroundColor(Color.parseColor("#cb93cb"));
				} else {
					resultTextView.setBackgroundColor(Color.TRANSPARENT);
				}
				return convertView;
			}
			
		}
		//Receives the new unit when changed and updates the list accordingly
		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			if (resultCode != Activity.RESULT_OK) return;
			if (requestCode == REQUEST_UNIT) {
				String unit = (String)data.getSerializableExtra(UnitPickerFragment.EXTRA_CURRENTUNIT);
				mUnitSelect.setText(unit);
				mCurrentUnit = getCurrentUnit(getArguments().getInt(ARG_SECTION_NUMBER) ,unit);
				mConversionUnits.clear();
				mConversionUnits.addAll(UnitWizard.get(getActivity()).getResult(mResult, mCurrentUnit));
				adapter.notifyDataSetChanged();
			}
		}
		
		//Method that returns the default unit based on the page
		//if no unit is currently selected
		public String getCurrentUnit(int page, String unit) {
			if(unit == null) {
				switch (page) {
				case 1:
					return "meter";
				case 2:
					return "kilogram";
				case 3:
					return "\u2103";
				case 4:
					return "kilometer\u00b2";
				case 5: 
					return "liter";
				}
				return "FAIL";
			} else {
				return unit;
			}
		}
	}

}
