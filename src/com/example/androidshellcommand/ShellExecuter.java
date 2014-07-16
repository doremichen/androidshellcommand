package com.example.androidshellcommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellExecuter {

	public ShellExecuter() {
		
	}
	
	public String Executer(String command) {
		String response = null;
		
		StringBuffer output = new StringBuffer();
		Process p = null;
		
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
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
	
	
}