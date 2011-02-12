package com.springone.examples.mongo.myfiles;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.document.mongodb.MongoTemplate;
import org.springframework.stereotype.Component;

import com.mongodb.DBCollection;
import com.mongodb.Mongo;

@Component
public class MongoFilesLoadApp {

	Mongo mongo;
	
	MongoTemplate documentTemplate;
	
	@Autowired
	FileManager fileManager;
	
	@Autowired
	MongoFileManager mongoManager;
	
	@Autowired
	public void setMongo(Mongo mongo) {
		this.mongo = mongo;
		documentTemplate = new MongoTemplate(mongo, "test");
	}

    public static void main( String[] args ) {
		System.out.println("Bootstrapping MongoFilesLoadApp");

		ConfigurableApplicationContext context = null;
        context = new ClassPathXmlApplicationContext("META-INF/spring/bootstrap.xml");

        MongoFilesLoadApp app = context.getBean(MongoFilesLoadApp.class);

		app.init();
        
        app.run();
        
        app.close();

		context.close();
        System.out.println( "DONE!" );
    }
    
    public void run() {
		List<FileEntry> files = fileManager.readFiles();
		mongoManager.addFiles(files);
    }

    public void close() {
        System.out.println( "CLOSING!" );

		Set<String> colls = this.mongo.getDB("test").getCollectionNames();
		System.out.println("COLLECTIONS: " + colls);

		DBCollection coll = this.mongo.getDB("test").getCollection("myFiles");
        System.out.println("LOADED: " + coll.count());
	}

    private void init() {
        System.out.println( "INITIALIZING!" );
        DBCollection coll = this.mongo.getDB("test").getCollection("myFiles");
        if (coll.count() > 0) {
			this.documentTemplate.dropCollection("myFiles");
        }
	}

}
