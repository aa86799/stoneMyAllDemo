package com.stone.ui;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.stone.R;
import com.stone.util.DensityUtil;
import com.stone.view.CameraPreview;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends Activity {
    private Camera mCamera;
    private CameraPreview mPreview;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public static final String ORIENTATION = "orientation";
    public static final String ORIENTATION_PORTRAIT = "portrait";
    public static final String ORIENTATION_LANDSCAPE = "landscape";
    private MediaRecorder mMediaRecorder;

      /*
检测并访问摄像机
创建预览类
建立一个预览布局
设置为监听器捕获
捕获和保存文件
松开相机
         */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        if (!checkCameraHardware(this)) {
            return;
        }

        setContentView(R.layout.activity_camera);

        // Create an instance of Camera
        mCamera = getCameraInstance();
        setParametersWithPortrait(mCamera);
//        setParametersWithLandscape(mCamera);

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        Button captureButton = (Button) findViewById(R.id.button_capture);
        captureButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*
                         get an image from the camera
                         快门，raw-img，缩放和压缩-img，jpeg-img
                          */
                        mCamera.takePicture(new Camera.ShutterCallback() {

                            @Override
                            public void onShutter() { //快门按下
                                //加个快门 声
                            }
                        }, new Camera.PictureCallback() { //raw 图像

                            @Override
                            public void onPictureTaken(byte[] data, Camera camera) {

                            }
                        }, new Camera.PictureCallback() { //postview 缩放和压缩图像
                            @Override
                            public void onPictureTaken(byte[] data, Camera camera) {

                            }
                        }, mPicture);


                    }
                }
        );
    }

    /**
     * Check if this device has a camera 检测设备是否有相机可用
     */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    /**
     * A safe way to get an instance of the Camera object. 一个安全的获取相机对象的办法
     */
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance  尝试获取一个实例
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null) {
                Log.d("", "Error creating media file, check storage permissions: ");
                return;
            }

            try {

                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d("", "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d("", "Error accessing file: " + e.getMessage());
            }

            // start preview with new settings
            try {
                mCamera.startPreview();

            } catch (Exception e){
                Log.d("", "Error starting camera preview: " + e.getMessage());
            }
        }
    };

    /**
     * Create a File for saving an image or video
     */
    public static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    //竖屏
    public void setParametersWithPortrait(Camera c) {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            c.setDisplayOrientation(90);
        } else {
            Camera.Parameters parameters = c.getParameters();
            parameters.set(ORIENTATION, ORIENTATION_PORTRAIT);
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

    public void getCameraInfo(Camera c, int cameraId, Camera.CameraInfo cameraInfo) {
        c.getCameraInfo(cameraId, cameraInfo);
//        int numberOfCameras = c.getNumberOfCameras(); //可用相机数量

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_camera_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaRecorder();       // if you are using MediaRecorder, release it first
        releaseCamera();              // release the camera immediately on pause event
    }

    private void releaseMediaRecorder() {
        if (mMediaRecorder != null) {
            mMediaRecorder.reset();   // clear recorder configuration
            mMediaRecorder.release(); // release the recorder object
            mMediaRecorder = null;
            mCamera.lock();           // lock camera for later use
        }
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }

}
