package com.example.project_techmind_android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

@SuppressWarnings("deprecation")
public class CameraHelper implements SurfaceHolder.Callback, Camera.PictureCallback {

    public static final int REQUEST_IMAGE_CAPTURE = 1001;
    private Bitmap capturedImageBitmap;
    private Camera camera;
    private SurfaceHolder surfaceHolder;
    private Context context;
    private ImageView imageView;

    public CameraHelper(Context context, SurfaceHolder surfaceHolder, ImageView imageView) {
        this.context = context;
        this.surfaceHolder = surfaceHolder;
        this.imageView = imageView;
    }

    public void openCamera() {
        try {
            camera = Camera.open();
            camera.setPreviewDisplay(surfaceHolder);
            setCameraDisplayOrientation();
        } catch (IOException e) {
            Log.e("CameraHelper", "Error setting camera preview: " + e.getMessage());
        }
    }

    public void releaseCamera() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    public void startPreview() {
        if (camera != null) {
            camera.startPreview();
        }
    }

    public void takePicture() {
        if (camera != null) {
            camera.takePicture(null, null, this);
        }

    }
    public Bitmap getCapturedImageBitmap() {
        return capturedImageBitmap;
    }

    private void setCameraDisplayOrientation() {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(Camera.CameraInfo.CAMERA_FACING_BACK, info);
        int rotation = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;
        } else {
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            Toast.makeText(context, "Failed to capture image", Toast.LENGTH_SHORT).show();
        }
        camera.startPreview();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        openCamera();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();
    }
}
