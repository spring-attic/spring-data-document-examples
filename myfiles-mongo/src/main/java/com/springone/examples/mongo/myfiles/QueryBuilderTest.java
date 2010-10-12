package com.springone.examples.mongo.myfiles;

import java.net.UnknownHostException;

import org.junit.Test;
import org.springframework.datastore.document.mongodb.query.BasicQuery;
import org.springframework.datastore.document.mongodb.query.QueryBuilder;
import org.springframework.datastore.document.mongodb.query.SortSpecification.SortOrder;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

public class QueryBuilderTest {

	@Test
	public void q1() {

		QueryBuilder q = new QueryBuilder();
		q.find("name").gte("M").lte("T").and("age").not().gt(22);
		q.fields().exclude("address");
		q.slice().on("orders", 10);
		q.sort().on("name", SortOrder.ASCENDING);
		q.limit(50);
		System.out.println("Q: " + q.build().getQueryObject());

	}
	
	@Test
	public void q2() {

		QueryBuilder q = new QueryBuilder();
		q.find("name").is("Thomas");
		q.find("age").not().mod(22, 2);
		System.out.println("Q: " + q.build().getQueryObject());

	}

	@Test
	public void q3() {

		QueryBuilder q = new QueryBuilder();
		q.or(
				new QueryBuilder().find("name").is("Sven").and("age").lt(50).build(), 
				new QueryBuilder().find("age").lt(50).build(),
				new BasicQuery("{'name' : 'Thomas'}")
		);
//		q.or(new QueryBuilder().find("age").lt(10).build(), new QueryBuilder().find("age").gt(50).build());
		q.sort().on("name", SortOrder.ASCENDING);
		System.out.println("Q: " + q.build().getQueryObject());
		
		System.out.println("P: " + JSON.parse("{ \"$or\" : [ { \"name\" : \"Sven\" , \"age\" : { \"$lt\" : 50}} , { \"age\" : { \"$lt\" : 50}} , { \"name\" : \"Thomas\"}]}, { \"$or\" : [ { \"age\" : { \"$lt\" : 10}} , { \"age\" : { \"$gt\" : 50}}]}"));
		System.out.println("P: " + JSON.parse("{\"$or\" : [{\"size\":{\"$gt\":100}}]}, {\"$or\" : [{\"size\":{\"$lt\":100}}]}"));
	}
	
	@Test
	public void m1() throws UnknownHostException, MongoException {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("test");
		System.out.println(db.getCollectionNames());
	}
	
}
