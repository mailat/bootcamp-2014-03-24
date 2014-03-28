package com.intel.weatherapp;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class WeatherActivity extends Activity {
	TextView weatherText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather_main);

		Log.d("Weather", "WeatherActivity - onCreate");

		// read the passed value
		Intent intent = getIntent();
		String city = intent.getStringExtra("city");
		city = city.replaceAll(" ", "%20");

		new GetWeather().execute(city);

		weatherText = (TextView) findViewById(R.id.weatherText);
	}

	class GetWeather extends AsyncTask<String, Integer, String> {
		private ProgressDialog progress;
		String response;

		@Override
		protected String doInBackground(String... urls) {

			try {
				// transporter for our in->out call
				HttpClient client = new DefaultHttpClient();
				HttpGet httpget = new HttpGet(
						"http://api.openweathermap.org/data/2.5/weather?q="
								+ urls[0] + ",ru&units=metric");

				response = client.execute(httpget, new BasicResponseHandler());
				Log.d("Weather", "response back: " + response);

				return response;
			} catch (Throwable e) {
				e.printStackTrace();
				Log.d("Yamba", e.getMessage());
				return "Error on posting" + e.getMessage();
			}
		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();
//			Toast.makeText(WeatherActivity.this, result, Toast.LENGTH_LONG)
//					.show();

			//parse the JSON and get the temperature
			try {
				StringBuffer buffer = new StringBuffer();
				JSONObject jObj = new JSONObject(response);

				// get JSONObject - coord
				JSONObject jsonObj = jObj.getJSONObject("coord");

				// get string from coord - lat, lon
				buffer.append(jsonObj.getString("lat")).append(" - ");
				buffer.append(jsonObj.getString("lon")).append(" , temp: ");

				// get JSONObject - main
				jsonObj = jObj.getJSONObject("main");
				// get temp
				buffer.append(jsonObj.getString("temp"));

				weatherText.setText(buffer.toString());

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		@Override
		protected void onPreExecute() {
			progress = ProgressDialog.show(WeatherActivity.this, "Getting weather details", "Please wait ....");
		}

	}

}
