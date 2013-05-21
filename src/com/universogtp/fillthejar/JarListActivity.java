package com.universogtp.fillthejar;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class JarListActivity extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    Jar jar1 = new Jar();
	    Jar jar2 = new Jar();
	    
	    JarList jarList = new JarList();
	    
	    jarList.addJar(jar1);
	    jarList.addJar(jar2);
	    
	    setListAdapter(new JarListAdapter(this,jarList));
	}
	
	/** {@inheritDoc} */
	@Override  
	protected void onListItemClick(ListView l, View v, int pos, long id) {  
	    super.onListItemClick(l, v, pos, id);
	    
	    Intent myIntent = new Intent(this, JarActivity.class);
        startActivityForResult(myIntent, pos);
	}  	

}
