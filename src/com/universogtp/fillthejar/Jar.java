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
	private int currentCycleStart;
	private int streak;

	public Jar(long iD, String name) {
		this.iD = iD;
		this.name = name;
		this.value=0;
		this.frecuency = 1;
		this.weekends=0;
		this.fillsPerCycle=1;
		this.fillsThisCycle=0;
		this.lastFill=0;
		this.streak = 0;
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

	public int getCurrentCycleStart() {
		return currentCycleStart;
	}

	public void setCurrentCycleStart(int currentCycleStart) {
		this.currentCycleStart = currentCycleStart;
	}	
	
	public int getStreak() {
		return streak;
	}

	public void setStreak(int streak) {
		this.streak = streak;
	}

	public String fill() {
		String message = null;
		
		if (getFillsThisCycle() < getFillsPerCycle()) {
			setValue(getValue()+1);
			setLastFill((int)(System.currentTimeMillis()/1000));
			setFillsThisCycle(getFillsThisCycle()+1);
			setStreak(getStreak()+1);
		} else {
			message = "No se puede rellenar hoy";
		}

		return message;
	}
	
	public boolean refresh() {
		boolean updated = false;
		
		long currentCycleStartMillis = (long) (getCurrentCycleStart() * 1000);
		long todayMillis = System.currentTimeMillis();
		
		int daysSinceCurrentCycleStart =  (int) ((todayMillis - currentCycleStartMillis) / (1000 * 60 * 60 * 24));		
		
		if (daysSinceCurrentCycleStart >= getFrecuency()) {
			int ellapsesCycles = daysSinceCurrentCycleStart / getFrecuency();
			setValue(getValue()-ellapsesCycles);
			setFillsThisCycle(0);
			
			setCurrentCycleStart(getCurrentCycleStart()+
					(getFrecuency()*ellapsesCycles*(60*24*24)));
			updated = true;
		}
		return updated;
	}
}