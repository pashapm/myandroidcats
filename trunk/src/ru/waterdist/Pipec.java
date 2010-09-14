package ru.waterdist;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore.LoadStoreParameter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class Pipec extends Activity {

	
	static {
		System.loadLibrary("pipec");
	}
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d(">>>>>>>>>>>>>>>>>>>>> ", "cal pipec");
		

		
new Thread(new Runnable() {
			
			@Override
			public void run() {

				try {
//					FileOutputStream fos = new FileOutputStream("/data/data/ru.waterdist/test_fifo");
//					byte b  =43;
//					fos.write(b);
					FileInputStream fis = new FileInputStream("/data/data/ru.waterdist/test_fifo1");
					Log.d("!!", "1-read");
					Log.d("!!!!!", "read: "+fis.read());
					Log.d("!!", "1-read");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
		}).start();

new Thread(new Runnable() {
	
	@Override
	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pipec();
		
	}
}).start();

	}
	
	native void pipec();
	native void pipec2();
}
