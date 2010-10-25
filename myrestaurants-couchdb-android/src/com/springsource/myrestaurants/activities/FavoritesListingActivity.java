package com.springsource.myrestaurants.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.springsource.myrestaurants.R;
import com.springsource.myrestaurants.controllers.NavigationManager;
import com.springsource.myrestaurants.dao.RestaurantDao;
import com.springsource.myrestaurants.dao.UserAccountDao;
import com.springsource.myrestaurants.models.Restaurant;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;


public class FavoritesListingActivity extends ListActivity {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private UserAccountDao userDao = new UserAccountDao();
	
	private RestaurantDao restDao = new RestaurantDao();
	
	public static String SelectedRestForDeletion;
	
	public static ArrayAdapter FavoriteArrayAdapter;

	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		displayFavorites();		
	}



	private void displayFavorites() {
		List<Map<String,String>> events = fetchRests();

		List<String> restNames = fetchRestNames();
		FavoriteArrayAdapter = new ArrayAdapter(this, R.layout.rests_list_item, R.id.title, restNames);
		/*
		SimpleAdapter adapter = new SimpleAdapter(
				this,
				events,
				R.layout.rests_list_item,
				new String[] { "name", "city" },
				new int[] { R.id.title, R.id.subtitle } );*/
		
		this.setListAdapter(FavoriteArrayAdapter);
	}
	

	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		displayFavorites();		
	}



	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		displayFavorites();		
	}



	//***************************************
    // ListActivity methods
    //***************************************
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
//		Intent intent = new Intent(this, EventDetailsActivity.class);
//		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		LocalActivityManager activityManager = EventsActivityGroup.group.getLocalActivityManager();
//		Window window = activityManager.startActivity("event_details", intent);
//		View view = window.getDecorView();
//		
//		EventsActivityGroup.group.replaceView(view);
		Object selectedObj = l.getItemAtPosition(position);
		FavoritesListingActivity.SelectedRestForDeletion = (String)selectedObj;
		logger.debug("selected obj = " + selectedObj);
		NavigationManager.startActivity(v.getContext(), DeleteFavoriteRestActivity.class);
	}
	
	//***************************************
    // Private methods
    //***************************************
	private List<Map<String,String>> fetchRests() {		
		List<String> ids = userDao.getFavoriteRestaurantsIds(null);
		List<Restaurant> rests = restDao.findRestaurantsByIds(ids);
		List<Map<String,String>> restList = new ArrayList<Map<String,String>>();	
		for (Restaurant restaurant : rests) {
			Map<String, String> map = new HashMap<String, String>();	
			map.put("name", restaurant.getName());
			map.put("city", restaurant.getCity());
			restList.add(map);
		}
		return restList;
	}
	
	private List<String> fetchRestNames() {		
		List<String> ids = userDao.getFavoriteRestaurantsIds(null);
		List<Restaurant> rests = restDao.findRestaurantsByIds(ids);
		List<String> restList = new ArrayList<String>();	
		for (Restaurant restaurant : rests) {
			restList.add(restaurant.getName());
		}
		return restList;
	}
	
}