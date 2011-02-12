package com.springsource.myrestaurants.models;

import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.util.Assert;

public class Restaurant {

	private String id;
	
	private String revision;
	
    private String name;

    private String city;
    
    private String state;
    
    private String type;
    

    private String zipCode;

	private Integer version;


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
        return this.name;
    }

	public void setName(String name) {
        this.name = name;
    }

	public String getCity() {
        return this.city;
    }

	public void setCity(String city) {
        this.city = city;
    }

	public String getState() {
        return this.state;
    }

	public void setState(String state) {
        this.state = state;
    }

	@JsonProperty("zip_code")
	public String getZipCode() {
        return this.zipCode;
    }

	@JsonProperty("zip_code")
	public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

	@JsonProperty("_id")
	public String getId() {
		return id;
	}
	
	@JsonProperty("_id")
	public void getId(String s) {
		Assert.hasText(s, "id must have a value");
		if (id != null) {
			throw new IllegalStateException("cannot set id, id already set");
		}
		id = s;
	}
	
	@JsonProperty("_rev")
	public String getRevision() {
		return revision;
	}
	
	@JsonProperty("_rev")
	public void setRevision(String s) {
		// no empty strings thanks 
		if (s != null && s.length() == 0) {
			return;
		}
		this.revision = s;
	}
	
	
	// This is just due to a poor data import from sql
	
	public Integer getVersion() {
        return this.version;
    }

	public void setVersion(Integer version) {
        this.version = version;
    }


	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Revision: ").append(getRevision()).append(", ");
        sb.append("Name: ").append(getName()).append(", ");
        sb.append("City: ").append(getCity()).append(", ");
        sb.append("State: ").append(getState()).append(", ");
        sb.append("ZipCode: ").append(getZipCode());
        return sb.toString();
    }
}
