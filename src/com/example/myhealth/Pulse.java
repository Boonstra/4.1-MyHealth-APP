package com.example.myhealth;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class Pulse extends Menu_Activity {
	
	private ListView pulseListView = null;
	
	private ArrayAdapter<JSONObject> aa;
	
	private ProgressDialog pDialog;
	
	private final String TAG = "Pulse";
	
	protected SharedPreferences prefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pulse_layout);
		
		Log.i(TAG, "Pulse clicked");		
		
		pulseListView = (ListView)findViewById(R.id.pulseList);
		
		prefs = getApplicationContext().getSharedPreferences("myPrefs", 0);
		
		Data.setPrefs(prefs);
		
		new LoadAllPulse().execute();
	}
	
	 class LoadAllPulse extends AsyncTask<String, String, String> {
		 
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(Pulse.this);
	            pDialog.setMessage("Loading Pulse. Please wait...");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(false);
	            pDialog.show();
	        }
	 
			@Override
			protected String doInBackground(String... params) {
				
				try {
					System.out.println("nope");
					
					ArrayList<JSONObject> result = Data.actionGetMeasurementBloodPressure("2013-09-01 00:00:00", "2013-12-01 00:00:00");	
					
					aa = new ArrayAdapter<JSONObject>( Pulse.this, android.R.layout.simple_list_item_1, result);
					
					
					runOnUiThread(new Runnable() {
					     public void run() {

					//stuff that updates ui
					    	 pulseListView.setAdapter(aa);
					    }
					});
							
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e){
					System.out.println(e.getMessage());
				}

				return null;
			}
	 
			 protected void onPostExecute(String file_url) {
		           
		            pDialog.dismiss();
		            
		        }
	 
	    }
}
