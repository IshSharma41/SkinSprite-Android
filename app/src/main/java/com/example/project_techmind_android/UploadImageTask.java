package com.example.project_techmind_android;

import android.os.AsyncTask;
import android.util.Log;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class UploadImageTask extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... params) {
        String imageUrl = "http://your-flask-server-ip:@app.route('/receive-image', methods=['POST'])";
        String base64Image = params[0];

        try {
            URL url = new URL(imageUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            // Send base64 image data
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write("{\"image\": \"" + base64Image + "\"}");
            writer.flush();
            writer.close();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                Log.d("UploadImageTask", "Image sent successfully");
            } else {
                Log.e("UploadImageTask", "Failed to send image. Response code: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("UploadImageTask", "Exception occurred: " + e.getMessage());
        }
        return null;
    }
}
