package org.springframework.data.mongodb.examples.hello;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.document.mongodb.MongoTemplate;
import org.springframework.data.mongodb.examples.hello.domain.Account;
import org.springframework.data.mongodb.examples.hello.domain.Person;
import org.springframework.stereotype.Repository;

import com.mongodb.DBCollection;

@Repository
public class HelloMongo {
	
	@Autowired
	MongoTemplate mongoTemplate;

	public void run() {
		if (mongoTemplate.getCollectionNames().contains("HelloMongo")) {
			mongoTemplate.dropCollection("HelloMongo");
		}
		
		mongoTemplate.createCollection("HelloMongo");

		Person test = createTestPerson();
		
		mongoTemplate.insert(test);
		
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
