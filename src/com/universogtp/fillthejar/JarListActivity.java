package com.universogtp.fillthejar;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class JarListActivity extends ListActivity {
	private JarList jarList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    jarList = new JarList();
	    
	    Jar jar1 = new Jar(1, "Jarra 1");
	    Jar jar2 = new Jar(2, "Jarra 2");	    
	    jarList.addJar(jar1);
	    jarList.addJar(jar2);
	    
	    setListAdapter(new JarListAdapter(this,jarList));
	}
	
	/** {@inheritDoc} */
	@Override  
	protected void onListItemClick(ListView l, View v, int pos, long id) {  
	    super.onListItemClick(l, v, pos, id);  
	    
	    Intent intent = new Intent(this, JarActivity.class);
	    intent.putExtra("jarObject", jarList.getJar(pos));
        startActivityForResult(intent, 0);
	}  	

}
