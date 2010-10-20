package com.springsource.myrestaurants.activities;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

import com.springsource.myrestaurants.R;


public class MainTabWidget extends TabActivity {
	
	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Resources res = getResources();
		TabHost tabHost = getTabHost();
		TabHost.TabSpec tabSpec;
		Intent intent;
				
		// add events tab
		intent = new Intent();
		intent.setClass(this, RestListingActivity.class);
		
		tabSpec = tabHost.newTabSpec("restaurants");
		tabSpec.setIndicator("Restaurants", res.getDrawable(R.drawable.ic_tab_events));
		tabSpec.setContent(intent);
		tabHost.addTab(tabSpec);
		
		// add profile tab
		intent = new Intent();
		intent.setClass(this, FavoritesListingActivity.class);
		
		tabSpec = tabHost.newTabSpec("favorites");
		tabSpec.setIndicator("Favorites", res.getDrawable(R.drawable.ic_tab_profile));
		tabSpec.setContent(intent);
		tabHost.addTab(tabSpec);
		
		// add info tab
		intent = new Intent();
		intent.setClass(this, RestListingActivity.class);
		
		tabSpec = tabHost.newTabSpec("info");
		tabSpec.setIndicator("Info", res.getDrawable(R.drawable.ic_tab_info));
		tabSpec.setContent(intent);
		tabHost.addTab(tabSpec);
	}
}
