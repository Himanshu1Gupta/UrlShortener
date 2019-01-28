package com.proptiger.config.dto;

import java.io.Serializable;

public class UrlDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String longUrl;
	private String domain;

	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	public String toString() {
		return "UrlDto [longUrl=" + longUrl + ", domain=" + domain + "]";
	}

}
