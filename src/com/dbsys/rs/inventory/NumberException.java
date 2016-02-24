package com.dbsys.rs.inventory;

import com.dbsys.rs.ApplicationException;

public class NumberException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	public NumberException() {
		super();
	}

	public NumberException(String message) {
		super(message);
	}
}
