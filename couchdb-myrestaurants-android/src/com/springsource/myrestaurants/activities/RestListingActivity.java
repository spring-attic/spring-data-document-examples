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
import com.springsource.myrestaurants.models.Restaurant;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;


public class RestListingActivity extends ListActivity {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	public static String SelectedRest;
	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		List<Map<String,String>> events = fetchRests();

		SimpleAdapter adapter = new SimpleAdapter(
				this,
				events,
				R.layout.rests_list_item,
				new String[] { "name", "city" },
				new int[] { R.id.title, R.id.subtitle } );
		
		this.setListAdapter(adapter);		
	}
	
	//***************************************
    // ListActivity methods
    //***************************************
	@Override
	protected void  onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
//		Intent intent = new Intent(this, EventDetailsActivity.class);
//		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		LocalActivityManager activityManager = EventsActivityGroup.group.getLocalActivityManager();
//		Window window = activityManager.startActivity("event_details", intent);
//		View view = window.getDecorView();
//		
//		EventsActivityGroup.group.replaceView(view);
		Object selectedObj = l.getItemAtPosition(position);
		RestListingActivity.SelectedRest = (String)((Map)selectedObj).get("name");
		logger.debug("selected obj = " + selectedObj);
		NavigationManager.startActivity(v.getContext(), RestDetailsActivity.class);
	}
	
	//***************************************
    // Private methods
    //***************************************
	private List<Map<String,String>> fetchRests() {
		
		RestaurantDao restDao = new RestaurantDao();
		List<Restaurant> rests = restDao.findAllRestaurants();
		List<Map<String,String>> restList = new ArrayList<Map<String,String>>();	
		for (Restaurant restaurant : rests) {
			Map<String, String> map = new HashMap<String, String>();	
			map.put("name", restaurant.getName());
			map.put("city", restaurant.getCity());
			restList.add(map);
		}
		return restList;
		
		/*
		List<Event> upcomingEvents = greenhouse.getUpcomingEvents();		
		List<Map<String,String>> eventList = new ArrayList<Map<String,String>>();		
		
		// TODO: Is there w way to populate the table from an Event instead of a Map?
		for (Event event : upcomingEvents) {
			Map<String, String> map = new HashMap<String, String>();			
			map.put("title", event.getTitle());
			map.put("groupName", event.getGroupName());
			eventList.add(map);
		}		
		
		return eventList;*/
	}
	
}