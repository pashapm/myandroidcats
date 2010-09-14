package ru.waterdist;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

public class Uris extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//make working dir and subdirs
		String[] dirs = {getDir(), getDir()+"/lns", getDir()+"/pics"};
		for (String s:dirs) {
			File dir = new File(s);
			if (! dir.isFile()) {
				dir.mkdirs();
			}
		}
		
		String files[] = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};
		for (String s : files) {
			try {
				String fn = s+".jpg";
				InputStream is = getAssets().open("letters/"+fn);
				FileOutputStream os = new FileOutputStream(getDir()+"/pics/"+fn);
				byte buff[] = new byte[1000];
				
				int c = 0;
				while ((c = is.read(buff)) != -1) {
					os.write(buff, 0, c);
				}
				
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String getDir() {
		return getStorage()+"/ftest";
	}
	
	
	public static String getStorage() {
		return Environment.getExternalStorageDirectory().getPath();
	}
}


