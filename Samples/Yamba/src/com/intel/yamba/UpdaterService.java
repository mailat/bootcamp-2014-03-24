package com.intel.yamba;

import java.util.List;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.marakana.android.yamba.clientlib.YambaClient.Status;

public class UpdaterService extends Service {
	
	private boolean runFlag = false;
	static final int DELAY = 60000; // 1min ?
	private UpdaterThread updater;
	private YambaApplication yamba;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("Yamba", "service onCreate");
		
		//our looper get initialized
		this.updater = new UpdaterThread();
		this.yamba = (YambaApplication) getApplication();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		Log.d("Yamba", "service onStartCommand");
		
		if (this.runFlag == false)
		{
			this.runFlag = true;
			this.updater.start();
		}
		
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		
		this.runFlag = false;
		this.updater.interrupt();
		this.updater = null;
		
		Log.d("Yamba", "service onDestroy");
	}
	
	private class UpdaterThread extends Thread
	{

		public UpdaterThread()
		{
			super("UpdaterService-Updater");
		}
		
		@Override
		public void run() {
			UpdaterService updaterService2 = UpdaterService.this;
			
			//I am still allowed to run? check the this.runFlag == false?
			while (updaterService2.runFlag)
			{
				try
				{
					//get the tweets from yamba.marakana.com
					List<Status> timeline = yamba.getTwitter().getTimeline(20);
							
					 //parse the values
					for (Status status : timeline)
						Log.d("Yamba", status.getUser() + ": " + status.getMessage() +
								"-" + status.getCreatedAt());

					//sleep for 1 min and wait for another batch of tweets
					Thread.sleep(DELAY);
				}
				catch(Throwable e)
				{
					//stop the looper and log the error
					//Log.d("Yamba", e.getMessage());
					updaterService2.runFlag = false;
				}
			}
		}
	}
}
