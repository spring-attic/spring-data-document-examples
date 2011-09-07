package com.springone.myrestaurants.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.document.mongodb.MongoOperations;
import org.springframework.data.document.mongodb.MongoTemplate;
import org.springframework.data.document.mongodb.SimpleMongoDbFactory;

import com.mongodb.Mongo;

@Configuration
public class MongoConfiguration {

	@Bean
	public MongoOperations mongoTemplate() throws Exception {

		Mongo m = new Mongo();
		MongoOperations operations = new MongoTemplate(new SimpleMongoDbFactory(m, "mvc"));

		if (!operations.collectionExists("mvc")) {
			operations.createCollection("mvc");
		}
		if (!operations.collectionExists("counters")) {
			operations.createCollection("counters");
		}

		return operations;
	}
}
