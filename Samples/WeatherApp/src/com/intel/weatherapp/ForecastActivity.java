package com.intel.weatherapp;

import java.util.ArrayList;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.widget.ArrayAdapter;

public class ForecastActivity extends ListActivity {

    String loadForecastUrl ;
    private String response ="";
	ArrayList<String> list;
	ArrayAdapter<String> adapter;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("Weather", "second activity onCreate");

        //setup the list activity
		list = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list);
		setListAdapter(adapter);
		
        //read the passed value
        Intent intent = getIntent();
        String city = intent.getStringExtra("city");
        
        //prepare the JSON url for the weather
        city = city.replaceAll(" ", "%20");
        //get the forecast of the next 5 days
        loadForecastUrl = "http://api.openweathermap.org/data/2.5/forecast?q=" + city 
        		+ "&ru&units=metric";
        
        //the right way to get the weather
        new WeatherReaderUpdater().execute(loadForecastUrl);
    }
     
    private class WeatherReaderUpdater extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {

            try {
                // transporter for our in->out call
                HttpClient client = new DefaultHttpClient();
                HttpGet httpget = new HttpGet(loadForecastUrl);

                response = client.execute(httpget, new BasicResponseHandler());
                Log.d("Weather", "response back: " + response);
            } catch (Throwable e) {
                e.printStackTrace();
                Log.d("Weather", "doInBackground exception:" + e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
        	Log.d("Weather", "onPosetExecute");
			try {
				JSONObject jobject = new JSONObject(response);
				int forecastCount = jobject.getInt("cnt");
				Log.d("Weather","Forecast count is " + String.valueOf(forecastCount));

				JSONArray jArray = jobject.getJSONArray("list");
				list.clear();
				
				JSONObject jObject;
				for (int i = 0; i < jArray.length(); i++) {
					jObject = jArray.getJSONObject(i);

					list.add(jObject.getString("dt_txt") + " temperature is "
							+ jObject.getJSONObject("main").getString("temp")
							+ " Celsius");
				}
				adapter.notifyDataSetChanged();

			} catch (Throwable e) {
				Log.d("Weather", "onPostExecute exception: " + e.getMessage());
			}
        }
    }   
}
