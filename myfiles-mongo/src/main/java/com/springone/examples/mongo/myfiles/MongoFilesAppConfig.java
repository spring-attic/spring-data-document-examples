package com.springone.examples.mongo.myfiles;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.datastore.document.mongodb.CannotGetMongoDbConnectionException;
import org.springframework.datastore.document.mongodb.MongoDbFactoryBean;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

@Configuration
public class MongoFilesAppConfig {
	
    public @Bean CommonAnnotationBeanPostProcessor commonAnnotationBeanPostProcessor() {
    	return new CommonAnnotationBeanPostProcessor();
    }
    
    public @Bean PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
    	return new PersistenceExceptionTranslationPostProcessor();
    }

    public @Bean MongoDbFactoryBean db() {
    	MongoDbFactoryBean bean = new MongoDbFactoryBean();
    	bean.setDatabaseName("test");
    	Mongo mongo;
		try {
			mongo = new Mongo("localhost");
		} catch (UnknownHostException e) {
			throw new CannotGetMongoDbConnectionException("Unnable to connect to MongoDB ", e);
		} catch (MongoException e) {
			throw new CannotGetMongoDbConnectionException("Unnable to connect to MongoDB ", e);
		}
    	bean.setMongo(mongo);
    	return bean; 
    }
    
    public @Bean FileManager fileManager() {
    	return new FileManager("/Users/trisberg/Books");
    }
    
}
