package com.example.myhealth;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;



public class ECG extends Menu_Activity{
	
	private ProgressDialog pDialog;
	
	private final String TAG = "ECG";
	
	protected SharedPreferences prefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ecg_layout);
		
		Log.i(TAG, "ECG clicked");		
		
		prefs = getApplicationContext().getSharedPreferences("myPrefs", 0);
		
		Data.setPrefs(prefs);
		
		new LoadECG().execute();
	}
	
	 class LoadECG extends AsyncTask<String, String, String> {
		 
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(ECG.this);
	            pDialog.setMessage("Loading ECG. Please wait...");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(false);
	            pDialog.show();
	        }
	 
			@Override
			protected String doInBackground(String... params) {

				return null;
			}
	 
			 protected void onPostExecute(String file_url) {
		           
		            pDialog.dismiss();
		            
		        }
	 
	    }
}
