package com.example.myhealth;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class Menu_Activity extends Activity {
		
private final String TAG = "Base";
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		
		switch(item.getItemId()){
			
			case R.id.bloodpressure:
				Log.i(TAG, "BloodPressure item clicked");
				Intent intent1 = new Intent(this, BloodPressure.class);
				startActivity(intent1);
			return true;
			
			case R.id.pulse:
				Log.i(TAG, "Pulse item clicked");
				Intent intent2 = new Intent(this, Pulse.class);
				startActivity(intent2);
			return true;
			
			case R.id.ecg:
				Log.i(TAG, "ECG item clicked");
				Intent intent3 = new Intent(this, ECG.class);
				startActivity(intent3);
			return true;
			
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
