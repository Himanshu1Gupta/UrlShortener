package com.proptiger.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "url_shortener", schema = "urlShortener")
public class UrlShortener implements Serializable {

	private static final long serialVersionUID = -2216259352219879958L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "long_url")
	private String longUrl;

	@Column(name = "hash_url")
	private int hashUrl;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "domain")
	private String domain;

	@PrePersist
	public void beforeCreation() {
		createdAt = new Date();

	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHashUrl() {
		return hashUrl;
	}

	public void setHashUrl(int hashUrl) {
		this.hashUrl = hashUrl;
	}

	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}
