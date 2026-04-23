package com.adso.backend.test;

import com.adso.backend.config.ConexionDB;

public class TestConexion {
    public static void main(String[] args) {
        System.out.println("=== TEST DE CONEXIÓN POSTGRESQL ===\n");

        try {
            // Obtener instancia Singleton
            ConexionDB db = ConexionDB.getInstancia();

            // Probar conexión
            boolean ok = db.probarConexion();

            if (ok) {
                System.out.println("\n✅ RESULTADO: Conexión exitosa a la base de datos.");
            } else {
                System.out.println("\n❌ RESULTADO: No se pudo conectar.");
                System.out.println("   Revisa que PostgreSQL esté corriendo y que la base de datos exista.");
            }

        } catch (ExceptionInInitializerError e) {
            System.err.println("\n❌ ERROR AL INICIALIZAR ConexionDB:");
            System.err.println("   " + e.getCause().getMessage());
        } catch (Exception e) {
            System.err.println("\n❌ ERROR INESPERADO:");
            e.printStackTrace();
        }
    }
}