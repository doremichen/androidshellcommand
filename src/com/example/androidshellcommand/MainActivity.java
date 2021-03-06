package com.example.androidshellcommand;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

import java.io.FileOutputStream;
import java.io.File;

import java.util.List;
import java.util.ArrayList;

public class MainActivity extends Activity {

	private static final String TAG = MainActivity.class.getSimpleName();
	
	private EditText input = null;
	private EditText input_count = null;
	private TextView output = null;
	private String command = null;
		
	
	private Thread runMonkeyThread = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		
		input = (EditText)this.findViewById(R.id.txt);
		output = (TextView)this.findViewById(R.id.out);
		
		input_count = (EditText)this.findViewById(R.id.et_monkey_count);
				
				
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		boolean noMonkey = ShellExecuter.NoMonkey();
		
		if(noMonkey == false) {
			finish();
		}
		
		Intent intent = new Intent();
		intent.setClass(this, ShellCommandService.class);
		this.startService(intent);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onExecutor(View v) {
		
		InputMethodManager imm = (InputMethodManager)getSystemService(
			      Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
		
		ShellExecuter exe = new ShellExecuter();
		
		
		command = input.getText().toString();
				
		Log.i(TAG, command);
		
		input.setText("");
		
		
		if(!command.equals("")) {
			
			String ouput_str = exe.Executer(command);
			output.setText(ouput_str);
		}
		else {
			Toast.makeText(MainActivity.this, "Please input command.", Toast.LENGTH_SHORT).show();
		}
		
			
	}
	
	public void onRunMonkey(View v) {
	    
	    InputMethodManager imm = (InputMethodManager)getSystemService(
			      Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
		
		runMonkeyThread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				ExecuteMonkey();
							
				runOnUiThread(new Runnable() { 
					public void run() 
					{ 
						Intent intent = new Intent();
						intent.setAction("action.show.alert.dialog");
						sendBroadcast(intent);
					} 
				}); 
				
			}
		
		});
		
		 runMonkeyThread.start();		    
		 ShellExecuter.enableMonkey("1");
	}
	
	
	//non public function
	private void ExecuteMonkey() {
	    
	    List<String> command = new ArrayList<String>();
	    
	    String count = input_count.getText().toString();
	    
	    command.add("monkey");
	    
	    command.add("-v");
//	    command.add("-s 1");
//	    command.add("-p com.android.calendar");
//	    command.add("--throttle 1000");
//	    command.add("--pct-touch 100");
//	    command.add("--pct-motion 100");
	    command.add("--ignore-crashes");
//	    command.add("--ignore-timeouts");
	    command.add("--ignore-security-exceptions");
	    command.add(count);
	   		
		String result = ShellExecuter.ExecuterBuilder(command);
		
//		String result = ShellExecuter.Executer("monkey -v -p com.android.calendar --ignore-crashes --ignore-security-exceptions 10000");
		
		WriteTestResult(result);

	    
	}
	
	
	 private void WriteTestResult(String str) {
	    
	    String filename = "mylog.txt";
        FileOutputStream outputStream;
        
        File file = new File(this.getFilesDir(), filename);
        
        Log.i(TAG, this.getFilesDir().getPath());
        
        try {
          outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
          outputStream.write(str.getBytes());
          outputStream.close();
        } catch (Exception e) {
          e.printStackTrace();
        }   	    
	    
     }
     	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
