package com.universogtp.fillthejar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
		
		TextView txtLista = (TextView) rowView.findViewById(R.id.element_text);
		txtLista.setText(jarList.getJar(position).getName());
		
		return rowView;
	}
	

}
