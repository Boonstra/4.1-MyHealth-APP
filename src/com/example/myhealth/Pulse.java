package com.example.myhealth;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.myhealth.BloodPressure.BPMeasurement;
import com.example.myhealth.BloodPressure.LoadAllBloodPressure;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class Pulse extends Menu_Activity {
	
	private ProgressDialog pDialog;

	private final String TAG = "Blood Pressure";

	protected SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pulse_layout);

		Log.i(TAG, "Measurements clicked");

		prefs = getApplicationContext().getSharedPreferences("myPrefs", 0);

		Data.setPrefs(prefs);

		PulseMeasurement[] PMarray = null;
		
		try {
			PMarray = new LoadAllPulse().execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(PMarray.length);
		ListView listView =  (ListView)this.findViewById(R.id.pulseList);
		pmArrayAdapter adapter = new pmArrayAdapter(this,R.layout.pulserow_layout, PMarray);
		listView.setAdapter(adapter);
		
		
	}

	class LoadAllPulse extends AsyncTask<String, String, PulseMeasurement[]> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Pulse.this);
			pDialog.setMessage("Loading Blood Pressure. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected PulseMeasurement[] doInBackground(String... params) {

			PulseMeasurement[] PMarray = null;
			
			try {
				System.out.println("nope");

				ArrayList<JSONObject> result = Data.actionGetMeasurementPulse("2013-09-01 00:00:00", "2013-12-01 00:00:00");
				
				PMarray = new  PulseMeasurement[result.size()];
				for (int i = 0, len = result.size(); i < len; i++) {
					JSONObject obj = result.get(i);					
					PMarray[i] = new PulseMeasurement(obj.getInt("value"),obj.getString("datetime"));
				}

				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			pDialog.dismiss();
			return PMarray;
		}

		protected void onPostExecute(String file_url) {

			pDialog.dismiss();

		}

	}

	public class PulseMeasurement {

		private int pulseValue;
		private String date;

		public PulseMeasurement(int pulseValue, String date){
			this.date = date;
			this.pulseValue = pulseValue;
		}
		
		public PulseMeasurement(){
			
		}
		
		public int getpulseValue() {
			return pulseValue;
		}

		public void setpulseValue(int pulseValue) {
			this.pulseValue = pulseValue;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

	}

}
