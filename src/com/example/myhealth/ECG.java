package com.example.myhealth;

import java.util.ArrayList;

import org.achartengine.GraphicalView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ECG extends Menu_Activity {

	protected final String TAG = "ECG";

	protected SharedPreferences prefs;
	
	/**
	 * The layout in which the ECG charts are stored
	 */
	protected LinearLayout chartsLinearLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ecg_layout);

		Log.i(TAG, "ECG clicked");		

		prefs = getApplicationContext().getSharedPreferences("myPrefs", 0);

		Data.setPrefs(prefs);
		
		chartsLinearLayout = (LinearLayout) findViewById(R.id.chartsLinearLayout);

		LoadECG loadECG = new LoadECG();
		
		loadECG.setContext(this);
		
		// Load ECG data
		loadECG.execute();
	}
	
	/**
	 * Add a graphical view to the ECG activity's scroll view
	 * 
	 * @param graphicalView
	 * @param chartTitle
	 */
	protected void addChartToChartsLinearLayout(GraphicalView graphicalView, String chartTitle)
	{
		LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		// Get ECG chart view to put the graphical view in 
		View newECGChart = layoutInflater.inflate(R.layout.ecg_chart, null);
		
		// Get chart title element
		TextView chartTitleTextView = (TextView) newECGChart.findViewById(R.id.chartTitleTextView);
		
		chartTitleTextView.setText(chartTitle);

		// Get chart element
		LinearLayout linearLayout = (LinearLayout) newECGChart.findViewById(R.id.chartsLinearLayout);
		
		linearLayout.addView(graphicalView);
		
		// Add chart view to the ECG activity
		chartsLinearLayout.addView(newECGChart);
	}

	/**
	 * An asynchronous task for loading the ECG data
	 */
	private class LoadECG extends AsyncTask<String, String, JSONArray>
	{
		protected Context context;
		
		protected ProgressDialog progressDialog;
		
		/**
		 * Being able to use the context is necessary to load the ECG measurements into a chart
		 * 
		 * @param context
		 */
		protected void setContext(Context context)
		{
			this.context = context;
		}
		
		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			
			if (context == null)
			{
				return;
			}
			
			// Create a loading message
			progressDialog = new ProgressDialog(ECG.this);
			
			progressDialog.setMessage("Loading ECG. Please wait...");
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(false);
			
			progressDialog.show();
		}

		@Override
		protected JSONArray doInBackground(String... params)
		{
			if (context == null)
			{
				return null;
			}
			
			try
			{
				// Get ECG measurement through a REST request
				JSONObject result = Data.actionGetMeasurementECG(3, 0);
				
				if (result != null &&
					result.getString("message").equals("success"))
				{
					return result.getJSONArray("measurements");
				}
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(JSONArray measurements)
		{
			if (context == null)
			{
				return;
			}
			
			if (measurements != null &&
				measurements.length() > 0)
			{
				// Iterate over all measurements
				for (int i = 0; i < measurements.length(); i++)
				{
					try
					{
						// Get measurement
						JSONObject measurement = measurements.getJSONObject(i);
						
						// Build a new ECG chart using the ECGChartBuilder, using the retrieved measurement. Add the chart to the view
						addChartToChartsLinearLayout(new ECGChartBuilder(measurement.getString("value")).buildECGChart(context), "ECG Track " + (i + 1));
					}
					catch (JSONException e)
					{
						continue;
					}
				}
			}
			
			progressDialog.dismiss();
		}
	}
}
