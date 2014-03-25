package com.intel.yamba;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class StatusActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.status, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId())
		{
			case R.id.action_settings:
				startActivity(new Intent(this, PrefsActivity.class));
				break;
			case R.id.action_start:
				startService(new Intent(StatusActivity.this, UpdaterService.class));
				break;
			case R.id.action_stop:
				stopService(new Intent(StatusActivity.this, UpdaterService.class));
				break;
		}
		
		return true;
	}

	public void postTwitterUpdate(View v) {
		// System.setProperty("http.proxyHost", "proxy here");
		// System.setProperty("http.proxyPort", "port here");
		
		// we got reference to the EditText and we read the status
		EditText editText = (EditText) findViewById(R.id.editText);

		//call the AsyncTask poster to send the text
		new PostToTwitter().execute(editText.getText().toString());

	}

	class PostToTwitter extends AsyncTask<String, Integer, String> {
		private ProgressDialog progress;
		
		@Override
		protected String doInBackground(String... statuses) {

			try {
				// post on Twitter
				((YambaApplication) getApplication()).getTwitter().postStatus(statuses[0]);
	
				return "Posted the text" + statuses[0];
			} catch (Throwable e) {
				e.printStackTrace();
				Log.d("Yamba", e.getMessage());
				return "Error on posting" + e.getMessage();
			}
		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();
			Toast.makeText(StatusActivity.this, result, Toast.LENGTH_LONG).show();
		}

		@Override
		protected void onPreExecute() {
			progress = ProgressDialog.show(StatusActivity.this, "Posting to yamba server", "Please wait ....");
		}

	}



}
