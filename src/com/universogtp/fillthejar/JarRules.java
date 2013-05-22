package com.universogtp.fillthejar;

import android.content.Context;

public class JarRules {
	Context context;
	JarPersistence persistence;

	public JarRules(JarPersistence persistance, Context context) {
        this.context = context;
		this.persistence = persistance;
    }	
	
	public JarRules(Context context) {
		this(new JarPersistence(context), context);
	}

	public int getCounter() {
		//return persistence.getValue();
		return 0;
	}

	public int fillJar() {
		//int counter=persistence.getValue();
		int counter=0;
		counter++;
		//persistence.setValue(counter);
		return counter;
	}	
}