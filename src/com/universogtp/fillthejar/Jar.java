package com.universogtp.fillthejar;

import java.io.Serializable;

public class Jar  implements Serializable {
	private static final long serialVersionUID = 1L;
	private int iD;
	private String name;

	public Jar(int iD, String name) {
		this.iD = iD;
		this.name = name;
	}

	public int getID() {
		return iD;
	}
	
	public String getName() {
		return name;
	}
}
