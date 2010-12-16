package org.springframework.data.mongodb.examples.hello;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.document.mongodb.MongoTemplate;
import org.springframework.data.mongodb.examples.hello.domain.Account;
import org.springframework.data.mongodb.examples.hello.domain.Person;
import org.springframework.stereotype.Component;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;

@Component
public class HelloMongo {
	
	@Autowired
	MongoTemplate mongoTemplate;

	public void run() {
		if (mongoTemplate.getDb().getCollectionNames().contains("HelloMongo")) {
			mongoTemplate.dropCollection("HelloMongo");
		}
		
		mongoTemplate.createCollection("HelloMongo");

		Person test = createTestPerson();
		
		mongoTemplate.insert("HelloMongo", test);
		
		DBCollection results = mongoTemplate.getCollection("HelloMongo");
		System.out.println("Results: " + results.findOne());
	}

	private Person createTestPerson() {
		Person p = new Person();
		p.setName("John");
		p.setAge(39);
		List<Account> accounts = new ArrayList<Account>();
		Account a = new Account();
		a.setAccountNumber("1234-59873-893-1");
		a.setAccountType(Account.Type.SAVINGS);
		a.setBalance(123.45D);
		accounts.add(a);
		p.setAccounts(accounts);
		return p;
	}

}
