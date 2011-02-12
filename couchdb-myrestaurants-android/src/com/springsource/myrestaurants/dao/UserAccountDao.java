package com.springsource.myrestaurants.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.springsource.myrestaurants.config.CouchDbConfig;
import com.springsource.myrestaurants.models.Restaurant;
import com.springsource.myrestaurants.models.UserAccount;

public class UserAccountDao {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private RestTemplate restTemplate;

	public UserAccountDao() {

		restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();

		converters.add(new MappingJacksonHttpMessageConverter());
		restTemplate.setMessageConverters(converters);

	}

	public UserAccount findUserAccount(Long id) {
		if (id == null)
			return null;
		return (UserAccount) restTemplate.getForObject(CouchDbConfig.URL
				+ "demouser", UserAccount.class);

	}

	public UserAccount findByName(String name) {
		if (name == null)
			return null;
		return (UserAccount) restTemplate.getForObject(CouchDbConfig.URL
				+ "demouser", UserAccount.class);
	}

	public void persist(UserAccount userAccount) {
		// Note only 1 user in couchdb version for now
		restTemplate.put(CouchDbConfig.URL + "demouser", userAccount);
		userAccount.setRevision(((UserAccount) restTemplate.getForObject(
				CouchDbConfig.URL + "demouser", UserAccount.class))
				.getRevision());

	}
	
	public boolean addToFavorites(UserAccount u, Restaurant r) {
		UserAccount user = (UserAccount) restTemplate.getForObject(CouchDbConfig.URL +  "demouser", UserAccount.class);
		if (user != null && r != null) {
			if (!user.getFavorites().contains(r.getId())) {
				
				user.getFavorites().add(r.getId());
				persist(user);
				logger.debug("Added " + r.getId() + "to favorites for user = " + user.getUserName());
				return true;
			} else {
				logger.debug("Already present in list");
				return true;
			}
		}
		logger.debug("Could not find user = demouser");
		return false;
	}
	
	public boolean removeFromFavorites(UserAccount u, Restaurant r) {
		UserAccount user = (UserAccount) restTemplate.getForObject(CouchDbConfig.URL +  "demouser", UserAccount.class);
		if (user != null && r != null) {
			if (!user.getFavorites().contains(r.getId())) {
				logger.debug(r.getName() + ", Not present in list");
				logger.debug("fav list = " + user.getFavorites());
				return true;

			} else {
				user.getFavorites().remove(r.getId());
				persist(user);
				logger.debug("Removed " + r.getId() + "from favorites for user = " + user.getUserName());
				return true;
			}
		}
		logger.debug("Could not find user = demouser");
		return false;
	}
	
	public List<String> getFavoriteRestaurantsIds(UserAccount u) {
		UserAccount user = (UserAccount) restTemplate.getForObject(CouchDbConfig.URL +  "demouser", UserAccount.class);
		if (user != null) {
			return user.getFavorites();
		} else {
			logger.debug("Could not find user = demouser");
		}
		return new ArrayList<String>();
		
	}

	public UserAccount merge(UserAccount userAccount) {
		
		
		restTemplate.put(CouchDbConfig.URL + "demouser", userAccount);
		return (UserAccount) restTemplate.getForObject(CouchDbConfig.URL
				+ "demouser", UserAccount.class);

	}
}
