package com.example.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private Camera mCamera;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mSurfaceView = findViewById(R.id.surfaceView);


        // 在需要使用相机的地方进行权限检查
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // 如果权限未被授予，则请求相机权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            // 如果权限已被授予，则直接打开相机
            openCamera();
        }


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // 横屏
            Toast.makeText(MainActivity.this, "横屏", Toast.LENGTH_SHORT).show();

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(160, 90);

// 应用新的布局参数
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            mSurfaceView.setLayoutParams(layoutParams);
            mCamera.setDisplayOrientation(0);
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // 竖屏
            Toast.makeText(MainActivity.this, "竖屏", Toast.LENGTH_SHORT).show();
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(90, 160);
            mSurfaceView.setLayoutParams(layoutParams);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            mCamera.setDisplayOrientation(90);
        }


    }


    // 打开相机的方法
    private void openCamera() {
        // 在这里实现打开相机的代码
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
                mCamera.setDisplayOrientation(90);

                try {
                    mCamera.setPreviewDisplay(holder);
                    mCamera.startPreview();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                // 在这里可以调整预览画面的大小和其他配置
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                mCamera.stopPreview();
                mCamera.release();
            }

        });
    }


    // 处理权限请求的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 相机权限已被授予，打开相机
                openCamera();
            } else {
                // 相机权限被拒绝，显示一个提示消息或执行其他操作
            }
        }
    }


}