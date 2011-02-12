package com.springsource.myrestaurants.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class InfoActivity extends Activity {
	
	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		TextView textview = new TextView(this);
	    textview.setText("SpringOne CouchDB Demo version 0.1");
	    setContentView(textview);
	}
}
