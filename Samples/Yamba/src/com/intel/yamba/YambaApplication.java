package com.intel.yamba;

import com.marakana.android.yamba.clientlib.YambaClient;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

public class YambaApplication extends Application implements OnSharedPreferenceChangeListener {

	SharedPreferences prefs;
	public YambaClient yambaClient;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		Log.d("Yamba", "onCreate");
		
		//get the initial values from preferences
		prefs = PreferenceManager.getDefaultSharedPreferences(this);		
		prefs.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		
		Log.d("Yamba", "onTerminate");
	}
	
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,String key) {
		this.yambaClient = null;
	}
	
	public synchronized YambaClient getTwitter()
	{
		if ( this.yambaClient == null)
		{
			String username =  prefs.getString("username", null);
			String password = prefs.getString("password", null);	

			if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password))
			{
				this.yambaClient = new YambaClient(username, password);
			}

		}
		return this.yambaClient;
	}

}
