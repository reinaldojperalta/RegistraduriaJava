package com.adso.backend.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

/**
 * Utilidad para validar reCAPTCHA v2 con Google.
 */
public class RecaptchaUtil {

    private static final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    private final String secretKey;

    public RecaptchaUtil(String secretKey) {
        this.secretKey = secretKey;
    }

    public boolean validar(String recaptchaResponse) {
        if (recaptchaResponse == null || recaptchaResponse.isEmpty()) {
            System.err.println("reCAPTCHA: Respuesta vacía");
            return false;
        }

        try {
            URL url = new URL(RECAPTCHA_VERIFY_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String params = "secret=" + URLEncoder.encode(secretKey, StandardCharsets.UTF_8) +
                    "&response=" + URLEncoder.encode(recaptchaResponse, StandardCharsets.UTF_8);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(params.getBytes(StandardCharsets.UTF_8));
            }

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
            }

            JSONObject json = new JSONObject(response.toString());
            boolean success = json.getBoolean("success");

            if (!success && json.has("error-codes")) {
                System.err.println("reCAPTCHA errores: " + json.getJSONArray("error-codes"));
            }

            return success;

        } catch (Exception e) {
            System.err.println("Error validando reCAPTCHA: " + e.getMessage());
            return false;
        }
    }

    public static boolean validarRapido(String recaptchaResponse, String secretKey) {
        return new RecaptchaUtil(secretKey).validar(recaptchaResponse);
    }
}