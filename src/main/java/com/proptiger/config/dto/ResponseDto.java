package com.proptiger.config.dto;

import java.io.Serializable;

public class ResponseDto implements Serializable{
    
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Url;
    
    public ResponseDto(String Url) {
        this.Url = Url;
    }

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}


}