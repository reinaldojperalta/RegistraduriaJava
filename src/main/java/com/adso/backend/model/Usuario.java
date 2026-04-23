package com.adso.backend.model;

public class Usuario {
    private int id;
    private String numeroDocumento;
    private String nombres;
    private String apellidos;
    private String rol; // ADMIN o VOTANTE
    private String passwordHash; // null para votantes
    private java.time.LocalDateTime fechaRegistro;
    private boolean activo;

    public Usuario(Builder builder) {
        this.id = builder.id;
        this.numeroDocumento = builder.numeroDocumento;
        this.nombres = builder.nombres;
        this.apellidos = builder.apellidos;
        this.rol = builder.rol;
        this.passwordHash = builder.passwordHash;
        this.fechaRegistro = builder.fechaRegistro;
        this.activo = builder.activo;
    }

    public static class Builder {
        private int id;
        private String numeroDocumento;
        private String nombres;
        private String apellidos;
        private String rol;
        private String passwordHash;
        private java.time.LocalDateTime fechaRegistro;
        private boolean activo = true; // default

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder numeroDocumento(String numeroDocumento) {
            this.numeroDocumento = numeroDocumento;
            return this;
        }

        public Builder nombres(String nombres) {
            this.nombres = nombres;
            return this;
        }

        public Builder apellidos(String apellidos) {
            this.apellidos = apellidos;
            return this;
        }

        public Builder rol(String rol) {
            this.rol = rol;
            return this;
        }

        public Builder passwordHash(String passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }

        public Builder fechaRegistro(java.time.LocalDateTime fechaRegistro) {
            this.fechaRegistro = fechaRegistro;
            return this;
        }

        public Builder activo(boolean activo) {
            this.activo = activo;
            return this;
        }

        public Usuario build() {
            return new Usuario(this);
        }
    }

    // GETTERS
    public int getId() {
        return id;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getRol() {
        return rol;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public java.time.LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public boolean isActivo() {
        return activo;
    }

    // SETTERS (solo los que pueden cambiar después de creado)
    public void setId(int id) {
        this.id = id;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    // Helper para mostrar nombre completo
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }

    // Helper para saber si es admin
    public boolean esAdmin() {
        return "ADMIN".equals(rol);
    }
}