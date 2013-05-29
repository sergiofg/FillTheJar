package com.universogtp.fillthejar;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class JarListActivity extends ListActivity {
	private JarList jarList;	

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    setTitle(R.string.jar_list);
	    
	    try {
			JarPersistence jarPersistance = new JarPersistence(this);
			jarList = jarPersistance.getJarList();
			jarPersistance.cleanup();
		} catch (Exception e) {
			e.printStackTrace();
			jarList = new JarList();
		}
	    setListAdapter(new JarListAdapter(this,jarList));
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		AlarmSetter.setAlarm(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuItem menuAddJar = menu.add(0,0,0,"Nuevo");
		menuAddJar.setIcon(R.drawable.content_new);
		menuAddJar.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}	
	
	/** {@inheritDoc} */
	@Override  
	protected void onListItemClick(ListView l, View v, int pos, long id) {  
	    super.onListItemClick(l, v, pos, id);  
	    
	    Intent intent = new Intent(this, JarActivity.class);
	    intent.putExtra("jarObject", jarList.getJar(pos));
        startActivityForResult(intent, 0);
	}
	
	@Override 	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
		    Intent intent = new Intent(this, JarNewActivity.class);
	        startActivityForResult(intent, 0);		
			return true;
		}
		return false;
	}
}
