package com.example.project_techmind_android;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.IOException;

public class UploadActivity extends AppCompatActivity implements SurfaceHolder.Callback {
        private static final int UPLOAD_RESULT_CODE = 101;

        // Your activity code...


    private static final int REQUEST_IMAGE_FROM_GALLERY = 2;
    private static final int REQUEST_CAMERA_PERMISSION = 101;
    private SurfaceView cameraPreview;
    private SurfaceHolder surfaceHolder;
    private CameraHelper cameraHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        cameraPreview = findViewById(R.id.camera_preview);
        cameraPreview.setVisibility(View.VISIBLE);
        cameraPreview.setZOrderMediaOverlay(true);
        surfaceHolder = cameraPreview.getHolder();
        surfaceHolder.addCallback(this);

        ImageView imageView = findViewById(R.id.imageView);

        Button btnGallery = findViewById(R.id.btnGallery);
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        Button btnCapture = findViewById(R.id.btnCapture);
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cameraHelper != null) {
                    cameraHelper.takePicture();
                }
            }
        });
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_IMAGE_FROM_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_FROM_GALLERY && data != null) {
            // Code for handling gallery image selection
        } else if (resultCode == RESULT_OK && requestCode == CameraHelper.REQUEST_IMAGE_CAPTURE) {
            // Code for handling camera image capture
        }

// Receive JSON string from UploadImageTask
        if (resultCode == RESULT_OK && requestCode == UPLOAD_RESULT_CODE && data != null) {
            String jsonString = data.getStringExtra("jsonString");
            // Start DisplayResultActivity and pass JSON string as extra
            Intent intent = new Intent(UploadActivity.this, DisplayResultActivity.class);
            intent.putExtra("jsonString", jsonString);
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (surfaceHolder != null) {
                    cameraHelper = new CameraHelper(UploadActivity.this, surfaceHolder, findViewById(R.id.imageView));
                }
                cameraHelper.openCamera();
            } else {
                Toast.makeText(this, "Camera permission is required", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            if (surfaceHolder != null) {
                cameraHelper = new CameraHelper(UploadActivity.this, surfaceHolder, findViewById(R.id.imageView));
            }
            cameraHelper.openCamera();
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        cameraHelper.startPreview();
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        cameraHelper.releaseCamera();
    }
}
