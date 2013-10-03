package com.example.myhealth;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;

public class Data {
		
	private static JSONParser jParser = new JSONParser();
	private static String dataURL = "http://10.0.2.2/yii/sites/4.1-MyHealth-WEB/api";
	private JSONObject json;
	
	private static List<NameValuePair> params = new ArrayList<NameValuePair>();
	
	/* Alle mogelijke acties in de API */
	private static final String ACTION_LOGIN = "login";
	private static final String ACTION_DEL_MES = "measurement/delete";
	//private static final String ACTION_UPLOAD_TEST = "uploadTest";
	private static String ACTION_ADD_MES_BP = "bloodPressureMeasurement/add";
	private static String ACTION_ADD_MES_PU = "pulseMeasurement/add";
	//private static String ACTION_ADD_MES_ECG = "ECGMeasurement/add";
	private static String ACTION_GET_MES_BP = "bloodPressureMeasurement/";
	private static String ACTION_GET_MES_PU = "pulseMeasurement/";
	private static String ACTION_GET_MES_ECG = "ECGMeasurement/";
	
	private static SharedPreferences pref;
	
	
	public Data(SharedPreferences pref) {	
		this.pref = pref;
	}
	
	/**
	 * @param action
	 * Methode wordt uitgevoerd voor elke actie. Basis parameters worden meegegeven aan de API.
	 */
	public static void setParams() {
		params.clear();
		
		params.add(new BasicNameValuePair("username", pref.getString("username", null)));
		params.add(new BasicNameValuePair("password", pref.getString("password", null)));
	}
	
	public static void setPrefs(SharedPreferences prefs) {
		pref = prefs;
	}
	
	/**
	 * @param username
	 * @param password
	 * @return
	 * @throws JSONException 
	 */
	public static JSONObject actionLogin(String username, String password) throws JSONException {
		params.clear();
		
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));		

		JSONObject json = jParser.makeHttpRequest(dataURL + "/" + ACTION_LOGIN, "GET", params);
		System.out.println(json.getString("message"));
		return json;
	}

	/**
	 * @return
	 * @throws JSONException
	 */
	public static ArrayList<JSONObject> actionGetMeasurementBloodPressure(String dateFrom, String dateTo) throws JSONException {		
		setParams();
		
		params.add(new BasicNameValuePair("dateFrom", dateFrom));
		params.add(new BasicNameValuePair("dateTo", dateTo));
		
		JSONObject json = jParser.makeHttpRequest(dataURL + "/" + ACTION_GET_MES_BP , "GET", params);
		
		System.out.println(json.toString());
		
		return JSONArrayToArrayList(json.getJSONArray("measurements"));
	}
	
	/**
	 * @return
	 * @throws JSONException
	 */
	public static ArrayList<JSONObject> actionGetMeasurementPulse(String dateFrom, String dateTo) throws JSONException {		
		setParams();
		
		params.add(new BasicNameValuePair("dateFrom", dateFrom));
		params.add(new BasicNameValuePair("dateTo", dateTo));

		JSONObject json = jParser.makeHttpRequest(dataURL + "/" + ACTION_GET_MES_PU , "GET", params);
		
		return JSONArrayToArrayList(json.getJSONArray("measurements"));
	}
	
	/**
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject actionGetMeasurementECG(String dateFrom, String dateTo) throws JSONException {		
		setParams();
		
		params.add(new BasicNameValuePair("dateFrom", dateFrom));
		params.add(new BasicNameValuePair("dateTo", dateTo));

		return jParser.makeHttpRequest(dataURL + "/" + ACTION_GET_MES_ECG , "GET", params);
	}	

	public static void actionAddMeasurementBloodPressure(String datetime, int low, int high) {
		setParams();

		params.add(new BasicNameValuePair("datetime", datetime));
		params.add(new BasicNameValuePair("low", low + ""));
		params.add(new BasicNameValuePair("high", high + ""));	
		
		jParser.makeHttpRequest(dataURL + "/" + ACTION_ADD_MES_BP, "GET", params);
	}
	
	public static void actionAddMeasurementPulse(String datetime, int pulse) {
		setParams();
		
		params.add(new BasicNameValuePair("datetime", datetime));
		params.add(new BasicNameValuePair("pulse", pulse + ""));
		
		jParser.makeHttpRequest(dataURL + "/" + ACTION_ADD_MES_PU, "GET", params);
	}	

	
	/**
	 * @param lang
	 */
	public static void actionDeleteMeasurement(int id) {
		setParams();
		
		params.add(new BasicNameValuePair("id", "" + id));
		
		jParser.makeHttpRequest(dataURL + "/" + ACTION_DEL_MES, "GET", params);
	}	
	
	/**
	 * @return
	 * @throws JSONException
	 */
	public static String actionUploadUrineTest() throws JSONException {
		setParams();

		//JSONObject json = jParser.makeHttpRequest(dataURL, "GET", params);
		
		return null;
	}
	
	public static ArrayList<JSONObject> JSONArrayToArrayList(JSONArray array) throws JSONException {
		ArrayList<JSONObject> list = new ArrayList<JSONObject>();
		for (int i=0; i<array.length(); i++) {
		    list.add( array.getJSONObject(i) );
		}
		
		return list;    
	}
	
}

