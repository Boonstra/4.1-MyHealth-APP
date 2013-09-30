package com.example.myhealth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;


public class Measurements extends Menu_Activity {

	private ProgressDialog pDialog;
	
	private final String TAG = "Measurements";
	
	ArrayList<HashMap<String, String>> ;
	ArrayList<HashMap<String, String>> pulseList;
	ArrayList<HashMap<String, String>> measurementsList;
	
	protected SharedPreferences prefs;
	
	
	 // products JSONArray
    JSONArray measurements = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.measurements_layout);
		
		Log.i(TAG, "Measurements clicked");
		
		prefs = getApplicationContext().getSharedPreferences("myPrefs", 0);
		
		Data.setPrefs(prefs);
		
		measurementsList = new ArrayList<HashMap<String, String>>();
		
		new LoadAllMeasurements().execute();
	}
	
	
    class LoadAllMeasurements extends AsyncTask<String, String, String> {
 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Measurements.this);
            pDialog.setMessage("Loading measurements. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
		@Override
		protected String doInBackground(String... params) {
			
			// Check your log cat for JSON reponse
            Log.d("Measurements: ", json.toString());
			
			try {
				Time now = new Time();
				now.setToNow();
				
				JSONObject result = Data.actionGetMeasurements(mUsername, mPassword);

                // looping through All Products
                for (int i = 0; i < ,measurements.length(); i++) {
                    JSONObject c = measurements.getJSONObject(i);

                    // Storing each json item in variable
                    String id = c.getString(TAG_PID);
                    String name = c.getString(TAG_NAME);

                    // creating new HashMap
                    HashMap<String, String> map = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    map.put(TAG_PID, id);
                    map.put(TAG_NAME, name);

                    // adding HashList to ArrayList
                    productsList.add(map);
			}
			} catch (InterruptedException e) {
				return null;
			}

			return null;
		}
 
		 protected void onPostExecute(String file_url) {
	           
	            pDialog.dismiss();
	            
	        }
 
    }
	
}
