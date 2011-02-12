package com.springone.myrestaurants.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.springone.myrestaurants.config.CouchDbConfig;
import com.springone.myrestaurants.domain.UserAccount;
import com.springone.myrestaurants.web.CouchDbMappingJacksonHttpMessageConverter;

@Repository
public class UserAccountDao {

	//@PersistenceContext
	//private EntityManager entityManager;
	
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

		// return entityManager.find(UserAccount.class, id);
	}

	public UserAccount findByName(String name) {
		if (name == null)
			return null;
		return (UserAccount) restTemplate.getForObject(CouchDbConfig.URL
				+ "demouser", UserAccount.class);

		/*
		 * Query q = entityManager.createQuery(
		 * "SELECT u FROM UserAccount u WHERE u.userName = :username");
		 * q.setParameter("username", name);
		 * 
		 * java.util.List resultList = q.getResultList(); if (resultList.size()
		 * > 0) { return (UserAccount) resultList.get(0); } return null;
		 */
	}

	public void persist(UserAccount userAccount) {
		// Note only 1 user in couchdb version for now
		restTemplate.put(CouchDbConfig.URL + "demouser", userAccount);
		userAccount.setRevision(((UserAccount) restTemplate.getForObject(
				CouchDbConfig.URL + "demouser", UserAccount.class))
				.getRevision());

	}

	public UserAccount merge(UserAccount userAccount) {
		
		
		restTemplate.put(CouchDbConfig.URL + "demouser", userAccount);
		return (UserAccount) restTemplate.getForObject(CouchDbConfig.URL
				+ "demouser", UserAccount.class);

	}
}
