package com.mambu.xbml.shared;

import java.io.Serializable;
import java.util.Date;

public class RequestSetttings implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String domain;
	public String username;
	public String password;
	public Date fromDate = null;
	public Date toDate = null;
		                  
}
