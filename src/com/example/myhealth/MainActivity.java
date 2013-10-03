package com.example.myhealth;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private SharedPreferences prefs;
	
	private final String TAG = "Main";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		prefs = getApplicationContext().getSharedPreferences("myPrefs", 0);
		
		TextView view = (TextView) findViewById(R.id.main_username);
		view.setText(" " + prefs.getString("username", null) + "!");
	}
	
	public void toMeasurements(View v){
			
			Log.i(TAG, "Starting Measurements");
			Intent intent = new Intent(this, BloodPressure.class);
			startActivity(intent);
			
		}

	public void toUrineTest(View v){
		
		Log.i(TAG, "Starting Urine Test");
		Intent intent = new Intent(this, UrineTest.class);
		startActivity(intent);
		
	}

	public void toDevice(View v){
		
		Log.i(TAG, "Starting Device");
		Intent intent = new Intent(this, Device.class);
		startActivity(intent);
		
	}

}
