package com.intel.yamba;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class UpdaterService extends Service {
	
	private boolean runFlag = false;
	static final int DELAY = 60000; // 1min ?
	private UpdaterThread updater;

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
		
		runFlag = false;
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
					//TODO long running from time to time operation for getting the tweets
					Log.d("Yamba","I got 10 messages from Yamba.Narakana.Com");
					
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
