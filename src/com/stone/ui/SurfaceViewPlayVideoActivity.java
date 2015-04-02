package com.stone.ui;


import java.util.Formatter;
import java.util.Locale;

import com.stone.R;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class SurfaceViewPlayVideoActivity extends Activity implements
		OnCompletionListener, OnErrorListener, OnInfoListener,
		OnPreparedListener, OnSeekCompleteListener, OnVideoSizeChangedListener,
		SurfaceHolder.Callback, OnClickListener {
	AudioManager audioManager;
	Display currentDisplay;
	SurfaceView surfaceView;
	SurfaceHolder surfaceHolder;
	MediaPlayer mediaPlayer;
	ImageButton ib_rew,// 快退
				 ib_pause,// 暂停
				 ib_ffwd,// 快进
				 ib_sound, // 声音
				 ib_stop; //停止
	SeekBar mediacontroller_progress, sound_controller;
	TextView tv_time_current, tv_endTime;
	int videoWidth = 0;
	int videoHeight = 0;
	int positionWhenPaused = -1; //暂停记录位置
	int cur_sound; //当前 音量
	boolean showing, dragging, stop;
	StringBuilder formatBuilder;
	Formatter formatter;
	public final String TAG = "SurfaceViewPlayVideoActivity";
	private final int SHOW_PROGRESS = 1;
	private Thread checkIsPlaying; //检查是否正在播放
	
	private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int pos;
            switch (msg.what) {
                case SHOW_PROGRESS: //显示播放进度
                    pos = setVideoProgress();
                    if (!dragging && showing && mediaPlayer!=null&&mediaPlayer.isPlaying()) {
                        msg = obtainMessage(SHOW_PROGRESS);
                        sendMessageDelayed(msg, 1000 - (pos % 1000));
                    }
                    break;
            }
        }
    };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate()");
		setContentView(R.layout.surface_view_play_video);
		
		initView();
		setListeners();
	}
	
	private void initView() {
		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		mediaPlayer = new MediaPlayer();
		surfaceView = (SurfaceView) findViewById(R.id.surfaceview);
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(this);//surfaceview的变化
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);// 缓冲类型
		surfaceHolder.setKeepScreenOn(true);
		
		currentDisplay = getWindowManager().getDefaultDisplay();
		
		ib_rew = (ImageButton) findViewById(R.id.rew);
		ib_pause = (ImageButton) findViewById(R.id.pause);
		ib_ffwd = (ImageButton) findViewById(R.id.ffwd);
		ib_sound = (ImageButton) findViewById(R.id.sound);
		ib_stop = (ImageButton) findViewById(R.id.stop);
		
		mediacontroller_progress = (SeekBar) findViewById(R.id.mediacontroller_progress);
		sound_controller = (SeekBar) findViewById(R.id.sound_controller);

		tv_time_current = (TextView) findViewById(R.id.time_current);
		tv_endTime = (TextView) findViewById(R.id.time);
		
		formatBuilder = new StringBuilder();
		formatter = new Formatter(formatBuilder, Locale.getDefault());

		int max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC); // 最大音量
		sound_controller.setMax(max);
		sound_controller.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
		soundControll();
		setVideoProgress(); 
		
		mediacontroller_progress.setMax(1000);
		
		checkIsPlaying = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (!showing) {
					if (mediaPlayer.isPlaying()) {
						showing = true;
						handler.sendEmptyMessage(SHOW_PROGRESS);
					}
				}
			}
		}); 
		checkIsPlaying.start();
	}
	private void setListeners() {
		mediaPlayer.setOnCompletionListener(this);
		mediaPlayer.setOnErrorListener(this);
		mediaPlayer.setOnInfoListener(this);
		mediaPlayer.setOnPreparedListener(this);
		mediaPlayer.setOnSeekCompleteListener(this);//mediaPlayer.seekTo(msec)
		mediaPlayer.setOnVideoSizeChangedListener(this);
		
		ib_rew.setOnClickListener(this);
		ib_pause.setOnClickListener(this);
		ib_ffwd.setOnClickListener(this);
		ib_sound.setOnClickListener(this);
		ib_stop.setOnClickListener(this);
		
		mediacontroller_progress.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) { // 结束拖动
						dragging = false;
						mediaPlayer.start();
						handler.sendEmptyMessage(SHOW_PROGRESS);
						updatePausePlay();
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) { // 开始拖动
						dragging = true;
						handler.removeMessages(SHOW_PROGRESS);
					}

					@Override
					public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
						Log.i(TAG, "progress:"+progress);
						if (!fromUser) {
							return;
						}
						long duration = mediaPlayer.getDuration();    //dura/1000 <==> 每单位 progress 代表多少  秒的 
			            long newposition = duration / 1000L * progress;
			            mediaPlayer.seekTo((int)newposition);
			           
			            if (tv_time_current != null) {
			                tv_time_current.setText(stringForTime(progress));
			            }
					}
				});

		sound_controller.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {

					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {

					}

					@Override
					public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
						audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0); // 设置音量
						Log.i(TAG, "progress=" + progress);
						soundControll();
					}
				});
	}
	@Override // onCompletionListener
	public void onCompletion(MediaPlayer mp) {
		Log.i(TAG, "onCompletion called");
	}

	@Override // onErrorListener
	public boolean onError(MediaPlayer mp, int what, int extra) {
		Log.i(TAG, "onError called");
		switch (what) {
		case MediaPlayer.MEDIA_ERROR_UNKNOWN:
			Log.v(TAG, "MEDIA_ERROR_UNKNOWN " + extra);
			break;
		case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
			Log.v(TAG, "MEDIA_ERROR_SERVER_DIED " + extra);
			break;
		}
		//mp 处理错误状态，可以使用 mp.reset(); 重置为空闲 状态
		
		finish(); 
		return false;
	}

	@Override // onInfoListener当出现关于媒体的特定信息或需要发出警告的信息时被调用
	public boolean onInfo(MediaPlayer mp, int what, int extra) {
		Log.i(TAG, "onInfo called");
		switch (what) {
		case MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING: 	//音频视频不正确交错
			Log.v(TAG, "MEDIA_INFO_BAD_INTERLEAVING " + extra);
			break;
		case MediaPlayer.MEDIA_INFO_METADATA_UPDATE: 	//当新的元数据可用时
			Log.v(TAG, "MEDIA_INFO_METADATA_UPDATE " + extra);
			break;
		case MediaPlayer.MEDIA_INFO_NOT_SEEKABLE:		//媒体不能正确定位时
			Log.v(TAG, "MEDIA_INFO_NOT_SEEKABLE " + extra);
			break;
		case MediaPlayer.MEDIA_INFO_UNKNOWN:			//信息未知
			Log.v(TAG, "MEDIA_INFO_UNKNOWN " + extra);
			break;
		case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING://无法播放视频时，可能音频是可以播的
			Log.v(TAG, "MEDIA_INFO_VIDEO_TRACK_LAGGING " + extra);
			break;
		}
		
		return false;
	}

	@Override // onPreparedListener
	public void onPrepared(MediaPlayer mp) {
		Log.i(TAG, "onPrepared called");
		//设置surface的大小 以 匹配视频 或 屏幕，取决于哪个比较小
	/*	videoWidth = mp.getVideoWidth();
		videoHeight = mp.getVideoHeight();
		//如果视频的宽度或高度大于 屏幕的,  如果小于还是用视频的
		if (videoWidth > currentDisplay.getWidth() || videoHeight > currentDisplay.getHeight()) {
			float widthRatio = (float) videoWidth / currentDisplay.getWidth(); //视频与屏幕的宽比
			float heightRatio = (float) videoHeight / currentDisplay.getHeight();//      高比
			//哪个比率大 就除以大的比率
			if (widthRatio > heightRatio) {
				videoWidth = (int) Math.ceil((float) videoWidth / widthRatio);
				videoHeight = (int) Math.ceil((float) videoHeight / widthRatio);
			} else {
				videoWidth = (int) Math.ceil((float) videoWidth / heightRatio);
				videoHeight = (int) Math.ceil((float) videoHeight / heightRatio);
			}
			
		}
		surfaceView.setLayoutParams(new LinearLayout.LayoutParams(videoWidth, videoHeight));*/
		
		/*if(!checkIsPlaying.isAlive()) {
			handler.post(checkIsPlaying);
		}*/
		mp.start();
	}

	@Override // onSeekCompleteListener
	public void onSeekComplete(MediaPlayer mp) {
		Log.i(TAG, "onSeekComplete called");
	}

	@Override // onVideoSizeChangedListener
	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
		Log.i(TAG, "onVideoSizeChanged called");
	}

	@Override // SurfaceHolder.Callback
	public void surfaceCreated(SurfaceHolder holder) {
		Log.i(TAG, "surfaceCreated called");
		try {
			mediaPlayer.reset(); 
			mediaPlayer.setDisplay(holder); // 设置视频的输出位置  视频显示
			mediaPlayer.setDataSource(Environment.getExternalStorageDirectory().getPath()+"/Movies/hoot.mp4");
			Log.i(TAG, Environment.getExternalStorageDirectory().getPath()+"/Movies/hoot.mp4");
			mediaPlayer.prepareAsync();//不阻塞主线程，异步的,准备完成后，将调用 onPreparedListener.onPrepared()
		} catch (Exception e) {
			Log.v(TAG, e.getMessage());
		}
	}

	@Override // SurfaceHolder.Callback
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		Log.i(TAG, "surfaceChanged called");
	}

	@Override // SurfaceHolder.Callback
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.i(TAG, "surfaceDestroyed called");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		Log.i(TAG, "onPause()");
		if (mediaPlayer.isPlaying()) {
			positionWhenPaused = mediaPlayer.getCurrentPosition();
			mediaPlayer.pause();
		}
	}
	@Override
	protected void onStop() {
		super.onStop();
		Log.i(TAG, "onStop()");
		if (mediaPlayer.isPlaying()) {
			positionWhenPaused = mediaPlayer.getCurrentPosition();
			mediaPlayer.stop();
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
		Log.i(TAG, "onResume()");
		if (positionWhenPaused >= 0) {
			mediaPlayer.seekTo(positionWhenPaused);
            positionWhenPaused = -1;
            mediaPlayer.start();
            showing = false; // resume后 调用 OnPreparedListener
            Log.i(TAG, "onResume():继续播放");
		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "onStop()");
		if (mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
			mediaPlayer.release();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ffwd: //快进
			int pos = mediaPlayer.getCurrentPosition();
            pos += 15000; // milliseconds
            mediaPlayer.seekTo(pos);
            mediaPlayer.start();
			handler.sendEmptyMessage(SHOW_PROGRESS);
			updatePausePlay();
			break;
		
		case R.id.rew:	//快退
			int cur_pos = mediaPlayer.getCurrentPosition();
			cur_pos -= 15000; // milliseconds
            mediaPlayer.seekTo(cur_pos);
            mediaPlayer.start();
			handler.sendEmptyMessage(SHOW_PROGRESS);
			updatePausePlay();
			break;
		case R.id.pause:
			if (stop) {
				try {
					mediaPlayer.seekTo(0);
		/*			mediaPlayer.reset();
					mediaPlayer.setDisplay(surfaceHolder); // 设置视频的输出位置  视频显示
					mediaPlayer.setDataSource(Environment.getExternalStorageDirectory().getPath()+"/Movies/hoot.mp4");
					Log.i(TAG, Environment.getExternalStorageDirectory().getPath()+"/Movies/hoot.mp4");
					mediaPlayer.prepareAsync();*///不阻塞主线程，异步的,准备完成后，将调用 onPreparedListener.onPrepared()
					mediaPlayer.start();
				} catch (Exception e) {
					Log.v(TAG, e.getMessage());
				}
//				surfaceView.setBackgroundColor(Color.TRANSPARENT);
				stop = false;
				updatePausePlay();
				return;
			}
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.pause();
				showing = false;
				stop = false;
				Log.i(TAG, "视频暂停");
			} else {
				mediaPlayer.start();
				stop = false;
				if (!checkIsPlaying.isAlive()) {
					handler.post(checkIsPlaying);
					Log.i(TAG, "暂停后播放");
				}
			}
			updatePausePlay();
			break;
		case R.id.stop:
			mediaPlayer.stop();
//			mediaPlayer.prepareAsync();//stop后，调用prepare,会从当前停的地方开始继续播
//			surfaceView.setBackgroundColor(Color.BLACK);
			showing = false;
			stop = true;
			mediacontroller_progress.setProgress(0);
			tv_time_current.setText("00:00");
			tv_endTime.setText("00:00");
			updatePausePlay();
			break;
		case R.id.sound: 
			int current = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
			if (current > 0) {
				cur_sound = current;
				audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0); //静音
			} else {
				audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, cur_sound, 0); //还原音量
			}
			soundControll();
			break;
		}
	}
	
	/**
	 * TextView显示播放进度
	 * @return 播放的当前位置
	 */
	private int setVideoProgress() {
		if (mediaPlayer == null || dragging) {
			return 0;
		}
		int position = 0;
		if (mediaPlayer.isPlaying()) {
			position = mediaPlayer.getCurrentPosition();
			System.out.println("po:"+position);
			int duration = mediaPlayer.getDuration();
			System.out.println("du:"+duration);
			if (mediacontroller_progress != null) {
				if (duration > 0) {
					// use long to avoid overflow
					long pos = 1000L * position / duration;
					mediacontroller_progress.setProgress((int) pos);
				}
//				int percent = videoView.getBufferPercentage();
//				mediacontroller_progress.setSecondaryProgress(percent * 10);
				
			}

			if (tv_endTime != null)
				tv_endTime.setText(stringForTime(duration));
			if (tv_time_current != null)
				tv_time_current.setText(stringForTime(position));
		}
		

		return position;
	}
	
	private void updatePausePlay() {
    	if (ib_pause == null)
            return;

        if (mediaPlayer.isPlaying()) {
        	ib_pause.setImageResource(android.R.drawable.ic_media_pause);
        } else {
        	ib_pause.setImageResource(android.R.drawable.ic_media_play);
        }
	}
	/**
	 * 声音图标 控制
	 */
	private void soundControll() {
		int current = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		if (current == 0) {
			ib_sound.setImageResource(android.R.drawable.ic_lock_silent_mode);
		} else {
			ib_sound.setImageResource(android.R.drawable.ic_lock_silent_mode_off);
		}
		sound_controller.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
	}
	
	private String stringForTime(int timeMs) {
		int totalSeconds = timeMs / 1000;

		int seconds = totalSeconds % 60;
		int minutes = (totalSeconds / 60) % 60;
		int hours = totalSeconds / 3600;

		formatBuilder.setLength(0);
		if (hours > 0) {
			return formatter.format("%d:%02d:%02d", hours, minutes, seconds)
					.toString();
		} else {
			return formatter.format("%02d:%02d", minutes, seconds).toString();
		}
	}
}
