package com.intel.yamba;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class FakePrefsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.fake_layout);
		
		//TODO get from preferences the username and password
		EditText editUsername = (EditText) findViewById(R.id.editUsername);
	}
	
	public void saveCredentials (View view)
	{
		//TODO save the credentials in preferences
		finish();
	}

}
