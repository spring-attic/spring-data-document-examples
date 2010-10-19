package com.springone.myrestaurants.dao;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.springone.myrestaurants.domain.UserAccount;

public class UserAccountDaoTests {

	private UserAccountDao userAccountDao;

	@Before
	public void setUp() {
		userAccountDao = new UserAccountDao();
	}
	
	@Test
	public void resetFavorites() {
		UserAccount u =  userAccountDao.findByName("demouser");
		u.getFavorites().clear();
		userAccountDao.persist(u);
	}
	
	@Test
	public void readUser() {
		
		
		UserAccount u =  userAccountDao.findByName("demouser");
		assertBasicPropertyValues(u);
		u = userAccountDao.findUserAccount(1L);
		assertBasicPropertyValues(u);
		
		System.out.println(u);
		
		
		int originalFavoriteSize = u.getFavorites().size();
		String revisionOld = u.getRevision();
		
		u.getFavorites().add("4");
		
		userAccountDao.persist(u);
		//u =  userAccountDao.findByName("demouser");
		System.out.println(u);
		
		Assert.assertEquals(originalFavoriteSize+1, u.getFavorites().size());
		Assert.assertNotSame("revision should not be the same", revisionOld, u.getRevision());
		
		
		

		
		
			//restTemplate.getForObject("http://127.0.0.1:5984/spring_demo/demouser",UserAccount.class);
		//System.out.println("user = " + u);
		
		/*
		System.out.println(u);		
		int[] favorites = u.getFavorites();
		List<Integer> favList = Utils.convertToList(favorites);
		favList.add(4);
		int[] newList = Utils.convertToPrimArray(favList);
		u.setFavorites(newList);
		*/
		/*
		HttpEntity<User> response = restTemplate.exchange(
				"http://127.0.0.1:5984/spring_demo/demouser", HttpMethod.PUT,
				new HttpEntity(new HttpHeaders()), User.class);
		System.out.println("http put response = " + response.getBody());*/
		
		/*
		restTemplate.put("http://127.0.0.1:5984/spring_demo/demouser", u);
		
		u = restTemplate.getForObject("http://127.0.0.1:5984/spring_demo/demouser",User.class);
		System.out.println("user = " + u);*/
	}

	private void assertBasicPropertyValues(UserAccount u) {
		Assert.assertEquals(u.getUserName(), "demouser");
		Assert.assertEquals(u.getFirstName(), "Demo");
		Assert.assertEquals(u.getLastName(), "User");
	}
}
