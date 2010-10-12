package com.springone.examples.mongo.myfiles;

import java.util.Date;

public class FileEntry {

	public enum Type {
		DIRECTORY, FILE
	}

	private String id;
	private Type type;
	private String directory;
	private String name;
	private Long size;
	private Date lastModified;

    public FileEntry() {
    }
    
	public FileEntry(String id, Type type, String directory, String name,
			Long size, Date lastModified) {
		super();
		this.id = id;
		this.type = type;
		this.directory = directory;
		this.name = name;
		this.size = size;
		this.lastModified = lastModified;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public String getDirectory() {
		return directory;
	}
	
	public void setDirectory(String directory) {
		this.directory = directory;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Long getSize() {
		return size;
	}
	
	public void setSize(Long size) {
		this.size = size;
	}
	
	public Date getLastModified() {
		return lastModified;
	}
	
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	@Override
	public String toString() {
		return "FileEntry [id=" + id + ", type=" + type + ", directory="
				+ directory + ", name=" + name + ", size=" + size
				+ ", lastModified=" + lastModified + "]";
	}
	
}
