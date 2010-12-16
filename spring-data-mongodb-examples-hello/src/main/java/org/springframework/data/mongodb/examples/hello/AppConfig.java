package org.springframework.data.mongodb.examples.hello;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.document.mongodb.MongoFactoryBean;
import org.springframework.data.document.mongodb.MongoTemplate;

import com.mongodb.Mongo;

@Configuration
public class AppConfig {

    public @Bean PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
    	return new PersistenceExceptionTranslationPostProcessor();
    }

    public @Bean MongoFactoryBean mongo() {
    	MongoFactoryBean bean = new MongoFactoryBean();
    	return bean; 
    }
    
    public @Bean MongoTemplate mongoTemplate(Mongo mongo) {
    	MongoTemplate mt = new MongoTemplate(mongo, "test", "HelloMongo");
    	return mt;
    }

}
