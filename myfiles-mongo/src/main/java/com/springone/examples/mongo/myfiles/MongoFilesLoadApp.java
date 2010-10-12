package com.springone.examples.mongo.myfiles;

import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.datastore.document.DocumentSource;
import org.springframework.datastore.document.mongodb.MongoDocumentSource;
import org.springframework.datastore.document.mongodb.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

@Component
public class MongoFilesLoadApp {

	DB db;
	
	MongoTemplate documentTemplate;
	
	@Autowired
	FileManager fileManager;
	
	@Autowired
	MongoFileManager mongoManager;
	
	@Autowired
	public void setDb(DB db) {
		this.db = db;
		documentTemplate = new MongoTemplate(db);
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

		Set<String> colls = this.db.getCollectionNames();
		System.out.println("COLLECTIONS: " + colls);

		DBCollection coll = db.getCollection("myFiles");
        System.out.println("LOADED: " + coll.count());
	}

    private void init() {
        System.out.println( "INITIALIZING!" );
        DBCollection coll = db.getCollection("myFiles");
        if (coll.count() > 0) {
			this.documentTemplate.dropCollection("myFiles");
        }
	}

}
