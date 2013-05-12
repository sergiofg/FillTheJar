package com.universogtp.fillthejar;

import com.universogtp.fillthejar.JarRules;

import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

public class JarView extends LinearLayout implements OnClickListener 
{
	TextView counter;
	Button button;
	JarRules rules;
	
	public JarView(Context context, AttributeSet attrs) 
	{
		super(context);
		
		rules = new JarRules(context);
		
		setOrientation(LinearLayout.VERTICAL);
	    setGravity(Gravity.CENTER_HORIZONTAL);
	    
		LayoutInflater inflater = (LayoutInflater) context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.jar_view, this, true);

	    counter = (TextView) getChildAt(0);
	    counter.setText(String.valueOf(rules.getCounter()));
	    
	    button = (Button) getChildAt(1);
	    button.setOnClickListener(this);	    
	}

	@Override
	public void onClick(View v) 
	{
		counter.setText(String.valueOf(rules.fillJar()));
	}

}
