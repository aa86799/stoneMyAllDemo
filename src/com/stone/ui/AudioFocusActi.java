package com.stone.ui;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.stone.R;
import com.stone.observer.SDCardListener;

public class AudioFocusActi extends Activity implements OnClickListener {
	private final String TAG = this.getClass().getSimpleName();
	
	private Button mPlayShort, mPlayLong;
	private AudioManager am;
	private SDCardListener sdCardListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.audio_focus);
		
		mPlayShort = (Button) findViewById(R.id.btn_short);
		mPlayLong = (Button) findViewById(R.id.btn_long);
		mPlayShort.setOnClickListener(this);
		mPlayLong.setOnClickListener(this);
		
		am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		checkAudioFocus();
		
		//测试fileobserver
		sdCardListener = new SDCardListener(Environment.getExternalStorageDirectory().getPath());
		sdCardListener.startWatching();
		
	}

	private void checkAudioFocus() {
		int result = am.requestAudioFocus(new OnAudioFocusChangeListener() {
			
			@Override
			public void onAudioFocusChange(int focusChange) {
				
			}
		}, AudioManager.STREAM_MUSIC,  // Use the music stream.
			AudioManager.AUDIOFOCUS_GAIN);// Request permanent focus.
		if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
//		    am.registerMediaButtonEventReceiver(RemoteControlReceiver);
		    // Start playback.
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_short:
			
			break;
		case R.id.btn_long:
			
			break;

		default:
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		sdCardListener.stopWatching();
	}
}
