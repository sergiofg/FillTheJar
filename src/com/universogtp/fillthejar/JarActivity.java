package com.universogtp.fillthejar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;


public class JarActivity extends Activity implements OnClickListener {
	TextView counter;
	Button button;
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
	    
	    counter = (TextView) findViewById(R.id.counter_text);
	    counter.setText(String.valueOf(jar.getValue()));
	    
	    button = (Button) findViewById(R.id.add_button);
	    button.setOnClickListener(this);		    
	}

	@Override 	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, JarListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);			
			return true;
		}
		return false;
	}
	
	@Override
	public void onClick(View v) {
		jar.fillJar();
		if (jarPersistence != null) {
			jarPersistence.updateJar(jar);
		}
		counter.setText(String.valueOf(jar.getValue()));
	}	
}
