package com.springone.examples.mongo.myfiles;

import static org.springframework.data.document.mongodb.query.Criteria.where;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.document.mongodb.MongoTemplate;
import org.springframework.data.document.mongodb.query.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.Mongo;

@Repository
public class MongoFileManager {

	MongoTemplate mongoDbTemplate;
	
	@Autowired
	public void setMongo(Mongo mongo) {
		mongoDbTemplate = new MongoTemplate(mongo, "test");
	}

    public void addFiles(List<FileEntry> files) throws DataAccessException {
		this.mongoDbTemplate.insertList("myFiles", files);
    }
    
    public List<FileEntry> queryForAllFiles() throws DataAccessException {
		List<FileEntry> results = this.mongoDbTemplate.getCollection("myFiles", FileEntry.class);
		return results;
    }
    
    public List<FileEntry> queryForLargeFiles(long size) throws DataAccessException {
    	Query q = new Query(where("size").gt(size));
		List<FileEntry> results = 
				this.mongoDbTemplate.find("myFiles", q, FileEntry.class);
		return results;
    }
    
}
