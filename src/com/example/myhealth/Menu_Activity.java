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
			
			case R.id.measurement:
				Log.i(TAG, "Measurements item clicked");
				Intent intent1 = new Intent(this, Measurements.class);
				startActivity(intent1);
			return true;
			
			case R.id.urinetest:
				Log.i(TAG, "Urine Test item clicked");
				Intent intent2 = new Intent(this, UrineTest.class);
				startActivity(intent2);
			return true;
			
			case R.id.devices:
				Log.i(TAG, "Device item clicked");
				Intent intent3 = new Intent(this, Device.class);
				startActivity(intent3);
			return true;
			
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
