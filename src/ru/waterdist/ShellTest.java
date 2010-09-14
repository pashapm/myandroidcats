package ru.waterdist;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.SequenceInputStream;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class ShellTest extends Activity {

	private void cat(String s1, String s2, String dest) throws IOException {
		FileInputStream fi0;
		fi0 = new FileInputStream(s1);
		FileInputStream fi1;
		fi1 = new FileInputStream(s2);
		SequenceInputStream seq = new SequenceInputStream(fi0, fi1);
		FileOutputStream fos = new FileOutputStream(dest);
		byte[] buff = new byte[1000];
		while (seq.read(buff, 0, 1000) != -1) {
			fos.write(buff);
		}
		fos.close();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		try {
			FileInputStream fi0;
			fi0 = new FileInputStream("/sdcard/firemaned/video.raw");
			FileInputStream fi1;
			fi1 = new FileInputStream("/sdcard/firemaned/video1.raw");
			SequenceInputStream seq = new SequenceInputStream(fi0, fi1);
			FileOutputStream fos = new FileOutputStream("/sdcard/firemaned/conc.raw");
			byte[] buff = new byte[1000];
			int c=0;
			while ((c=seq.read(buff, 0, 1000)) != -1) {
				fos.write(buff);
			}
			fos.close();
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
//		String[] arr = {"cat", "/sdcard/voice.mp3 >" ,"/sdcard/zzz"};
//		try {
//			File f = new File("/sdcard/zzz");
//			f.createNewFile();
//			
//			Process p = Runtime.getRuntime().exec("cat /sdcard/voice.mp3 > /sdcard/zzz");
//			
//			BufferedReader r = new BufferedReader( new InputStreamReader(p.getErrorStream()) );
//			for (int i=0; i<10; i++) {
//				String s = r.readLine();
//				Log.d("!!!", s+"");
//			}
//			
//			;
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
