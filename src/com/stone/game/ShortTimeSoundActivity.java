package com.stone.game;

import java.util.HashMap;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.stone.R;
/**
 * 即时音效   播放7秒之内的音效选用 SoundPool
 */
public class ShortTimeSoundActivity extends Activity {
	SoundPool sp;							//得到一个声音池引用
	HashMap<String,Integer> spMap;			//得到一个map的引用
	Button b1;								//声音播放控制按钮
	Button b1Pause;								//声音暂停控制按钮
    Button b2;								//声音播放控制按钮
    Button b2Pause;								//声音暂停控制按钮
    int retVal1;
    int retVal2;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shorttimesound);
        initSoundPool();						//初始化声音池
        b1 = (Button)findViewById(R.id.Button01);//声音播放控制按钮实例化
        b2 = (Button)findViewById(R.id.Button02);//声音播放控制按钮实例化
        b1Pause = (Button)findViewById(R.id.Button1Pause);
        b2Pause = (Button)findViewById(R.id.Button2Pause);
        
        b1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				retVal1 = playSound("a",0);		//播放第一首音效，一遍
				Toast.makeText(ShortTimeSoundActivity.this, "播放音效1", Toast.LENGTH_SHORT).show();
		}});
        b1Pause.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sp.pause(retVal1);
				Toast.makeText(ShortTimeSoundActivity.this, "暂停音效1", Toast.LENGTH_SHORT).show();
		}});
        b2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				retVal2 = playSound("b",-1);		//播放第二首音效，循环放
			Toast.makeText(ShortTimeSoundActivity.this, "播放音效2", Toast.LENGTH_SHORT).show();
		}});
        b2Pause.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sp.pause(retVal2);
				Toast.makeText(ShortTimeSoundActivity.this, "暂停音效2", Toast.LENGTH_SHORT).show();
				
		}});
    }
    public void initSoundPool(){			//初始化声音池
    	sp = new SoundPool(
    			5, 				//maxStreams参数，该参数为设置同时能够播放多少音效
    			AudioManager.STREAM_MUSIC,	//streamType参数，该参数设置音频类型，在游戏中通常设置为：STREAM_MUSIC
    			0				//srcQuality参数，该参数设置音频文件的质量，目前还没有效果，设置为0为默认值。
    	);
    	spMap = new HashMap<String,Integer>();
    	spMap.put("a", sp.load(this, R.raw.attack02, 1));
    	spMap.put("b", sp.load(this, R.raw.attack14, 1));//sp.load(context,resid,优先级1);
    }
    /**
     * 播放声音
     * @param soundkey 播放音效的id
     * @param number 播放音效的次数
     */
    public int playSound(String soundkey,int number){	
    	AudioManager am = (AudioManager)this.getSystemService(this.AUDIO_SERVICE);//实例化AudioManager对象
    	float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);	//返回当前AudioManager对象的最大音量值
    	float audioCurrentVolumn = am.getStreamVolume(AudioManager.STREAM_MUSIC);//返回当前AudioManager对象的音量值
    	float volumnRatio=audioCurrentVolumn/audioMaxVolumn;
    	return sp.play(
    			spMap.get(soundkey), 					//播放的音乐id
    			volumnRatio, 						//左声道音量  0.0~1.0
    			volumnRatio, 						//右声道音量  0.0~1.0
    			1, 									//优先级，0为最低
    			number, 							//循环次数，0无不循环，-1无永远循环 ,(测试出来>0最多三次)
    			1									//回放速度 ，该值在0.5-2.0之间，1为正常速度
    	);
    }
}
