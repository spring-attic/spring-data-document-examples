package com.springsource.myrestaurants.models;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class UserAccount {

    private String userName;

    private String firstName;

    private String lastName;
      
	private String revision;
	

	private String type;

    private Date birthDate;

    private List<String> favorites;
    

    private Integer version;


	
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

    @JsonProperty("Version")
	public Integer getVersion() {
        return this.version;
    }

    @JsonProperty("Version")
	public void setVersion(Integer version) {
        this.version = version;
    }

	@JsonProperty("_id")
	public String getUserName() {
        return this.userName;
    }

	@JsonProperty("_id")
	public void setUserName(String userName) {
        this.userName = userName;
    }

    @JsonProperty("FirstName")
	public String getFirstName() {
        return this.firstName;
    }

    @JsonProperty("FirstName")
	public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("LastName")
	public String getLastName() {
        return this.lastName;
    }

    @JsonProperty("LastName")
	public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonProperty("BirthDate")
	public Date getBirthDate() {
        return this.birthDate;
    }

    @JsonProperty("BirthDate")
	public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @JsonProperty("Favorites")
	public List<String> getFavorites() {
		return favorites;
	}

    @JsonProperty("Favorites")
	public void setFavorites(List<String> favorites) {
		this.favorites = favorites;
	}

	public String getType() {
		return type;
	}

	
	public void setType(String type) {
		this.type = type;
	}
	

	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getUserName()).append(", ");
        sb.append("Revision: ").append(getRevision()).append(", ");
        sb.append("UserName: ").append(getUserName()).append(", ");
        sb.append("FirstName: ").append(getFirstName()).append(", ");
        sb.append("LastName: ").append(getLastName()).append(", ");
        sb.append("BirthDate: ").append(getBirthDate()).append(", ");
        sb.append("Favorites: ").append(getFavorites() == null ? "null" : getFavorites().size());
        return sb.toString();
    }
}
