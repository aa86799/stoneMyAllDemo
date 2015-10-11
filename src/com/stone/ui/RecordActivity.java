package com.stone.ui;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.stone.R;
import com.stone.util.ViewUtil;
import com.stone.view.CameraPreview;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class RecordActivity extends Activity implements SurfaceHolder.Callback, Camera.AutoFocusCallback {
    private int mWdith, mHeight;

    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;
    private CameraPreview mPreview;
    private MediaRecorder mMediaRecorder;
    private Camera.Size mCameaSize;
    private boolean isRecording;
    private String mMediaFile;

    private TextView mTvTime;

    private static final String ORIENTATION = "orientation";
    private static final String ORIENTATION_PORTRAIT = "portrait";
    private static final String ORIENTATION_LANDSCAPE = "landscape";

    private static final int TIME_CHANGED = 0x10;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager
                .LayoutParams.FLAG_FULLSCREEN);

        mWdith = getResources().getDisplayMetrics().widthPixels;
        mHeight = getResources().getDisplayMetrics().heightPixels;

        File movieFile = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_MOVIES) + File.separator + "MyCameraApp");
        if (!movieFile.exists()) {
            if (!movieFile.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        mMediaFile = movieFile.getAbsolutePath() + File.separator + "VID_" + timeStamp + ".mp4";


        setContentView(R.layout.record_activity);

        mSurfaceView = getView(R.id.sv_main);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        mTvTime = getView(R.id.tv_time);


    }


    private void initRecorder() {
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
//        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
//        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
//        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mMediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_1080P));
//        mMediaRecorder.setVideoEncodingBitRate(15000);
//        mMediaRecorder.setAudioEncodingBitRate(10000);
//        mMediaRecorder.setAudioSamplingRate(20000);
//        mMediaRecorder.setAudioChannels(2);
//        mMediaRecorder.setVideoFrameRate(24);
//        mMediaRecorder.setVideoSize(w, h);
//        mMediaRecorder.setMaxFileSize(10*1024*1024);
        mMediaRecorder.setOutputFile(mMediaFile);
        mMediaRecorder.setMaxDuration(10 * 1000);

        mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());

        mMediaRecorder.setOnInfoListener(new MediaRecorder.OnInfoListener() {
            @Override
            public void onInfo(MediaRecorder mr, int what, int extra) {
                if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
                    System.out.println("stop.....");
//                    mr.stop();
                    stopRecordingVideo();
                }
            }
        });
    }

    private <T> T getView(int resid) {
		return ViewUtil.findViewById(this, resid);
	}

    public void record(View view) {
        if (isRecording) {
            stopRecordingVideo();
        } else {
            mCamera.stopPreview();
            startRecordingVideo();
        }

    }

    private void startPreview() {
        mCamera = getCameraInstance();
        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setParametersWithPortrait(mCamera);
        mCamera.startPreview();

    }

    private void startRecordingVideo() {
        isRecording = true;
        mMediaRecorder = new MediaRecorder();
        mCamera.unlock();
        mMediaRecorder.setCamera(mCamera);
        initRecorder();
        try {
            mMediaRecorder.prepare();
            mMediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
            finish();
        }

    }

    private void stopRecordingVideo() {
        mMediaRecorder.stop();
        mMediaRecorder.release();
        mCamera.lock();
        mCamera.stopPreview();
        mCamera.release();

        startPreview();
        isRecording = false;
    }

    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance  尝试获取一个实例
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    //竖屏
    public void setParametersWithPortrait(Camera c) {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            c.setDisplayOrientation(90);
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {//api9
                Camera.Parameters parameters = c.getParameters();
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
                mCameaSize = getBestPreviewSize(mWdith, mHeight, parameters);
                parameters.setPreviewSize(mCameaSize.width, mCameaSize.height);
                mCamera.setParameters(parameters);
            }
        } else {
            Camera.Parameters parameters = c.getParameters();
            parameters.set(ORIENTATION, ORIENTATION_LANDSCAPE);
        }
    }

    //横屏
    public void setParametersWithLandscape(Camera c) {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            c.setDisplayOrientation(0);
        } else {
            Camera.Parameters parameters = c.getParameters();
            parameters.set(ORIENTATION, ORIENTATION_LANDSCAPE);
        }
    }

    private Camera.Size getBestPreviewSize(int width, int height, Camera.Parameters parameters) {
        Camera.Size result = null;

        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result = size;
                } else {
                    int resultArea = result.width * result.height;
                    int newArea = size.width * size.height;

                    if (newArea > resultArea) {
                        result = size;
                    }
                }
            }
        }

        return result;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        System.out.println("onConfigurationChanged");
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setParametersWithLandscape(mCamera);
        } else {
            setParametersWithLandscape(mCamera);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startPreview();
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCamera.stopPreview();
        mCamera.release();
    }

    private boolean isFocusSuccess;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mCamera == null) return super.onTouchEvent(event);

        float x = event.getRawX();
        float y = event.getRawY();
        Rect focusRect = calculateTapArea(x, y, 1f);
        Rect meteringRect = calculateTapArea(x, y, 1.5f);

        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);

        if (parameters.getMaxNumFocusAreas() > 0) {
            List<Camera.Area> focusAreas = new ArrayList<Camera.Area>();
            focusAreas.add(new Camera.Area(focusRect, 1000));

            parameters.setFocusAreas(focusAreas);
        }

        if (parameters.getMaxNumMeteringAreas() > 0) {
            List<Camera.Area> meteringAreas = new ArrayList<Camera.Area>();
            meteringAreas.add(new Camera.Area(meteringRect, 1000));

            parameters.setMeteringAreas(meteringAreas);
        }

        mCamera.setParameters(parameters);
        mCamera.autoFocus(this);

        return true;
    }

    private Rect calculateTapArea(float x, float y, float coefficient) {
        float focusAreaSize = 300;
        int areaSize = Float.valueOf(focusAreaSize * coefficient).intValue();

        int centerX = (int) (x / getResolution().width * 2000 - 1000);
        int centerY = (int) (y / getResolution().height * 2000 - 1000);

        int left = clamp(centerX - areaSize / 2, -1000, 1000);
        int right = clamp(left + areaSize, -1000, 1000);
        int top = clamp(centerY - areaSize / 2, -1000, 1000);
        int bottom = clamp(top + areaSize, -1000, 1000);

        return new Rect(left, top, right, bottom);
    }

    private int clamp(int x, int min, int max) {
        if (x > max) {
            return max;
        }
        if (x < min) {
            return min;
        }
        return x;
    }

    private Camera.Size getResolution() {
        Camera.Parameters params = mCamera.getParameters();
        Camera.Size s = params.getPreviewSize();
        return s;
    }

    private List<Camera.Size> getResolutionList() {
        return  mCamera.getParameters().getSupportedPreviewSizes();
    }

    private List<Camera.Area> newAreaList(Camera.Area area) {
        List<Camera.Area> list = new ArrayList<Camera.Area>();
        list.add(area);
        return list;
    }

    @Override
    public void onAutoFocus(boolean success, Camera camera) {

    }
}