package com.springsource.myrestaurants.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.springsource.myrestaurants.R;
import com.springsource.myrestaurants.dao.RestaurantDao;
import com.springsource.myrestaurants.dao.UserAccountDao;
import com.springsource.myrestaurants.models.Restaurant;

public class RestDetailsActivity extends Activity {

	private Restaurant rest;

	// ***************************************
	// Activity methods
	// ***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (RestListingActivity.SelectedRest == null) {
			TextView view = new TextView(this);
			view.append("nothing selected");
			setContentView(view);
			return;
		}

		setContentView(R.layout.event_details);

		final TextView textViewEventName = (TextView) findViewById(R.id.event_details_textview_name);
		final TextView textViewEventDate = (TextView) findViewById(R.id.event_details_textview_date);
		final TextView textViewEventLocation = (TextView) findViewById(R.id.event_details_textview_location);

		// ---Button view---
		Button btnOpen = (Button) findViewById(R.id.rest_fav_button);
		btnOpen.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				UserAccountDao userDao = new UserAccountDao();
				boolean ok = userDao.addToFavorites(null, rest);
				if (ok) {
					Toast.makeText(getBaseContext(),
							"Added " + RestListingActivity.SelectedRest,
							Toast.LENGTH_SHORT).show();
					FavoritesListingActivity.FavoriteArrayAdapter.add(rest.getName());
				} else {
					Toast.makeText(getBaseContext(),
							"Not able to add restaurant.  Sorry it didn't work out.",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		RestaurantDao restDao = new RestaurantDao();
		rest = restDao.findRestaurantByName(RestListingActivity.SelectedRest);

		if (rest != null) {
			textViewEventName.setText(rest.getName());
			textViewEventDate.setText(rest.getCity() + "," + rest.getState());
			textViewEventLocation.setText(rest.getZipCode());
		} else {
			textViewEventName.setText("Could not find restaurant with name = "
					+ RestListingActivity.SelectedRest);
		}
	}
}
