package com.universogtp.fillthejar;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class JarNewActivity extends Activity implements OnItemSelectedListener{
	private EditText jarNameEditText;
	private CheckBox checkbox;
	private Spinner spinner_f,spinner_s;
	private String selectionFrecuency;
	private int streak,weekend;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(R.layout.jar_new);
	    
	    jarNameEditText = (EditText) findViewById(R.id.jarNameEditText);
	    spinner_f = (Spinner) findViewById(R.id.SFrecuency);
	    checkbox = (CheckBox) findViewById(R.id.Check_weekend);
	    spinner_s= (Spinner) findViewById(R.id.SpStreak);
	    
	    ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.SFrecuency, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapter_s = ArrayAdapter.createFromResource(this,R.array.SpStreak, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_f.setAdapter(adapter);
        spinner_s.setAdapter(adapter_s);
        
        spinner_f.setOnItemSelectedListener(this);
        spinner_s.setOnItemSelectedListener(this);
	    
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem menuRemoveJar = menu.add(0,0,0,"Cancelar");
		menuRemoveJar.setIcon(R.drawable.content_remove);
		menuRemoveJar.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);	
		
		MenuItem menuSaveJar = menu.add(0,1,1,"Grabar");
		menuSaveJar.setIcon(R.drawable.content_save);
		menuSaveJar.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override 	
	public boolean onOptionsItemSelected(MenuItem item) {
		int nfrecuency=0;
		switch (item.getItemId()) {
		case android.R.id.home:
		case 0:
			Intent intent = new Intent(this, JarListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);			
			return true;
		case 1:
			try {
				JarPersistence jarPersistence = new JarPersistence(this);
				if (selectionFrecuency.equals("diariamente")){
					nfrecuency=1;
				}
				if (selectionFrecuency.equals("semanalmente")){
					nfrecuency=7;
				}
				if (selectionFrecuency.equals("quincenalmente")){
					nfrecuency=15;
				}
				if (checkbox.isChecked()){
					weekend=1;
				}else weekend=0;
				Jar jar = new Jar(0, jarNameEditText.getText().toString(),nfrecuency,streak,weekend);
				jarPersistence.newJar(jar);
				jarPersistence.cleanup();
				Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			intent = new Intent(this, JarListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);			
			return true;			
		}
		return false;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View v, int pos,long arg3) {
		Spinner sp = (Spinner)parent;
		switch (sp.getId()) {
		case R.id.SFrecuency :
			selectionFrecuency = parent.getItemAtPosition(pos).toString();
			if (selectionFrecuency.equals("diariamente")){
				checkbox.setVisibility(View.VISIBLE);
			}else{
				checkbox.setVisibility(View.INVISIBLE);
			}
			break;

		case R.id.SpStreak:
			streak =Integer.parseInt(parent.getItemAtPosition(pos).toString());
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		Spinner sp = (Spinner)parent;
		switch (sp.getId()) {
		case R.id.SFrecuency :
			selectionFrecuency="";
			break;

		case R.id.SpStreak:
			streak = 1;
			break;
		}
		
	}	
}
