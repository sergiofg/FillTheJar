package com.universogtp.fillthejar;

import java.util.ArrayList;

public class JarList {
	private ArrayList<Jar> jarArray;
	
	public JarList() {
		jarArray = new ArrayList<Jar>();
	}

	public void addJar(Jar jar) {
		jarArray.add(jar);
	}
	
	public int count() {
		return jarArray.size();
	}
	
	public Jar getJar (int position) {
		return jarArray.get(position);
	}
	
	public Jar[] toArray() {
		Jar[] jars = jarArray.toArray(new Jar[jarArray.size()]);
		return jars;
	}
}
