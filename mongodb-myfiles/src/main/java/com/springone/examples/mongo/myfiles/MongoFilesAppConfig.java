package com.springone.examples.mongo.myfiles;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.document.mongodb.MongoFactoryBean;

@Configuration
public class MongoFilesAppConfig {
	
    public @Bean CommonAnnotationBeanPostProcessor commonAnnotationBeanPostProcessor() {
    	return new CommonAnnotationBeanPostProcessor();
    }
    
    public @Bean PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
    	return new PersistenceExceptionTranslationPostProcessor();
    }

    public @Bean MongoFactoryBean mongo() {
    	MongoFactoryBean bean = new MongoFactoryBean();
    	return bean; 
    }
    
    public @Bean FileManager fileManager() {
    	return new FileManager(".");
    }
    
}
