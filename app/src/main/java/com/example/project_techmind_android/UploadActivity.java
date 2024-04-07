package com.example.project_techmind_android;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String TAG = "UploadActivity";

    private ImageView imageView;
    private Button uploadButton;
    private ApiService apiService;
    private Uri imageUri;

    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        imageView = findViewById(R.id.imageView);
        uploadButton = findViewById(R.id.uploadButton);
        resultTextView = findViewById(R.id.resultTextView);
        Button dictionaryButton = findViewById(R.id.diseaseDictionaryButton);
        dictionaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create intent to navigate to Dictionary activity
                Intent intent = new Intent(UploadActivity.this, Dictionary.class);
                startActivity(intent);
            }
        });

        Retrofit retrofit = RetrofitClient.getClient("https://4995-27-62-172-193.ngrok-free.app");
        apiService = retrofit.create(ApiService.class);


        uploadButton.setOnClickListener(v -> openGallery());
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
            uploadImage();
        }
    }

    private void uploadImage() {
        resultTextView.setText(" waiting for Result Text ");
        if (imageUri != null) {
            try {
                File imageFile = new File(getRealPathFromURI(imageUri));
                FileInputStream fis = new FileInputStream(imageFile);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int read;
                while ((read = fis.read(buffer)) != -1) {
                    baos.write(buffer, 0, read);
                }
                fis.close();
                byte[] imageData = baos.toByteArray();

                String imageDataBase64 = android.util.Base64.encodeToString(imageData, android.util.Base64.DEFAULT);

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("image", imageDataBase64);

                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

                Call<JsonObject> call = apiService.uploadImage(requestBody);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()) {
                            try {
                                JsonObject responseBody = response.body();
                                if (responseBody != null && responseBody.has("prediction")) {
                                    JsonObject predictionObject = responseBody.getAsJsonObject("prediction");
                                    String highestPrediction = "";
                                    double highestValue = Double.MIN_VALUE;
                                    for (Map.Entry<String, JsonElement> entry : predictionObject.entrySet()) {
                                        String key = entry.getKey();
                                        JsonElement valueElement = entry.getValue();
                                        if (valueElement.isJsonPrimitive() && valueElement.getAsJsonPrimitive().isNumber()) {
                                            double value = valueElement.getAsDouble();
                                            if (value > highestValue) {
                                                highestValue = value;
                                                highestPrediction = key;
                                            }
                                        }
                                    }

                                    // Set the highest prediction to the TextView
                                    String result = "<b>" + highestPrediction + "</b>: <i>" + highestValue + "</i>";
                                    resultTextView.setText(Html.fromHtml(result));
                                } else {
                                    Log.e(TAG, "Prediction key not found in the response");
                                    Toast.makeText(UploadActivity.this, "Prediction key not found in the response", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(UploadActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.e(TAG, "Failed to receive prediction: " + response.message());
                            Toast.makeText(UploadActivity.this, "Failed to receive prediction", Toast.LENGTH_SHORT).show();
                        }
                    }


                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.e(TAG, "Network error: " + t.getMessage());
                        Toast.makeText(UploadActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(UploadActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
        if (cursor == null) {
            return null;
        }
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
    }
}
