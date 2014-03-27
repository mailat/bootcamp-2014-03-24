package com.intel.weatherapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;

public class ForecastActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.d("Weather", "ForecastActivity - onCreate");
	}
	
}
