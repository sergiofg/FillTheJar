package com.universogtp.fillthejar;

import java.text.DateFormat;
import java.util.Date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class JarListAdapter extends ArrayAdapter<Jar> {
	private final Context context;
	private final JarList jarList;		

	public JarListAdapter(Context context, JarList jarList) {
		super(context, R.layout.jar_list_item,jarList.toArray());
		
		this.context = context;
		this.jarList = jarList;
	}
	
	public View getView(int position, View converView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.jar_list_item, parent, false);
		
		ImageView image = (ImageView) rowView.findViewById(R.id.element_image);
		image.setImageResource(R.drawable.ic_launcher); 
		
		TextView txtName = (TextView) rowView.findViewById(R.id.element_name);
		txtName.setText(jarList.getJar(position).getName());
		
		TextView txtCounter = (TextView) rowView.findViewById(R.id.element_counter);
		txtCounter.setText(String.valueOf(jarList.getJar(position).getValue()));

		String lastFillString = "";
		long lastFillMillis = Long.valueOf(jarList.getJar(position).getLastFill())*1000;
		if (lastFillMillis>0) {
			Date lastFillDate = new Date(lastFillMillis);
			lastFillString = DateFormat.getDateInstance().format(lastFillDate);
		}
		TextView txtLastFill = (TextView) rowView.findViewById(R.id.element_last_fill);		
		txtLastFill.setText("Rellenado: "+lastFillString);
		
		TextView txtStreak = (TextView) rowView.findViewById(R.id.element_streak);
		txtStreak.setText("Racha: "+String.valueOf(jarList.getJar(position).getStreak()));	
		
		return rowView;
	}
	

}
