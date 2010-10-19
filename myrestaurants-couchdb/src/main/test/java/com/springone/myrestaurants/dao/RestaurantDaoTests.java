package com.springone.myrestaurants.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.springone.myrestaurants.domain.Restaurant;
import com.springone.myrestaurants.web.CouchDbMappingJacksonHttpMessageConverter;


public class RestaurantDaoTests {

	private RestTemplate restTemplate;
	
	private RestaurantDao restaurantDao;

	@Before
	public void setUp() {
		restaurantDao = new RestaurantDao();


	}
	
	@Test
	public void readRestaurants() {
	
		//List<Restaurant> response = (List<Restaurant>) restTemplate.getForObject("http://localhost:5984/spring_demo/_design/demo/_view/all", Restaurant.class);
		List<Restaurant> response = restaurantDao.findAllRestaurants();
		Assert.assertEquals(50, response.size());
		System.out.println(response.get(0));
		
		Restaurant r = restaurantDao.findRestaurant(response.get(0).getId());
		Assert.assertNotNull(r);
		System.out.println(r);
		
		response = restaurantDao.findRestaurantEntries(1, 10);
		//TODO does not respect limits..
		Assert.assertEquals(50, response.size());
		
		
	}
	

}
