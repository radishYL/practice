package com.market.test;

import java.io.Serializable;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String name;
	
	public String sex;

	@Override
	public String toString() {
		return "User [name=" + name + ", sex=" + sex + "]";
	}
	
}
