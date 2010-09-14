package ru.waterdist;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

public class ProgressTest extends Activity {

	static {
		System.loadLibrary("pipec");
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		final TextView disp = new TextView(this);
		disp.setText("0");
		setContentView(disp);
		
		final String pipe = "/data/data/ru.waterdist/pipe";
		
		new AsyncTask<Void, Integer, Integer>() {

			private Thread mReader = new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						FileInputStream fis = new FileInputStream(pipe);
						int p = 0;
						while ((p = fis.read()) > 0) {
							publishProgress(p);
						}
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			}) ;
			
			@Override
			protected void onProgressUpdate(Integer... values) {
				disp.setText(""+values[0]);
			};
			
			protected Integer doInBackground(Void... params) {
				if (mkfifo(pipe) < 0) {
					cancel(true);
				}
				mReader.start();
				return process(pipe);
			}
			
		}.execute();
	}
	
	
	native int process(String pipe);
	native int mkfifo(String pipe);
}
