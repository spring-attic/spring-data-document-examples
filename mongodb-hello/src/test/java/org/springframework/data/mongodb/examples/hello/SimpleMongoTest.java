package org.springframework.data.mongodb.examples.hello;

import static org.junit.Assert.*;
import static org.springframework.data.mongodb.core.query.Criteria.*;
import static org.springframework.data.mongodb.core.query.Query.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoCollectionUtils;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
	MongoOperations operations;

	@Before
	public void setUp() {
		if (operations.getCollectionNames().contains("MyNewCollection")) {
			operations.dropCollection("MyNewCollection");
		}

		String collectionName = MongoCollectionUtils.getPreferredCollectionName(Person.class);
		if (operations.getCollectionNames().contains(collectionName)) {
			operations.dropCollection(collectionName);
		}
		operations.createCollection(collectionName);
	}

	@Test
	public void createAndDropCollection() {
		assertFalse(operations.getCollectionNames().contains("MyNewCollection"));

		/* create a new collection */
		DBCollection collection = null;
		if (!operations.getCollectionNames().contains("MyNewCollection")) {
			collection = operations.createCollection("MyNewCollection");
		}

		assertNotNull(collection);
		assertTrue(operations.getCollectionNames().contains("MyNewCollection"));

		operations.dropCollection("MyNewCollection");

		assertFalse(operations.getCollectionNames().contains("MyNewCollection"));
	}

	@Test
	public void createAnIndex() {
		String collectionName = MongoCollectionUtils.getPreferredCollectionName(Person.class);
		if (!operations.getCollectionNames().contains(collectionName)) {
			operations.createCollection(collectionName);
		}
		operations.ensureIndex(new Index().on("name", Order.ASCENDING), collectionName);
	}

	@Test
	public void saveAndRetrieveDocuments() {
		String collectionName = operations.getCollectionName(Person.class);

		Person p = new Person("Bob", 33);
		operations.insert(p);

		assertEquals(1, operations.getCollection(collectionName).count());

		Person qp = operations.findOne(query(where("id").is(p.getId())), Person.class);

		assertNotNull(qp);
		assertEquals(p.getName(), qp.getName());
	}

	@Test
	public void queryingForDocuments() {
		String collectionName = MongoCollectionUtils.getPreferredCollectionName(Person.class);

		Person p1 = new Person("Bob", 33);
		p1.addAccount(new Account("198-998-2188", Account.Type.SAVINGS, 123.55d));
		operations.insert(p1);
		Person p2 = new Person("Mary", 25);
		p2.addAccount(new Account("860-98107-681", Account.Type.CHECKING, 400.51d));
		operations.insert(p2);
		Person p3 = new Person("Chris", 68);
		p3.addAccount(new Account("761-002-8901", Account.Type.SAVINGS, 10531.00d));
		operations.insert(p3);
		Person p4 = new Person("Janet", 33);
		p4.addAccount(new Account("719-100-0019", Account.Type.SAVINGS, 1209.10d));
		operations.insert(p4);

		assertEquals(4, operations.getCollection(collectionName).count());

		List<Person> result = operations.find(
				new Query(where("age").lt(50).and("accounts.balance").gt(1000.00d)),
				Person.class);

		System.out.println(result);
		assertNotNull(result);
		assertEquals(1, result.size());
	}

	@Test
	public void updatingDocuments() {
		String collectionName = operations.getCollectionName(Person.class);

		Person p1 = new Person("Bob", 33);
		p1.addAccount(new Account("198-998-2188", Account.Type.SAVINGS, 123.55d));
		operations.insert(p1);
		Person p2 = new Person("Mary", 25);
		p2.addAccount(new Account("860-98107-681", Account.Type.CHECKING, 400.51d));
		operations.insert(p2);
		Person p3 = new Person("Chris", 68);
		p3.addAccount(new Account("761-002-8901", Account.Type.SAVINGS, 10531.00d));
		operations.insert(p3);
		Person p4 = new Person("Janet", 33);
		p4.addAccount(new Account("719-100-0019", Account.Type.SAVINGS, 1209.10d));
		operations.insert(p4);

		assertEquals(4, operations.getCollection(collectionName).count());

		WriteResult wr = operations.updateMulti(
				query(where("accounts.accountType").is(Account.Type.SAVINGS.name())),
				new Update().inc("accounts.$.balance", 50.00),
				Person.class);

		assertNotNull(wr);
		assertEquals(3, wr.getN());
	}
}
