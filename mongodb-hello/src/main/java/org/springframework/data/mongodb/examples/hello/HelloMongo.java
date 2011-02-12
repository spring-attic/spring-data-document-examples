package org.springframework.data.mongodb.examples.hello;

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

		Person p = new Person("John", 39);
		Account a = new Account("1234-59873-893-1", Account.Type.SAVINGS, 123.45D);
		p.getAccounts().add(a);
		
		mongoTemplate.insert(p);
		
		DBCollection results = mongoTemplate.getCollection("HelloMongo");
		System.out.println("Results: " + results.findOne());
	}

}
