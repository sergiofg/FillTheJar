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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class JarUpdateActivity extends Activity implements OnItemSelectedListener{
	private EditText jarNameEditText;
	private CheckBox checkbox;
	private Spinner spinner_f,spinner_s;
	private String selectionFrequency;
	private int fillspercycle,weekend,value,cycle;
	private TextView tx_streak,txweekend;
	private LinearLayout weekends,streaks;
	Jar jar;
	JarPersistence jarPersistence;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    
	    setContentView(R.layout.jar_new);
	    
	    Intent intent = getIntent();
	    jar = (Jar)intent.getSerializableExtra("jarObject");
	    setTitle(R.string.edition);
	    
	    
	    jarNameEditText = (EditText) findViewById(R.id.jarNameEditText);
	    spinner_f = (Spinner) findViewById(R.id.SFrequency);
	    checkbox = (CheckBox) findViewById(R.id.Check_weekend);
	    spinner_s= (Spinner) findViewById(R.id.SpStreak);
	    tx_streak =(TextView) findViewById(R.id.tx_streak);
	    txweekend =(TextView) findViewById(R.id.txweekend);
	    weekends =(LinearLayout) findViewById(R.id.l_weekends);
	    streaks =(LinearLayout) findViewById(R.id.l_streak);
	    
	    ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.SFrequency, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapter_s = ArrayAdapter.createFromResource(this,R.array.SpStreak, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        spinner_f.setAdapter(adapter);
        spinner_s.setAdapter(adapter_s);
        
        spinner_f.setOnItemSelectedListener(this);
        spinner_s.setOnItemSelectedListener(this);
        
        jarNameEditText.setText(jar.getName());
        
        switch (jar.getFrequency()) {
        case 0:
        	spinner_f.setSelection(3);
			break;
		case 1:
			spinner_f.setSelection(0);
			break;
		case 7:
			spinner_f.setSelection(1);
			break;
		case 15:
			spinner_f.setSelection(2);
			break;
		}
        spinner_s.setSelection(jar.getFillsPerCycle()-1);
        if (jar.getWeekends()==1)
        	{
        	checkbox.setChecked(true) ;
        	}else checkbox.setChecked(false) ;
       
	    
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem menuSaveJar = menu.add(0,1,1,R.string.change);
		menuSaveJar.setIcon(R.drawable.content_save);
		menuSaveJar.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		
		MenuItem menuRemoveJar = menu.add(0,0,0,R.string.cancel);
		menuRemoveJar.setIcon(R.drawable.content_remove);
		menuRemoveJar.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);	
		

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override 	
	public boolean onOptionsItemSelected(MenuItem item) {
		int frecuency=0;
		switch (item.getItemId()) {
		case android.R.id.home:
		case 0:
			  Intent intent = new Intent(this, JarActivity.class);
	    	  intent.putExtra("jarObject", jar);
	          startActivityForResult(intent, 0);		
			return true;
		case 1:
			try {
				JarPersistence jarPersistence = new JarPersistence(this);
				if (selectionFrequency.equals("diariamente")){
					frecuency=1;
				}
				if (selectionFrequency.equals("semanalmente")){
					frecuency=7;
				}
				if (selectionFrequency.equals("quincenalmente")){
					frecuency=15;
				}
				if (selectionFrequency.equals("ilimitado")){
					frecuency=0;
				}
				if (checkbox.isChecked()){
					weekend=1;
				}else weekend=0;
				
				if (jarNameEditText.getText().toString().isEmpty()){
					Toast.makeText(this, R.string.confirmation_add, Toast.LENGTH_SHORT).show();
				}else
				{				
			        if (fillspercycle >= jar.getValue()){
			            value = jar.getValue();
			            }else value=fillspercycle;
			        
			        cycle = jar.getFillsThisCycle();
					
					jar = new Jar(jar.getID(), jarNameEditText.getText().toString());
								
					jar.setFrequency(frecuency);
					jar.setWeekends(weekend);
					jar.setFillsPerCycle(fillspercycle);
					jar.setFillsThisCycle(cycle);
					jar.setValue(value);
					
					jarPersistence.updateJar(jar);
					jarPersistence.cleanup();
	
					Toast.makeText(this, R.string.modify, Toast.LENGTH_SHORT).show();
					
		            intent = new Intent(this, JarActivity.class);
		    	    intent.putExtra("jarObject", jar);
		            startActivityForResult(intent, 0);
				}
	            
			} catch (Exception e) {
				e.printStackTrace();
			}

			return true;			
		}
		return false;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View v, int pos,long arg3) {
		Spinner sp = (Spinner)parent;
		switch (sp.getId()) {
		case R.id.SFrequency :
			selectionFrequency = parent.getItemAtPosition(pos).toString();
			break;

		case R.id.SpStreak:
			fillspercycle =Integer.parseInt(parent.getItemAtPosition(pos).toString());
			break;
		}
		if (selectionFrequency.equals("diariamente")){
			txweekend.setVisibility(View.VISIBLE);
			weekends.setVisibility(View.VISIBLE);
		}else{
			txweekend.setVisibility(View.INVISIBLE);
			weekends.setVisibility(View.INVISIBLE);
		} 
		if (selectionFrequency.equals("ilimitado")){
			tx_streak.setVisibility(View.INVISIBLE);
			streaks.setVisibility(View.INVISIBLE);
		}else{
			tx_streak.setVisibility(View.VISIBLE);
			streaks.setVisibility(View.VISIBLE);
		} 
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		Spinner sp = (Spinner)parent;
		switch (sp.getId()) {
		case R.id.SFrequency :
			selectionFrequency="";
			break;

		case R.id.SpStreak:
			fillspercycle = 1;
			break;
		}
		
	}	
}