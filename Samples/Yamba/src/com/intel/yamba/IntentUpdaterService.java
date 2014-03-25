package com.intel.yamba;

import java.util.List;
import com.marakana.android.yamba.clientlib.YambaClient.Status;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class IntentUpdaterService extends IntentService {

	public IntentUpdaterService() {
		super("IntentUpdaterService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		//get the tweets from yamba.marakana.com
		try {
			List<Status> timeline = ((YambaApplication) getApplication()).getTwitter().getTimeline(20);
		
			 //parse the values
			for (Status status : timeline)
				Log.d("Yamba", status.getUser() + ": " + status.getMessage() +
						"-" + status.getCreatedAt());
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
