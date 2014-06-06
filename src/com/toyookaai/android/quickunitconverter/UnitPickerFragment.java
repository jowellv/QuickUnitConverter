package com.toyookaai.android.quickunitconverter;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

//class that create a DialogFragment that contains a list of units that can be selected
public class UnitPickerFragment extends DialogFragment {
	public	 static final String EXTRA_UNITNAMES = "com.toyookaai.android.quickunitconverter.unitnames";
	public static final String EXTRA_CURRENTUNIT = "com.toyookaai.android.quickunitconverter.currentunit";

	private ArrayList<String>  mUnitNames;
	private String mCurrentUnit;
	
	public static UnitPickerFragment newInstance(ArrayList<String> unit, String currentUnit) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_UNITNAMES, unit);
		args.putSerializable(EXTRA_CURRENTUNIT, currentUnit);
		
		UnitPickerFragment fragment = new UnitPickerFragment();
		fragment.setArguments(args);
		return fragment;
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		mCurrentUnit = (String)getArguments().getSerializable(EXTRA_CURRENTUNIT);
		mUnitNames = (ArrayList<String>)getArguments().getSerializable(EXTRA_UNITNAMES);
		
		return new AlertDialog.Builder(getActivity())
			.setTitle(R.string.unit_picker_title)
			.setSingleChoiceItems(mUnitNames.toArray(new String[mUnitNames.size()]), mUnitNames.indexOf(mCurrentUnit), new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							mCurrentUnit = mUnitNames.get(which);
							sendResult(Activity.RESULT_OK);
							dialog.dismiss();
						}
					})
					.create();
			
	}
	private void sendResult(int resultCode) {
		if (getTargetFragment() == null)
			return;
		
		Intent i = new Intent();
		i.putExtra(EXTRA_CURRENTUNIT, mCurrentUnit);
		
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
	}
}
