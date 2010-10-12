package com.springone.examples.mongo.myfiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.datastore.document.mongodb.MongoTemplate;
import org.springframework.stereotype.Component;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@Component
public class MongoFilesQueryApp {

	DB db;
	
	MongoTemplate documentTemplate;

	@Autowired
	MongoFileManager mongoManager;

	@Autowired
	public void init(DB db) {
		this.db = db;
		documentTemplate = new MongoTemplate(db);
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

    	DBCollection coll = db.getCollection("myFiles");
    	String query = "";
//    	DBCursor cur = coll.find((DBObject)JSON.parse("{ 'size' : { '$gt' : 100, '$lt' : 1000 } ,  'name' : { '$lt' : 'C' } }"));
//    	DBCursor cur = coll.find((DBObject)JSON.parse("{ '$or' : [{ 'size' : { '$gt' : 400, '$lt' : 500 }}, { 'name' : { '$lt' : 'B' } }] }"));
//    	DBCursor cur = coll.find((DBObject)JSON.parse("{ '$or' : [{ 'size' : { '$gt' : 400, '$lt' : 500 }}, { 'name' : { '$lt' : 'B' } }] }"));
//    	DBCursor cur = coll.find((DBObject)JSON.parse("{ 'type' : { '$in' : ['DIRECTORY'] } }, { '$or' : [{ 'size' : { '$gt' : 400, '$lt' : 500 }}, { 'name' : { '$lt' : 'B' } }] }"));
    	DBCursor cur = coll.find((DBObject)JSON.parse("{ 'type' : { '$in' : ['DIRECTORY'] } }, { '$or' : {{ 'size' : { '$gt' : 400, '$lt' : 500 }}, { 'name' : { '$lt' : 'B' } }} }"));
    	int x = 0;
    	while (cur.hasNext()) {
    		x++;
    		DBObject o = cur.next();
    		System.out.println(x + " --> " + o.get("type") + ": " + o.get("name") + " " + o.get("size"));
    	}
        
    }

}
