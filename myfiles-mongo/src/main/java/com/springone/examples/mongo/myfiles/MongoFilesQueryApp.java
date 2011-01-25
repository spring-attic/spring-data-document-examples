package com.springone.examples.mongo.myfiles;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.document.mongodb.MongoTemplate;
import org.springframework.stereotype.Component;

import com.mongodb.DB;
import com.mongodb.Mongo;

@Component
public class MongoFilesQueryApp {

	DB db;
	
	MongoTemplate documentTemplate;

	@Autowired
	MongoFileManager mongoManager;

	@Autowired
	public void init(Mongo mongo) throws Exception {
		this.db = mongo.getDB("test");
		documentTemplate = new MongoTemplate(mongo, "test", "myFiles");
		((InitializingBean)documentTemplate).afterPropertiesSet();
	}

    public static void main( String[] args ) {
		System.out.println("Bootstrapping MongoFilesQueryApp");

		ConfigurableApplicationContext context = null;
        context = new ClassPathXmlApplicationContext("META-INF/spring/bootstrap.xml");

        MongoFilesQueryApp app = context.getBean(MongoFilesQueryApp.class);
        app.run();

        context.close();
        System.out.println( "DONE!" );
    }
    
    public void run() {
		System.out.println("ALL [" + mongoManager.queryForAllFiles().size() + "]");
    	System.out.println("QRY [" + mongoManager.queryForLargeFiles(10000000).size() + "]");
    }

}
