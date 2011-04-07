package org.springframework.data.mongodb.examples.custsvc.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.examples.custsvc.domain.Customer;
import org.springframework.data.mongodb.examples.custsvc.domain.SurveyInfo;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CustomerRepositoryTests {
	
	static private Long idUsed;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Transactional
	@Test
	public void testInsertNewEntity() {
		Customer c = new Customer();
		c.setFirstName("Sven");
		c.setLastName("Svensson");
		customerRepository.save(c);
		List<Customer> results = customerRepository.findAll();
		Assert.assertEquals(1, results.size());
	}

	@Transactional
	@Rollback(false)
	@Test
	public void testInsertOneEntity() {
		Customer c = new Customer();
		c.setFirstName("Sven");
		c.setLastName("Olafsen");
		SurveyInfo surveyInfo = new SurveyInfo();
		Map<String, String> qAndA = new HashMap<String, String>();
		qAndA.put("age", "22");
		qAndA.put("married", "Yes");
		qAndA.put("citizenship", "Norwegian");
		surveyInfo.setQuestionsAndAnswers(qAndA);
		c.setSurveyInfo(surveyInfo);
		customerRepository.save(c);
		Assert.assertNotNull(c.getId());
		idUsed = c.getId();
	}

	@Transactional
	@Rollback(false)
	@Test
	public void testCheckOneEntityHasDocument() {
		Customer c = customerRepository.findOne(idUsed);
		Assert.assertNotNull(c);
		Assert.assertNotNull(c.getSurveyInfo());
		Assert.assertNotNull(c.getSurveyInfo().getQuestionsAndAnswers());
		Assert.assertEquals("22", c.getSurveyInfo().getQuestionsAndAnswers().get("age"));
	}

	@Transactional
	@Rollback(false)
	@Test
	public void testUpdateOneEntity() {
		Customer c = customerRepository.findOne(idUsed);
		Assert.assertNotNull(c);
		c.setLastName("Nilsson");
	}

	@Transactional
	@Rollback(false)
	@Test
	public void testCheckOneEntity() {
		Customer c = customerRepository.findOne(idUsed);
		Assert.assertNotNull(c);
		Assert.assertEquals("Nilsson", c.getLastName());
	}

	@Transactional
	@Rollback(false)
	@Test
	public void testDeleteOneEntity() {
		Customer c = customerRepository.findOne(idUsed);
		customerRepository.delete(c);
	}

	@Transactional
	@Rollback(false)
	@Test
	public void testCheckOneEntityIsGone() {
		Customer c = customerRepository.findOne(idUsed);
		Assert.assertNull(c);
	}
}
