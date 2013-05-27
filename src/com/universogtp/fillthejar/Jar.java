package com.universogtp.fillthejar;

import java.io.Serializable;

public class Jar  implements Serializable {
	private static final long serialVersionUID = 1L;
	private long iD;
	private String name;
	private int created;
	private int value;
	private int frecuency;
	private int weekends;
	private int fillsPerCycle;
	private int fillsThisCycle;
	private int lastFill;
	private int streak;

	public Jar(long iD, String name,int frecuency,int streak,int weekends) {
		this.iD = iD;
		this.name = name;
		this.value=0;
		this.frecuency = frecuency;
		this.streak = streak;
		this.weekends = weekends;
	}
	
	public long getID() {
		return iD;
	}
	
	public void setID(long iD){
		this.iD=iD;
	}
	
	public String getName() {
		return name;
	}
	
	public int getCreated() {
		return created;
	}

	public void setCreated(int created) {
		this.created = created;
	}	
	
	public void setName(String name){
		this.name=name;
	}	
	
	public int getValue(){
		return value;
	}
	
	public void setValue(int value){
		this.value=value;
	}
	
	public int getFrecuency() {
		return frecuency;
	}

	public void setFrecuency(int frecuency) {
		this.frecuency = frecuency;
	}
	
	public int isWeekends() {
		return weekends;
	}
	
	public int getWeekends(){
		return weekends;
	}
	
	public void setWeekends(int weekends) {
		this.weekends = weekends;
	}
	
	public int getFillsPerCycle() {
		return fillsPerCycle;
	}

	public void setFillsPerCycle(int fillsPerCycle) {
		this.fillsPerCycle = fillsPerCycle;
	}
	
	public int getFillsThisCycle() {
		return fillsThisCycle;
	}

	public void setFillsThisCycle(int fillsThisCycle) {
		this.fillsThisCycle = fillsThisCycle;
	}
	
	public int getLastFill() {
		return lastFill;
	}

	public void setLastFill(int lastFill) {
		this.lastFill = lastFill;
	}

	public int getStreak() {
		return streak;
	}

	public void setStreak(int streak) {
		this.streak = streak;
	}

	public int fill() {
		// TODO calcular si debe rellenarse o no		
		value++;
		return value;
	}
	
	public boolean refresh() {
		// TODO calcular si el tarro debe vaciarse y actualiza en consecuencia (devolver true si el tarro cambia y debe ser guardado en persistencia)  
		return false;
	}
}