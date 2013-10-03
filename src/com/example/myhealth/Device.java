package com.example.myhealth;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


public class Device extends Activity {
	
private final String TAG = "Device";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.device_layout);
		
		Log.i(TAG, "Device clicked");
	}
}
