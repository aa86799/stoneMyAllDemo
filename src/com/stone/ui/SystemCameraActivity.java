package com.stone.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.stone.R;

import java.io.File;

/**
 * author : stone
 * email  : aa86799@163.com
 * time   : 15/6/16 14 18
 */
public class SystemCameraActivity extends Activity {

    private int SystemCapture1 = 0x100;
    private int SystemCapture2 = 0x101;
    private ImageView iv;
    private Uri uri;
    private String imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_system_camera);
        iv = (ImageView) findViewById(R.id.iv_img);

    }


    public void click1(View view) {
        File file = CameraActivity.getOutputMediaFile(CameraActivity.MEDIA_TYPE_IMAGE);
        uri = Uri.fromFile(file);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, SystemCapture1);

        imgPath = file.getPath();
    }

    public void click2(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, SystemCapture2);
    }

    //调用相机 不回传数据
    public void click3(View view) {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.setAction("android.media.action.STILL_IMAGE_CAMERA");
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SystemCapture1) {
//                iv.setImageURI(uri);  //uri被消费一次
                iv.setImageURI(Uri.fromFile(new File(imgPath)));
                //data 为null
                System.out.println("传入了文件路径，data==null=>" + (data == null));

            } else if (requestCode == SystemCapture2) {
                //data 不为null   获取缩放图像
                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                System.out.println("bmp width:" + bmp.getWidth() + ", height:" + bmp.getHeight());
                //这个bitmap的质量不好
                iv.setImageBitmap(bmp);

            }
        }
    }
}
