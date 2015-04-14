package tw.android;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.LevelListDrawable;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Main extends Activity {
	private Button btBattery;
	private TextView tvBatteryInfo;
	private ImageView ivBattery;
	
	private String healthState,acOrUSB,batteryState;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		btBattery = (Button) findViewById(R.id.btBattery);
		tvBatteryInfo = (TextView) findViewById(R.id.tvBatteryInfo);
		ivBattery = (ImageView) findViewById(R.id.ivBattery);
		
		btBattery.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				runInfo();
				
			}
		});
		
		
	}
	
	public void runInfo(){
		/*�q���T��*/
		this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
	}
	
	private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
	    @SuppressWarnings("deprecation")
		@Override
	    public void onReceive(Context ctxt, Intent intent) {
	    	int  health= intent.getIntExtra(BatteryManager.EXTRA_HEALTH,0); //���d���A
			int  icon_small= intent.getIntExtra(BatteryManager.EXTRA_ICON_SMALL,0); //�q��i-con��
			int  level= intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);	//�ثe�q�q
			int  plugged= intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,0);	//�q�y�ӷ�
			boolean  present= intent.getExtras().getBoolean(BatteryManager.EXTRA_PRESENT); //�q���s�b�_
			int  scale= intent.getIntExtra(BatteryManager.EXTRA_SCALE,0);	//�q�q�̤j��
			int  status= intent.getIntExtra(BatteryManager.EXTRA_STATUS,0);	//�R�q���A
			String  technology= intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY); //�q���޳N
			int  temperature= intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0); //�ثe�ū�
			int  voltage= intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0);	//�ثe�q��
			
			/*�P�_���d���A*/
			switch (health) {  
            case BatteryManager.BATTERY_HEALTH_COLD:  
            	healthState = "COLD";
                break;  
            case BatteryManager.BATTERY_HEALTH_DEAD:  
            	healthState = "DEAD";
                break;  
            case BatteryManager.BATTERY_HEALTH_GOOD:  
            	healthState = "GOOD";
                break;  
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:  
            	healthState = "OVERHEAT";
                break;  
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:  
            	healthState = "OVER_VOLTAGE";
                break;  
            case BatteryManager.BATTERY_HEALTH_UNKNOWN:  
            	healthState = "UNKNOWN";
                break;  
            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:  
            	healthState = "UNSPECIFIED_FAILURE"; 
                break;  
            default:  
                break;  
            }  
			
			/*�q��i-con��*/
			LevelListDrawable batteryLevel = (LevelListDrawable) getResources().getDrawable(icon_small);
			batteryLevel.setLevel(level);
	        ivBattery.setBackgroundDrawable(batteryLevel);
			
			/*�P�_�q�y�ӷ�*/
			switch (plugged) {  
            case BatteryManager.BATTERY_PLUGGED_AC:  
            	acOrUSB = "�ϥ�AC";
                break;  
            case BatteryManager.BATTERY_PLUGGED_USB:  
            	acOrUSB = "�ϥ�USB";
                break;  
            default:  
                break;  
            }  

			/*�P�_�R�q���A�ӷ�*/
			 switch (status) {  
             case BatteryManager.BATTERY_STATUS_CHARGING:  
            	 batteryState = "�R�q��";
                 break;  
             case BatteryManager.BATTERY_STATUS_DISCHARGING:  
            	 batteryState = "�����R�q";
                 break;  
             case BatteryManager.BATTERY_STATUS_FULL:  
            	 batteryState = "�q�q�w��";
                 break;  
             case BatteryManager.BATTERY_STATUS_NOT_CHARGING:  
            	 batteryState = "���R�q";
                 break;  
             case BatteryManager.BATTERY_STATUS_UNKNOWN:  
            	 batteryState = "����";
                 break;  
             default:  
                 break;  
             }  
			
			 tvBatteryInfo.setText("���d���A: "+healthState+"\n"+
						"�ثe�q�q: "+level+"% \n"+
						"�q�y�ӷ�: "+acOrUSB+"\n"+
						"�q���s�b: "+present+"\n"+
						"�q�q�̤j��: "+scale+"%\n"+
						"�R�q���A: "+batteryState+"\n"+
						"�q���޳N: "+technology+"\n"+
						"�ثe�ū�: "+temperature+"��\n"+
						"�ثe�q��: "+voltage+"\n");
	    }
	  };
	  
	  @Override
		protected void onDestroy() {
			super.onDestroy();
			//������n�����A�_�h�|�X�{ Activity xxx has leaked IntentReceiver
			unregisterReceiver(mBatInfoReceiver);
		}
}