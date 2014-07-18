package com.example.androidshellcommand;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.view.WindowManager;

public class ShellCommandService extends Service {
	
	private static final String TAG = ShellCommandService.class.getSimpleName();
	
	private static final String ACTION_SHOWDIALOG = "action.show.alert.dialog";
	
	private IntentFilter mIntentFilter = null;
	
	private AlertDialog alertDialog = null;
	
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			
			if(action.equals(ACTION_SHOWDIALOG)){
				
				alertDialog = getAlertDialog(context, "Info", "Monkey finish");
				alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                alertDialog.show();
								
			}
			
		}
		
	};
	
	
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		
		mIntentFilter = new IntentFilter();
		mIntentFilter.addAction(ACTION_SHOWDIALOG);
		this.registerReceiver(mReceiver, mIntentFilter);
				
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		this.unregisterReceiver(mReceiver);
	}
	
	
	//non public function
	private AlertDialog getAlertDialog(Context context, String title, String message) {
        
        Builder builder = new AlertDialog.Builder(context);
        
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    
                    public void onClick(DialogInterface dialog, int which) {
                    
                        //do something
                        alertDialog.dismiss();
                    }
                    
        });
        
        return builder.create();
        
     }   

}
