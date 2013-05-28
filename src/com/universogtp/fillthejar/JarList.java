package com.universogtp.fillthejar;

import java.util.ArrayList;
import java.util.Iterator;

public class JarList implements Iterable<Jar> {
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
	
	public boolean refresh() {
		boolean changed = false;
		for (Jar jar : jarArray) {
			changed = changed || jar.refresh();
		}
		
		return changed;
	}
	
	public Jar[] toArray() {
		Jar[] jars = jarArray.toArray(new Jar[jarArray.size()]);
		return jars;
	}

	@Override
	public Iterator<Jar> iterator() {
        Iterator<Jar> jarIterator = jarArray.iterator();
        return jarIterator;
	}
}