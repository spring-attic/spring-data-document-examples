package org.springframework.data.mongodb.examples.hello;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.data.document.mongodb.query.Criteria.where;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.document.mongodb.MongoTemplate;
import org.springframework.data.document.mongodb.query.Index;
import org.springframework.data.document.mongodb.query.Order;
import org.springframework.data.document.mongodb.query.Query;
import org.springframework.data.document.mongodb.query.Update;
import org.springframework.data.mongodb.examples.hello.domain.Account;
import org.springframework.data.mongodb.examples.hello.domain.Person;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.DBCollection;
import com.mongodb.WriteResult;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SimpleMongoTest {

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Before
	public void setUp() {
		if (mongoTemplate.getCollectionNames().contains("MyNewCollection")) {
			mongoTemplate.dropCollection("MyNewCollection");
		}
		if (mongoTemplate.getCollectionNames().contains("MyCollection")) {
			mongoTemplate.dropCollection("MyCollection");
		}
		mongoTemplate.createCollection("MyCollection");
	}
	
	@Test
	public void createAndDropCollection() {
		assertFalse(mongoTemplate.getCollectionNames().contains("MyNewCollection"));
		
		/* create a new collection */
		DBCollection collection = null;
		if (!mongoTemplate.getCollectionNames().contains("MyNewCollection")) {
			collection = mongoTemplate.createCollection("MyNewCollection");
		}
		
		assertNotNull(collection);
		assertTrue(mongoTemplate.getCollectionNames().contains("MyNewCollection"));
		
		mongoTemplate.dropCollection("MyNewCollection");
		
		assertFalse(mongoTemplate.getCollectionNames().contains("MyNewCollection"));
	}
	
	@Test
	public void createAnIndex() {
		if (!mongoTemplate.getCollectionNames().contains("MyCollection")) {
			mongoTemplate.createCollection("MyCollection");
		}
		mongoTemplate.ensureIndex("MyCollection", new Index().on("name", Order.ASCENDING));
	}
	
	@Test
	public void saveAndRetrieveDocuments() {

		Person p = new Person("Bob", 33);
		mongoTemplate.insert("MyCollection", p);
		
		assertEquals(1, mongoTemplate.getCollection("MyCollection").count());
		
		Person qp = mongoTemplate.findOne("MyCollection", new Query(where("id").is(p.getId())), Person.class);
		
		assertNotNull(qp);
		assertEquals(p.getName(), qp.getName());
	}

	@Test
	public void queryingForDocuments() {

		Person p1 = new Person("Bob", 33);
		p1.addAccount(new Account("198-998-2188", Account.Type.SAVINGS, 123.55d));
		mongoTemplate.insert(p1);
		Person p2 = new Person("Mary", 25);
		p2.addAccount(new Account("860-98107-681", Account.Type.CHECKING, 400.51d));
		mongoTemplate.insert(p2);
		Person p3 = new Person("Chris", 68);
		p3.addAccount(new Account("761-002-8901", Account.Type.SAVINGS, 10531.00d));
		mongoTemplate.insert(p3);
		Person p4 = new Person("Janet", 33);
		p4.addAccount(new Account("719-100-0019", Account.Type.SAVINGS, 1209.10d));
		mongoTemplate.insert(p4);
		
		assertEquals(4, mongoTemplate.getCollection("HelloMongo").count());
		
		List<Person> result = mongoTemplate.find(
				new Query(where("age").lt(50)).and(where("accounts.balance").gt(1000.00d)), 
				Person.class);
		
		System.out.println(result);
		assertNotNull(result);
		assertEquals(1, result.size());
	}

	@Test
	public void updatingDocuments() {

		Person p1 = new Person("Bob", 33);
		p1.addAccount(new Account("198-998-2188", Account.Type.SAVINGS, 123.55d));
		mongoTemplate.insert(p1);
		Person p2 = new Person("Mary", 25);
		p2.addAccount(new Account("860-98107-681", Account.Type.CHECKING, 400.51d));
		mongoTemplate.insert(p2);
		Person p3 = new Person("Chris", 68);
		p3.addAccount(new Account("761-002-8901", Account.Type.SAVINGS, 10531.00d));
		mongoTemplate.insert(p3);
		Person p4 = new Person("Janet", 33);
		p4.addAccount(new Account("719-100-0019", Account.Type.SAVINGS, 1209.10d));
		mongoTemplate.insert(p4);
		
		assertEquals(4, mongoTemplate.getCollection("HelloMongo").count());
		
		WriteResult wr = mongoTemplate.updateMulti(
				new Query(where("accounts.accountType").not().is(Account.Type.SAVINGS)),
				new Update().inc("accounts.$.balance", 50.00));
		
		System.out.println(wr);
		assertNotNull(wr);
		assertEquals(3, wr.getN());	
	}
}
