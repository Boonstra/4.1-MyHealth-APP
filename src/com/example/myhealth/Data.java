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
	private static String dataURL = "http://10.0.2.2/myhealth/api/login";
	private static String measurementsDataURL = "http://10.0.2.2/myhealth/api/measurements";
	private JSONObject json;
	
	private static List<NameValuePair> params = new ArrayList<NameValuePair>();
	
	/* Alle mogelijke acties in de API */
	private static final String ACTION_LOGIN = "login";
	private static final String ACTION_GET_MES = "getMeasurements";
	private static final String ACTION_ADD_MES = "addMeasurement";
	private static final String ACTION_DEL_MES = "deleteMeasurement";
	private static final String ACTION_UPLOAD_TEST = "uploadTest";
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
		
//		JSONObject json = new JSONObject();
//		JSONObject user = new JSONObject();
//		json.put("message", "success");
//		user.put("id", 1);
//		json.putOpt("user", user);
		

		JSONObject json = jParser.makeHttpRequest(dataURL + "/" + ACTION_LOGIN, "GET", params);
		System.out.println(json.getString("message"));
		return json;
	}

	/**
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject actionGetMeasurements(String dateFrom, String dateTo) throws JSONException {		
		setParams();
		
		params.add(new BasicNameValuePair("dateFrom", dateFrom));
		params.add(new BasicNameValuePair("dateTo", dateTo));

		return jParser.makeHttpRequest(dataURL + "/" + ACTION_GET_MES, "GET", params);
	}

	/**
	 * @param temp
	 */
	public static void actionAddMeasurement() {
		setParams();
		
		//params.add(new BasicNameValuePair("temp", temp));
		
		jParser.makeHttpRequest(dataURL + "/" + ACTION_ADD_MES, "GET", params);
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
	
}
