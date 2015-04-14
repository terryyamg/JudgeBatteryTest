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
		/*電池訊息*/
		this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
	}
	
	private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
	    @SuppressWarnings("deprecation")
		@Override
	    public void onReceive(Context ctxt, Intent intent) {
	    	int  health= intent.getIntExtra(BatteryManager.EXTRA_HEALTH,0); //健康狀態
			int  icon_small= intent.getIntExtra(BatteryManager.EXTRA_ICON_SMALL,0); //電池i-con圖
			int  level= intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);	//目前電量
			int  plugged= intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,0);	//電流來源
			boolean  present= intent.getExtras().getBoolean(BatteryManager.EXTRA_PRESENT); //電池存在否
			int  scale= intent.getIntExtra(BatteryManager.EXTRA_SCALE,0);	//電量最大值
			int  status= intent.getIntExtra(BatteryManager.EXTRA_STATUS,0);	//充電狀態
			String  technology= intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY); //電池技術
			int  temperature= intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0); //目前溫度
			int  voltage= intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0);	//目前電壓
			
			/*判斷健康狀態*/
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
			
			/*電池i-con圖*/
			LevelListDrawable batteryLevel = (LevelListDrawable) getResources().getDrawable(icon_small);
			batteryLevel.setLevel(level);
	        ivBattery.setBackgroundDrawable(batteryLevel);
			
			/*判斷電流來源*/
			switch (plugged) {  
            case BatteryManager.BATTERY_PLUGGED_AC:  
            	acOrUSB = "使用AC";
                break;  
            case BatteryManager.BATTERY_PLUGGED_USB:  
            	acOrUSB = "使用USB";
                break;  
            default:  
                break;  
            }  

			/*判斷充電狀態來源*/
			 switch (status) {  
             case BatteryManager.BATTERY_STATUS_CHARGING:  
            	 batteryState = "充電中";
                 break;  
             case BatteryManager.BATTERY_STATUS_DISCHARGING:  
            	 batteryState = "取消充電";
                 break;  
             case BatteryManager.BATTERY_STATUS_FULL:  
            	 batteryState = "電量已滿";
                 break;  
             case BatteryManager.BATTERY_STATUS_NOT_CHARGING:  
            	 batteryState = "未充電";
                 break;  
             case BatteryManager.BATTERY_STATUS_UNKNOWN:  
            	 batteryState = "未知";
                 break;  
             default:  
                 break;  
             }  
			
			 tvBatteryInfo.setText("健康狀態: "+healthState+"\n"+
						"目前電量: "+level+"% \n"+
						"電流來源: "+acOrUSB+"\n"+
						"電池存在: "+present+"\n"+
						"電量最大值: "+scale+"%\n"+
						"充電狀態: "+batteryState+"\n"+
						"電池技術: "+technology+"\n"+
						"目前溫度: "+temperature+"度\n"+
						"目前電壓: "+voltage+"\n");
	    }
	  };
	  
	  @Override
		protected void onDestroy() {
			super.onDestroy();
			//結束後要移除，否則會出現 Activity xxx has leaked IntentReceiver
			unregisterReceiver(mBatInfoReceiver);
		}
}