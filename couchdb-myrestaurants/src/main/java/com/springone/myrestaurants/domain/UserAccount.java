package com.springone.myrestaurants.domain;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

//@Entity
///@Table(name = "user_account")
public class UserAccount {

    private String userName;

    private String firstName;

    private String lastName;
      
	private String revision;
	

	private String type;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date birthDate;

    //@ManyToMany(cascade = CascadeType.ALL)
    //private Set<Restaurant> favorites = new java.util.HashSet<Restaurant>();
    private List<String> favorites;
    
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	@Version
    @Column(name = "version")
    private Integer version;

	@JsonIgnore
	public Long getId() {
        return this.id;
    }

	@JsonIgnore
	public void setId(Long id) {
        this.id = id;
    }
	
	@JsonProperty("_rev")
	public String getRevision() {
		return revision;
	}
	
	@JsonProperty("_rev")
	public void setRevision(String s) {
		// no empty strings thanks 
		if (s != null && s.isEmpty()) {
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
