package com.intel.yamba;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("Yamba", "starting using the BootReceiver the service");
		
		//start the service
		context.startService(new Intent(context, UpdaterService.class));
	}

}
