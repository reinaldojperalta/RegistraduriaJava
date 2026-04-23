package com.adso.backend.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuración de reCAPTCHA cargada desde recaptcha.properties
 */
public class RecaptchaConfig {

    private static final String PROPERTIES_FILE = "recaptcha.properties";
    private static RecaptchaConfig instancia;

    private final String siteKey;
    private final String secretKey;
    private final boolean enabled;

    private RecaptchaConfig() {
        Properties props = new Properties();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input != null) {
                props.load(input);
                this.siteKey = props.getProperty("recaptcha.site.key", "");
                this.secretKey = props.getProperty("recaptcha.secret.key", "");
                this.enabled = Boolean.parseBoolean(props.getProperty("recaptcha.enabled", "true"));
            } else {
                this.siteKey = "";
                this.secretKey = "";
                this.enabled = false;
                System.err.println("Advertencia: No se encontró " + PROPERTIES_FILE);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error cargando reCAPTCHA config", e);
        }
    }

    public static synchronized RecaptchaConfig getInstancia() {
        if (instancia == null) {
            instancia = new RecaptchaConfig();
        }
        return instancia;
    }

    public String getSiteKey() {
        return siteKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public boolean isEnabled() {
        return enabled;
    }
}