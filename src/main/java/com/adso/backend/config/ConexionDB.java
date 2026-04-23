package com.adso.backend.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionDB {

    private static ConexionDB instancia;
    private static final Object LOCK = new Object();

    private final String url;
    private final String user;
    private final String password;
    private final String driver;

    private ConexionDB() {
        Properties props = new Properties();
        String resourceName = "db.properties";

        try (InputStream input = getClass().getClassLoader().getResourceAsStream(resourceName)) {

            // DIAGNÓSTICO: ¿Encontró el archivo?
            if (input == null) {
                throw new RuntimeException(
                        "❌ No se encontró '" + resourceName + "' en el classpath.\n" +
                                "   Asegúrate de que esté en: src/main/resources/" + resourceName);
            }
            System.out.println("✅ Archivo " + resourceName + " encontrado en classpath");

            props.load(input);

            // Leer propiedades
            this.url = props.getProperty("pglocal.url");
            this.user = props.getProperty("pglocal.user");
            this.password = props.getProperty("pglocal.password");
            this.driver = props.getProperty("pglocal.driver");

            // DIAGNÓSTICO: Mostrar qué leyó (oculta la contraseña)
            System.out.println("📋 Propiedades leídas:");
            System.out.println("   URL: " + (url != null ? url : "⚠️ NULL"));
            System.out.println("   User: " + (user != null ? user : "⚠️ NULL"));
            System.out.println("   Password: " + (password != null ? "******" : "⚠️ NULL"));
            System.out.println("   Driver: " + (driver != null ? driver : "⚠️ NULL"));

            // Validar que nada sea null
            if (url == null || user == null || password == null || driver == null) {
                throw new RuntimeException(
                        "❌ Una o más propiedades son NULL. Verifica que el archivo db.properties contenga exactamente:\n"
                                +
                                "   pglocal.url=jdbc:postgresql://localhost:5432/TU_BD\n" +
                                "   pglocal.user=postgres\n" +
                                "   pglocal.password=TU_PASS\n" +
                                "   pglocal.driver=org.postgresql.Driver");
            }

            Class.forName(driver);
            System.out.println("✅ Driver " + driver + " cargado correctamente");

        } catch (IOException e) {
            throw new RuntimeException("❌ Error al leer " + resourceName + ": " + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("❌ Driver no encontrado: " + e.getMessage(), e);
        }
    }

    public static ConexionDB getInstancia() {
        if (instancia == null) {
            synchronized (LOCK) {
                if (instancia == null) {
                    instancia = new ConexionDB();
                }
            }
        }
        return instancia;
    }

    public Connection getConexion() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public boolean probarConexion() {
        try (Connection conn = getConexion()) {
            System.out.println("✅ Conexión exitosa a PostgreSQL");
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Error SQL: " + e.getMessage());
            // Si dice "no existe la base de datos", damos instrucciones específicas
            if (e.getMessage().contains("no existe la base de datos") ||
                    e.getMessage().contains("database") && e.getMessage().contains("does not exist")) {
                System.err.println("\n💡 SOLUCIÓN: La base de datos no existe en el servidor.");
                System.err.println("   Crea la base de datos ejecutando en DBeaver:");
                System.err.println("   CREATE DATABASE dbRegistraduria;\n");
            }
            return false;
        }
    }
}