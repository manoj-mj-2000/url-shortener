//$Id$
package com.urlShortner.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity  //class to be mapped to a database table using JPA
@Data	 //	Lombok – auto-generate getters/setters
@NoArgsConstructor 		//no need for public UrlMapping() {} default constructor
@AllArgsConstructor		//no need for public UrlMapping(long id, String originalUrl, String shortCode) {} 
						//auto generates the every constructor.

public class UrlMapping {

	@Id  	//Marks id as primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) 	//Tells JPA to auto-generate the id
    private Long id;
	@Column(name="original_url")
    private String originalUrl;
	@Column(name="short_code", unique=true)
	private String shortCode;
    
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOriginalUrl() {
		return originalUrl;
	}
	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}
	public String getShortCode() {
		return shortCode;
	}
	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}
}
