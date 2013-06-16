package com.universogtp.fillthejar;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

public class JarActivity extends Activity implements OnClickListener {
	TextView counter;
	Button button;
	ImageView image;
	Jar jar;
	JarPersistence jarPersistence;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jar_activity);
		
	    Intent intent = getIntent();
	    jar = (Jar)intent.getSerializableExtra("jarObject");
	    setTitle(jar.getName());
	    
	    try {
			jarPersistence = new JarPersistence(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	    ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    
	    image = (ImageView) findViewById(R.id.jar_image);
	    counter = (TextView) findViewById(R.id.counter_text);
	    button = (Button) findViewById(R.id.add_button);
	    
	    refreshDisplay();
	    button.setOnClickListener(this);		    
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		MenuItem menuDelJar = menu.add(0,0,0,R.string.erased);
		menuDelJar.setIcon(R.drawable.content_discard);
		menuDelJar.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		
		MenuItem menuEmptyJar = menu.add(0,1,1,R.string.empty);
		menuEmptyJar.setIcon(R.drawable.rating_bad);
		menuEmptyJar.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);	
		
		MenuItem menuModifyJar = menu.add(0,2,2,R.string.change);
		menuModifyJar.setIcon(R.drawable.content_edit);
		menuModifyJar.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		return true;
	}
	
	
	private void refreshDisplay() {
	    int value = jar.getValue();
	    if (value > 10) value = 10;
	    int jarImageID = getResources().getIdentifier("jar"+value, "drawable", "com.universogtp.fillthejar");
	    image.setImageResource(jarImageID);
	    
	    counter.setText(String.valueOf(jar.getValue()));
	}

	@Override 	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, JarListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);			
			return true;

		case 0:
			AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setMessage(R.string.confirmation_del);
            adb.setCancelable(false);
            adb.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { 
               
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                	borrar();
                    finish();
                }
            });
            adb.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
               
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    dialog.cancel();
                }
            });
            adb.show();	
			return true;
		case 1:
			int cycle;
			cycle = jar.getFillsPerCycle();
			jar = new Jar(jar.getID(), jar.getName());
			jar.setFillsPerCycle(cycle);
			if (jarPersistence != null) {
				jarPersistence.updateJar(jar);
			}
			refreshDisplay();
			return true;
		case 2:
			intent = new Intent(this, JarUpdateActivity.class);
		    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	    intent.putExtra("jarObject", jar);
            startActivityForResult(intent, 0);
			return true;

		}
		return false;
	}
	
	@Override
	public void onClick(View v) {
		jar.fill(this);
		if (jarPersistence != null) {
			jarPersistence.updateJar(jar);
		}
		refreshDisplay();
	}	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	Intent intent = new Intent(this, JarListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
	    }

	    return super.onKeyDown(keyCode, event);
	}
    public void borrar(){
		jarPersistence.deleteJar(jar);
		Toast.makeText(this,R.string.delete, Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(this, JarListActivity.class);
	    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    startActivity(intent);
    }	
}
