package com.intel.weatherapp;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.d("Weather", "MainActivity - onCreate");
		
		// System.setProperty("http.proxyHost", "proxy here");
		// System.setProperty("http.proxyPort", "port here");
	}
	
	public void showForecast (View v)
	{
		//get the city name
		EditText cityText = (EditText) findViewById(R.id.cityText);
		
		//start the forecast activity
		Intent intent = new Intent(this, ForecastActivity.class);
		intent.putExtra("city", cityText.getText().toString());
		startActivity(intent);
	}
	
	public void showWeather (View v)
	{
		//get the city name
		EditText cityText = (EditText) findViewById(R.id.cityText);
		
		//start the weather activity
		Intent intent = new Intent(this, WeatherActivity.class);
		intent.putExtra("city", cityText.getText().toString());
		startActivity(intent);
	}

}
