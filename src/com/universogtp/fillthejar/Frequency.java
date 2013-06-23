package com.universogtp.fillthejar;

public class Frequency {
		
	public int ConvertFrequency(String f){
		int i=0;
		if (f.equals("diariamente")){
			i=1;
		}
		if (f.equals("semanalmente")){
			i=7;
		}
		if (f.equals("quincenalmente")){
			i=15;
		}
		if (f.equals("ilimitado")){
			i=0;
		}
		return i;
	}
}
