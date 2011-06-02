package org.springframework.data.mongodb.examples.hello;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.document.mongodb.MongoTemplate;
import org.springframework.data.mongodb.examples.hello.domain.Account;
import org.springframework.data.mongodb.examples.hello.domain.Person;
import org.springframework.stereotype.Repository;

@Repository
public class HelloMongo {
	
	@Autowired
	MongoTemplate mongoTemplate;

	public void run() {
		
		if (mongoTemplate.collectionExists(Person.class)) {
			mongoTemplate.dropCollection(Person.class);
		}
		
		mongoTemplate.createCollection(Person.class);

		Person p = new Person("John", 39);
		Account a = new Account("1234-59873-893-1", Account.Type.SAVINGS, 123.45D);
		p.getAccounts().add(a);
		
		mongoTemplate.insert(p);
		
		List<Person> results = mongoTemplate.findAll(Person.class);
		System.out.println("Results: " + results);
	}

}
