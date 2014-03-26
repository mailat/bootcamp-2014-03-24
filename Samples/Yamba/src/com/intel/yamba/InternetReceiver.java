package com.intel.yamba;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

public class InternetReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("Yamba", "starting using the InternetReceiver the service");
		
		boolean isNetworkDown = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
		if (isNetworkDown)
		{
			Log.d("Yamba", "InternetReceiver - stop the service");
			context.stopService(new Intent(context, UpdaterService.class));
		}
		else
		{
			Log.d("Yamba", "InternetReceiver - start the service");
			context.startService(new Intent(context, UpdaterService.class));
		}
	}

}
