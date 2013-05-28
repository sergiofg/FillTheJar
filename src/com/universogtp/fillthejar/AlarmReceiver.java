package com.universogtp.fillthejar;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
	    try {
			JarPersistence jarPersistance = new JarPersistence(context);
			JarList jarList = jarPersistance.getJarList();
			jarPersistance.cleanup();
			if (jarList.refresh()) {
				for (Jar jar : jarList) {
				    jarPersistance.updateJar(jar);
				}
			}
	        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	        @SuppressWarnings("deprecation")
			Notification notification =  new Notification.Builder(context)
	         .setContentTitle("Jar actualizados")
	         .setContentText("Se han actualizado los jar")
	         .setSmallIcon(R.drawable.ic_launcher)
	         .getNotification();

	        notification.flags |= Notification.FLAG_AUTO_CANCEL;

    		notificationManager.notify(0, notification);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}