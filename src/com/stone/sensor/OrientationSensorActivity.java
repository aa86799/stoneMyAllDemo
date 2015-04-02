package com.stone.sensor;

import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.stone.R;
/**
 *	传感器
 */
public class OrientationSensorActivity extends Activity implements SensorEventListener
{
	private boolean			mRegisteredSensor;
	//定义SensorManager
	private SensorManager		mSensorManager;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		
		mRegisteredSensor = false;
		//取得SensorManager实例
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		
		((Button)findViewById(R.id.btn_add)).setText("添加快捷方式");
		((Button)findViewById(R.id.btn_delete)).setText("删除快捷方式");
	}
	/*处理快捷方式*/
	public void click(View view) {
		switch (view.getId()) {
			case R.id.btn_add:
				 //为程序创建桌面快捷方式
				 Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
				 //快捷方式的名称
				 shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));
				 shortcut.putExtra("duplicate", false); //不允许重复创建
				 //指定当前的Activity为快捷方式启动的对象: 如
				 //com.everest.video.VideoPlayer
				 //注意: ComponentName的第二个参数必须加上点号(.)，否则快捷方式无法启动相应程序
				 ComponentName comp = new ComponentName(this.getPackageName(), "."+this.getLocalClassName());
				shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setComponent(comp));
				//快捷方式的图标
				ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(this, R.drawable.ic_launcher);
				shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
				sendBroadcast(shortcut);

				break;
			case R.id.btn_delete:
				Intent delIntent = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
				//快捷方式的名称
				delIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));
				//指定当前的Activity为快捷方式启动的对象: 如 //com.everest.video.VideoPlayer
				//注意: ComponentName的第二个参数必须是完整的类名（包名+类名），否则无法删除快捷方式
				String appClass = this.getPackageName() + "." +this.getLocalClassName();
				ComponentName compn = new ComponentName(this.getPackageName(), appClass);
				delIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setComponent(compn));
				sendBroadcast(delIntent);
				break;
		}
	}
	@Override
	protected void onResume()
	{
		super.onResume();

		//接受SensorManager的一个列表(Listener)
		//这里我们指定类型为TYPE_ORIENTATION(方向感应器)
		List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
		for (Sensor sensor : sensors) {
			System.out.println("传感器类型：" + sensor.getName());
		}
		if (sensors.size() > 0)
		{
			Sensor sensor = sensors.get(0);
			//注册SensorManager
			//this->接收sensor的实例
			//接收传感器类型的列表
			//接受的频率
			mRegisteredSensor = mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
		}
	}
	@Override
	protected void onPause()
	{
		if (mRegisteredSensor)
		{
			//如果调用了registerListener
			//这里我们需要unregisterListener来卸载\取消注册
			mSensorManager.unregisterListener(this);
			mRegisteredSensor = false;
		}
		super.onPause();
	}
	//当精准度发生改变时
	//sensor->传感器
	//accuracy->精准度
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{
		
	}
	// 当传感器在被改变时触发
	@Override
	public void onSensorChanged(SensorEvent event)
	{
		// 接受方向感应器的类型
		if (event.sensor.getType() == Sensor.TYPE_ORIENTATION)
		{
			//这里我们可以得到数据，然后根据需要来处理
			//模拟器上面无法测试效果
			float x = event.values[SensorManager.DATA_X];
			float y = event.values[SensorManager.DATA_Y];
			float z = event.values[SensorManager.DATA_Z];
			System.out.println("x="+x+";y="+y+";z="+z);
		}
	}
}
