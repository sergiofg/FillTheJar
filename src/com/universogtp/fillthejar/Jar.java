package com.universogtp.fillthejar;

import java.io.Serializable;

public class Jar  implements Serializable {
	private static final long serialVersionUID = 1L;
	private long iD;
	private String name;
	private int value;

	public Jar(long iD, String name) {
		this.iD = iD;
		this.name = name;
		this.value=0;
	}
	
	public long getID() {
		return iD;
	}
	
	public String getName() {
		return name;
	}
	
	public int getValue(){
		return value;
	}
	
	public void setID(long iD){
		this.iD=iD;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public void setValue(int value){
		this.value=value;
	}
	
	public int fill() {
		value++;
		return value;
	}	
}