package com.example.myhealth;

import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;

import com.example.myhealth.Measurements.LoadAllMeasurements;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class Device extends Menu_Activity {

	public Bluetooth bluetooth;
	protected Thread thread;
	private InputStream inputStream;
	public TextView output;
	//private TextView bloodPressure, pulse, ecg;
	//private int numberBloodPressure = 0, numberPulse = 0, numberEcg = 0;
	private ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.device_layout);
		
		new LoadDevice().execute();
		
		output = (TextView) findViewById(R.id.device_output);
		
//		bloodPressure = (TextView) findViewById(R.id.device_bp);
//		pulse = (TextView) findViewById(R.id.device_pu);
//		ecg = (TextView) findViewById(R.id.device_ecg);
		
		bluetooth = new Bluetooth();
		bluetooth.isEnabled();
		inputStream = bluetooth.setupConnection();

	}
	
	@Override
	protected void onPause() {
		super.onPause();
		//output.append("\n...in onPause()...");
		
		((BluetoothListen) thread).stopThread();
		bluetooth.closeConnection();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		//output.append("\n...in onResume()...");
		
		thread = new BluetoothListen(this, inputStream);
		thread.start();	
		output.setText("\n...Receiving...");
	}
	
	class LoadDevice extends AsyncTask<String, String, String> {
		 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Device.this);
            pDialog.setMessage("Loading device. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
		@Override
		protected String doInBackground(String... params) {
			try {
				// Simulate network access.
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				return null;
			}

			return null;
		}
 
		 protected void onPostExecute(String file_url) {
	           
	            pDialog.dismiss();
	            
	        }
 
    }


	public void parseData(String data) throws NumberFormatException, JSONException {
		String[] dataArray = data.split(";");
		System.out.println("--" + dataArray[0]);
		
		Data.setPrefs(getApplicationContext().getSharedPreferences("myPrefs", 0));
		
		for ( String dataString : dataArray) {
			String[] dataArray2 = dataString.split("/");
			
			if (dataArray2[0].equals("bp")) {

				Data.actionAddMeasurementBloodPressure(dataArray2[1], Integer.parseInt(dataArray2[2]), Integer.parseInt(dataArray2[3]));
				//System.out.println("aantal:" + numberBloodPressure);
				//numberBloodPressure ++;
				//System.out.println("aantal:" + numberBloodPressure);
				//bloodPressure.setText("asdasdsad");
				
			}else if (dataArray2[0].equals("pu")) {
				Data.actionAddMeasurementPulse(dataArray2[1], Integer.parseInt(dataArray2[2]));
				//numberPulse ++;
				//pulse.setText(numberPulse + " ");
			}else if (dataArray2[0].equals("ecg")) {
				//Data.uploadMeasurement(ecg); TODO
			}
		}
		
	}

}
