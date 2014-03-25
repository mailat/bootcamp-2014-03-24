package com.intel.yamba;

import com.marakana.android.yamba.clientlib.YambaClient;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class StatusActivity extends Activity implements OnSharedPreferenceChangeListener{

	private String username = null;
	private String password = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status);
		
		//get the initial values from preferences
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);		
		username = prefs.getString("username", null);
		password = prefs.getString("password", null);	
		
		//register for the preference changes
		prefs.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.status, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			startActivity(new Intent(this, PrefsActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void postTwitterUpdate(View v) {
		// System.setProperty("http.proxyHost", "proxy here");
		// System.setProperty("http.proxyPort", "port here");
		
		// we got reference to the EditText and we read the status
		EditText editText = (EditText) findViewById(R.id.editText);

		//check if we have a username, password; if not redirect to preferences
		if (username!=null && password!=null)
		{
			//call the AsyncTask poster to send the text
			new PostToTwitter().execute(editText.getText().toString());
		}
		else
			startActivity(new Intent(this, PrefsActivity.class));
	}

	class PostToTwitter extends AsyncTask<String, Integer, String> {
		private ProgressDialog progress;
		
		@Override
		protected String doInBackground(String... statuses) {

			try {
				// post on Twitter
				YambaClient client = new YambaClient(username, password);
				client.postStatus(statuses[0]);
				return "Posted ok the text" + statuses[0];
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

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,String key) {
		if (key.equals("username"))
			username = sharedPreferences.getString(key, null);
		else if (key.equals("password"))
			password = sharedPreferences.getString(key, null);
	}

}
