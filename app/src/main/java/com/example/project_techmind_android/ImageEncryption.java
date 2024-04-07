package com.example.project_techmind_android;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;

public class ImageEncryption {

    private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY = "ThisIsSecretKey123"; // 16 or 32 characters for AES

    public static void main(String[] args) throws Exception {
        // Load image file
        byte[] imageData = new byte[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            imageData = Files.readAllBytes(Paths.get("image.jpg"));
        }

        // Encrypt the image
        byte[] encryptedImageData = encrypt(imageData);

        // Send the encrypted image data to the server
        // (You'll need to implement the server-side logic to receive and decrypt the image)

        // Example: Send encryptedImageData to server
    }

    public static byte[] encrypt(byte[] data) throws Exception {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    private static Key generateKey() {
        return new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
    }
}
