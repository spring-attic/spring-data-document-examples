package com.springone.myrestaurants.config;

import com.mongodb.Mongo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.document.mongodb.MongoTemplate;
import org.springframework.data.document.mongodb.SimpleMongoDbFactory;

@Configuration
public class MongoConfiguration {

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {

		Mongo m = new Mongo();
		MongoTemplate mongoTemplate = new MongoTemplate(new SimpleMongoDbFactory(m, "mvc"));
		if (!mongoTemplate.collectionExists("mvc")) {
			mongoTemplate.createCollection("mvc");
		}
		if (!mongoTemplate.collectionExists("counters")) {
			mongoTemplate.createCollection("counters");
		}
		return mongoTemplate;
	}
}
