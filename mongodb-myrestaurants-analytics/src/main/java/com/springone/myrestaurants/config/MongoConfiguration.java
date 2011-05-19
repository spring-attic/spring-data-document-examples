package com.springone.myrestaurants.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.document.mongodb.MongoTemplate;

import com.mongodb.DB;
import com.mongodb.Mongo;

@Configuration
public class MongoConfiguration {

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {

		Mongo m = new Mongo();
		MongoTemplate mongoTemplate = new MongoTemplate(m, "mvc", "mvc");
		mongoTemplate.afterPropertiesSet();
		if (!mongoTemplate.collectionExists("mvc")) {
			mongoTemplate.createCollection("mvc");
		}
		if (!mongoTemplate.collectionExists("counters")) {
			mongoTemplate.createCollection("counters");
		}
		return mongoTemplate;
	}
}
