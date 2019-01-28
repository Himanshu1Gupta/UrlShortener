package com.proptiger.config.dto;

import java.io.Serializable;

public class Response2Dto implements Serializable{
    
	    private String report;
	    
	    public Response2Dto(String report) {
	        this.report = report;
	    }

		public String getReport() {
			return report;
		}

		public void setReport(String report) {
			this.report = report;
		}



}
