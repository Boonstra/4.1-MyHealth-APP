package com.example.myhealth;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class BloodPressure extends Menu_Activity {

	private ProgressDialog pDialog;

	private final String TAG = "Blood Pressure";

	protected SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bloodpressure_layout);

		Log.i(TAG, "Measurements clicked");

		prefs = getApplicationContext().getSharedPreferences("myPrefs", 0);

		Data.setPrefs(prefs);

		BPMeasurement[] BPMarray = null;
		
		try {
			BPMarray = new LoadAllBloodPressure().execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ListView listView =  (ListView)this.findViewById(R.id.bloodList);
		bpmArrayAdapter adapter = new bpmArrayAdapter(this,R.layout.list_row_layout,BPMarray);
		listView.setAdapter(adapter);
		
		
	}

	class LoadAllBloodPressure extends AsyncTask<String, String, BPMeasurement[]> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(BloodPressure.this);
			pDialog.setMessage("Loading Blood Pressure. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected BPMeasurement[] doInBackground(String... params) {

			BPMeasurement[] BPMarray = null;
			
			try {
				System.out.println("nope");

				ArrayList<JSONObject> result = Data.actionGetMeasurementBloodPressure("2013-09-01 00:00:00", "2013-12-01 00:00:00");
				
				BPMarray = new  BPMeasurement[result.size()];
				
				for (int i = 0, len = result.size(); i < len; i++) {
					JSONObject obj = result.get(i);
					BPMarray[i] = new BPMeasurement(obj.getInt("high"),obj.getInt("low"),obj.getString("datetime"));
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			pDialog.dismiss();
			return BPMarray;
		}

		protected void onPostExecute(String file_url) {

			pDialog.dismiss();

		}

	}

	public class BPMeasurement {

		private int high, low;
		private String date;

		public BPMeasurement(int high, int low, String date){
			this.date = date;
			this.high = high;
			this.low = low;
		}
		
		public BPMeasurement(){
			
		}
		
		public int getHigh() {
			return high;
		}

		public void setHigh(int high) {
			this.high = high;
		}

		public int getLow() {
			return low;
		}

		public void setLow(int low) {
			this.low = low;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

	}

}
