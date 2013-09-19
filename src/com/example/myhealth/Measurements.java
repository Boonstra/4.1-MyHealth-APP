package com.example.myhealth;

import android.os.Bundle;
import android.util.Log;


public class Measurements extends Menu_Activity {

	
private final String TAG = "Measurements";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.measurements_layout);
		
		Log.i(TAG, "Measurements clicked");
	}
}
