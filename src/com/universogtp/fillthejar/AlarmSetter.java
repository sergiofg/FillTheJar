package com.universogtp.fillthejar;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmSetter extends BroadcastReceiver {

	  public static void setAlarm(Context context) {
		    AlarmManager alarmManager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		    Calendar calendar=Calendar.getInstance();
		    
		    calendar.set(Calendar.HOUR_OF_DAY, 12);
		    calendar.set(Calendar.MINUTE, 0);
		    calendar.set(Calendar.SECOND, 0);
		    calendar.set(Calendar.MILLISECOND, 0);
		    calendar.set(Calendar.AM_PM, Calendar.AM);
		    
		    if (calendar.getTimeInMillis()<System.currentTimeMillis()) {
		      calendar.add(Calendar.DAY_OF_YEAR, 1);
		    }
		    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
		                      AlarmManager.INTERVAL_DAY,
		                      getPendingIntent(context));
		  }
		  
		  public static void cancelAlarm(Context context) {
		    AlarmManager alarmManager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

		    alarmManager.cancel(getPendingIntent(context));
		  }
		  
		  private static PendingIntent getPendingIntent(Context context) {
		    Intent intent=new Intent(context, AlarmReceiver.class);
		    
		    return(PendingIntent.getBroadcast(context, 0, intent, 0));
		  }
		  
		  @Override
		  public void onReceive(Context context, Intent intent) {
		    setAlarm(context);
		  }
}