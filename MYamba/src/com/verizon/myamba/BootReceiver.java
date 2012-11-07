package com.verizon.myamba;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		// Setup alarm
		Intent refreshIntent = new Intent("com.verizon.yamba.action.REFRESH");
		PendingIntent operation = PendingIntent.getService(context,
				-1, refreshIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		am.setInexactRepeating(AlarmManager.RTC, System.currentTimeMillis(),
				AlarmManager.INTERVAL_FIFTEEN_MINUTES/15, operation);

		Log.d("BootReceiver", "onReceived");
	}
}
