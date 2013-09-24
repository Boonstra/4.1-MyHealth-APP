package com.example.myhealth;

import android.os.Bundle;
import android.util.Log;


public class UrineTest extends Menu_Activity {
	
private final String TAG = "UrineTest";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.urinetest_layout);
		
		Log.i(TAG, "Urine Test clicked");
	}
}
