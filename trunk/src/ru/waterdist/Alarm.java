package ru.waterdist;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class Alarm extends Activity {
        
	@Override     
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.main);   
        AlarmManager mana = (AlarmManager)getSystemService(Context.ALARM_SERVICE); 
        Intent i = new Intent(this, TestActivity.class);
        PendingIntent pend = PendingIntent.getActivity(this, 1, i, Intent.FLAG_ACTIVITY_NEW_TASK);
        mana.set(AlarmManager.RTC_WAKEUP  , System.currentTimeMillis()+10000, pend);
        
	}
	
	
}


//Calendar calendar = Calendar.getInstance();
//calendar.setTimeInMillis(System.currentTimeMillis());
//calendar.add(Calendar.SECOND, 60);

