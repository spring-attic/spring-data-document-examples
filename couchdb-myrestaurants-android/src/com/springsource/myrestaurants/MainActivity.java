package com.springsource.myrestaurants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.springsource.myrestaurants.activities.MainTabWidget;

public class MainActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.signin);

		findViewById(R.id.signin_button).setOnClickListener(new OnClickListener() {
			public void onClick(final View view) {
				// uncomment the following line (and comment the line after it) to enable the OAuth stuff
				//startActivity(new Intent(SignInActivity.this, OAuthActivity.class));
				startActivity(new Intent(MainActivity.this, MainTabWidget.class));
				finish();
			}
		});
		/*
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		TextView view = new TextView(this);
		view.append("Running tests...\n\n");
		setContentView(view);
		try {
			RestaurantDao restDao = new RestaurantDao();
			List<Restaurant> rests = restDao.findAllRestaurants();
			for (Restaurant restaurant : rests) {
				view.append(restaurant.toString());
			}
			view.append("Done!");
		} catch (Exception e) {
			view.append(e.toString());
			Log.e("rest-template", "this is bad", e);
		}*/
	}
}