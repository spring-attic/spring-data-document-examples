package org.springframework.data.mongodb.examples.hello;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.document.mongodb.MongoTemplate;
import org.springframework.data.document.mongodb.query.Criteria;
import org.springframework.data.document.mongodb.query.Query;
import org.springframework.data.mongodb.examples.hello.domain.Person;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.DBCollection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SimpleMongoTest {

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Before
	public void setUp() {
		if (mongoTemplate.getCollectionNames().contains("HelloMongo")) {
			mongoTemplate.dropCollection("HelloMongo");
		}
	}
	
	@Test
	public void createAndDropCollection() {
		assertFalse(mongoTemplate.getCollectionNames().contains("MyNewCollection"));
		
		/* create a new collection */
		DBCollection collection = mongoTemplate.createCollection("MyNewCollection");
		
		assertNotNull(collection);
		assertTrue(mongoTemplate.getCollectionNames().contains("MyNewCollection"));
		
		mongoTemplate.dropCollection("MyNewCollection");
		
		assertFalse(mongoTemplate.getCollectionNames().contains("MyNewCollection"));
	}
	
	@Test
	public void saveAndRetrieveDocuments() {

		Person p = new Person("Bob", 33);
		mongoTemplate.insert(p);
		
		assertEquals(1, mongoTemplate.getCollection("HelloMongo").count());
		
		List<Person> l = mongoTemplate.find(new Query(Criteria.where("_id").is(new ObjectId(p.getId()))), Person.class);
		assertEquals(1, l.size());
		assertEquals(p.getName(), l.get(0).getName());
	}

}
