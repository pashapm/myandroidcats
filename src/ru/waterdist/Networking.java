package ru.waterdist;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Networking {

	public static void readURL() {
		try {
			URL bash = new URL("http://bash.org.ru");
			InputStreamReader reader = new InputStreamReader(bash.openStream());
			BufferedReader buff = new BufferedReader(reader);
			
			String inputLine;

			while ((inputLine = buff.readLine()) != null)
			    System.out.println(inputLine);

			buff.close();

			 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void readURL2() {
		try {
			URL bash = new URL("http://bash.org.ru");
			URLConnection conn = bash.openConnection();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
