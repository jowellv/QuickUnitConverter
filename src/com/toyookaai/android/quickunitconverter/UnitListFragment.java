package com.toyookaai.android.quickunitconverter;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

public class UnitListFragment extends ListFragment {
	private ArrayList<Unit> mUnits;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mUnits = UnitWizard.get(getActivity()).getUnits();
		ArrayAdapter<Unit> adapter = new ArrayAdapter<Unit>(getActivity(), android.R.layout.simple_list_item_1, mUnits);
		setListAdapter(adapter);
	}
}
