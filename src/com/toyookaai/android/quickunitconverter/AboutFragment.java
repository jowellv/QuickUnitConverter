package com.toyookaai.android.quickunitconverter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

public class AboutFragment extends DialogFragment {
	private String mVersionName;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		try {
			mVersionName = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
		} catch (PackageManager.NameNotFoundException e) {
			mVersionName = null;
		}
		return new AlertDialog.Builder(getActivity())
			.setTitle(getString(R.string.about_title) + " (v" + mVersionName + ")")
			.setMessage(R.string.about_message)
			.setPositiveButton(android.R.string.ok, null)
			.setNegativeButton(R.string.send_email, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					Intent i = new Intent(Intent.ACTION_SEND);
					i.setType("message/rfc822");
					i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"toyookaai@gmail.com"});
					i.putExtra(Intent.EXTRA_SUBJECT, "Quick Unit Converter v" + mVersionName +" | "+ android.os.Build.MODEL + " | " +  getResources().getConfiguration().locale);
					try {
					    startActivity(Intent.createChooser(i, "Choose an Email client :"));
					} catch (android.content.ActivityNotFoundException ex) {
					    Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
					}
				}
			})
			.setNeutralButton(R.string.more_apps, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(Uri.parse("market://details?id=com.toyookaai.android.quickunitconverter"));
					try {
						startActivity(intent);
					} catch (android.content.ActivityNotFoundException ex) {
					    Toast.makeText(getActivity(), "Could not find the market.", Toast.LENGTH_SHORT).show();
					}
				}
			})
			.create();
			
		
	}
}
