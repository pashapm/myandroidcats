package ru.waterdist;

import android.app.Activity;
import android.os.Bundle;
import android.os.PowerManager;

public class TestActivity extends Activity {

	@Override    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
//        PowerManager pm = (PowerManager)getSystemService(POWER_SERVICE);
//        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.FULL_WAKE_LOCK, "ho");
//        wl.acquire(); 
        
        Networking.readURL();
	}
}
