package com.example.androidshellcommand;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder;
import java.util.List;

public class ShellExecuter {

    private static final String TAG = ShellExecuter.class.getSimpleName();

	public ShellExecuter() {
		
	}
	
	public final static String Executer(String command) {
		String response = null;
		
		StringBuffer output = new StringBuffer();
		Process p = null;
		
		try {
		    Log.i(TAG, "start "+command);
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			Log.i(TAG, "print result of  "+command);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line = "";
			while((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response = output.toString();
		
		return response;
	}
	
	
	public final static String ExecuterBuilder(List<String> command) {
		String response = null;
		
		StringBuffer output = new StringBuffer();
		Process p = null;
		
		try {
		    Log.i(TAG, "start "+command);
		    p = new ProcessBuilder()
                   .command(command)
                   .redirectErrorStream(true)
                   .start();

			Log.i(TAG, "print result of  "+command);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line = "";
			while((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response = output.toString();
		
		return response;
	}
	
}
