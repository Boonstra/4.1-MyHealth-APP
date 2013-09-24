package com.example.myhealth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;


public class Measurements extends Menu_Activity {

	private ProgressDialog pDialog;
	
	//JSONParser jParser = new JSONParser();
		
	private final String TAG = "Measurements";
	
	//JSONParser jParser = new JSONParser();
	
	ArrayList<HashMap<String, String>> measurements;
	
	//URL
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.measurements_layout);
		
		Log.i(TAG, "Measurements clicked");
		
		
		measurements = new ArrayList<HashMap<String, String>>();
		
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
			try {
				// Simulate network access.
				Thread.sleep(2000);
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
