package com.universogtp.fillthejar;

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

		String status = "";
		switch (jarList.getJar(position).getFrecuency()) {
		case 1:
			status = "diario ";
			break;
		case 7:
			status = "semanal ";
			break;
		case 15:
			status = "quincenal ";
			break;
		default:
			break;
		};
		status += jarList.getJar(position).getFillsThisCycle();
		status += "/";
		status += jarList.getJar(position).getFillsPerCycle();
		TextView txtStatus = (TextView) rowView.findViewById(R.id.element_status);
		txtStatus.setText(status);
		
		TextView txtStreak = (TextView) rowView.findViewById(R.id.element_streak);
		txtStreak.setText(context.getString(R.string.streak)+": "+String.valueOf(jarList.getJar(position).getStreak()));	
		
		return rowView;
	}
	

}
