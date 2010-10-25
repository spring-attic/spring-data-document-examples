package com.springsource.myrestaurants.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.springsource.myrestaurants.config.CouchDbConfig;
import com.springsource.myrestaurants.models.Restaurant;

public class RestaurantDao {

	private RestTemplate restTemplate;

	public RestaurantDao() {
		restTemplate = new RestTemplate();

		List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
		
		//Converter specific to processing CouchDB view queries.
		converters.add(new CouchDbMappingJacksonHttpMessageConverter());
		
		restTemplate.setMessageConverters(converters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Restaurant> findAllRestaurants() {
		
		return (List<Restaurant>) restTemplate.getForObject(CouchDbConfig.URL + "_design/demo/_view/all", Restaurant.class);
	}



	public Restaurant findRestaurant(String id) {
		//TODO query id directly.
		if (id == null)
			return null;
		List<Restaurant> rests = findAllRestaurants();
		for (Restaurant restaurant : rests) {
			if (restaurant.getId().equals(id)) {
				return restaurant;
			}
		}
		return null;
	}
	
	public Restaurant findRestaurantByName(String name) {
		//TODO query view with name as key
		if (name == null)
			return null;
		List<Restaurant> rests = findAllRestaurants();
		for (Restaurant restaurant : rests) {
			if (restaurant.getName().equals(name)) {
				return restaurant;
			}
		}
		return null;
	}
	
	public List<Restaurant> findRestaurantsByIds(List<String> ids) {
		List<Restaurant> favorites = new ArrayList<Restaurant>();
		if (ids != null) {			
			List<Restaurant> rests = findAllRestaurants();
			for (Restaurant restaurant : rests) {
				if (ids.contains(restaurant.getId())) {
					favorites.add(restaurant);
				}
			}
		}
		return favorites;
	}
	


	@SuppressWarnings("unchecked")
	public List<Restaurant> findRestaurantEntries(int firstResult,
			int maxResults) {
		
		// TODO respect bounds
		return findAllRestaurants();
		/*
		return entityManager.createQuery("select o from Restaurant o")
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();*/
	}

	public long countRestaurants() {
		
		//TODO optimize with the creation of common views for counts.
		return ((List<Restaurant>) restTemplate.getForObject(CouchDbConfig.URL + "_design/demo/_view/all", Restaurant.class)).size();
	}

}
