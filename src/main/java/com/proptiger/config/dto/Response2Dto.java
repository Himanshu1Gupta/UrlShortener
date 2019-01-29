package com.proptiger.config.dto;

import java.io.Serializable;

public class Response2Dto implements Serializable{
    
	    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private String reports;
	    
	    public Response2Dto(String reports) {
	        this.reports = reports;
	    }

		public String getReports() {
			return reports;
		}

		public void setReports(String reports) {
			this.reports = reports;
		}

	


}
