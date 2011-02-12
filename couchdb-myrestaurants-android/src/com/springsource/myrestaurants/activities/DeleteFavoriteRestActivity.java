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

public class DeleteFavoriteRestActivity extends Activity {

	private Restaurant rest;
	

	// ***************************************
	// Activity methods
	// ***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (FavoritesListingActivity.SelectedRestForDeletion == null) {
			TextView view = new TextView(this);
			view.append("nothing selected");
			setContentView(view);
			return;
		}

		setContentView(R.layout.remove_event_details);

		final TextView textViewEventName = (TextView) findViewById(R.id.event_details_textview_name);
		final TextView textViewEventDate = (TextView) findViewById(R.id.event_details_textview_date);
		final TextView textViewEventLocation = (TextView) findViewById(R.id.event_details_textview_location);

		// ---Button view---
		Button btnOpen = (Button) findViewById(R.id.rest_fav_button);
		btnOpen.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				UserAccountDao userDao = new UserAccountDao();
				boolean ok = userDao.removeFromFavorites(null, rest);
				if (ok) {
					Toast.makeText(getBaseContext(),
							"Removed " + rest.getName() ,
							Toast.LENGTH_SHORT).show();
					//TODO update previous list adapter.
					FavoritesListingActivity.FavoriteArrayAdapter.remove(rest.getName());
				} else {
					Toast.makeText(getBaseContext(),
							"Restaurant was not a favorite.",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		RestaurantDao restDao = new RestaurantDao();
		rest = restDao.findRestaurantByName(FavoritesListingActivity.SelectedRestForDeletion );

		if (rest != null) {
			textViewEventName.setText(rest.getName());
			textViewEventDate.setText(rest.getCity() + "," + rest.getState());
			textViewEventLocation.setText(rest.getZipCode());
		} else {
			textViewEventName.setText("Could not find restaurant with name = "
					+ FavoritesListingActivity.SelectedRestForDeletion);
		}
	}
}
