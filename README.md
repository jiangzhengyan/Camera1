## 使用Camera+SurfaceView打开摄像头
1,效果图如下![在这里插入图片描述](https://img-blog.csdnimg.cn/direct/04363640079a4f27bbe52c0fffac76a7.jpeg)

2,代码解析

 2.1. 主布局
 ```java
 <?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity"
    android:id="@+id/rl">

    <SurfaceView
        android:layout_centerInParent="true"
        android:id="@+id/surfaceView"
        android:layout_width="90dp"
        android:layout_height="160dp" />

</RelativeLayout>
```

 2.2.代码逻辑(初始化)
 ```java
  mSurfaceView = findViewById(R.id.surfaceView);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                // 在需要使用相机的地方进行权限检查
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    // 如果权限未被授予，则请求相机权限
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.CAMERA},
                            CAMERA_PERMISSION_REQUEST_CODE);
                } else {
                    // 如果权限已被授予，则直接打开相机
                    openCamera();
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
```
2.3.横竖屏的处理(如果不需要可以忽略)
```java
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
  ```
  2.4.打开相机
  ```java
    // 打开相机的方法
    private void openCamera() {
        // 在这里实现打开相机的代码
        mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
        mCamera.setDisplayOrientation(90);
        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   ```
   2.5.获取权限
   ```java
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
```
3.github代码仓库地址
[github地址点击跳转](https://github.com/jiangzhengyan/Camera1)
欢迎指正
