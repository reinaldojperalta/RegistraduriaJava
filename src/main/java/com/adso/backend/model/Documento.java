package com.adso.backend.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Documento {
    private int id;
    private int idCiudadano;
    private String tipoDocumento; // Cédula, Tarjeta, Registro Civil, Contraseña
    private String numeroSerie;
    private LocalDate fechaExpedicion;
    private LocalDate fechaVencimiento; // puede ser null
    private String estado; // vigente, vencido, cancelado
    private String observaciones;
    private LocalDateTime fechaRegistro;

    // Campos adicionales para JOIN con ciudadano (no persistidos directamente)
    private String nombresCiudadano;
    private String apellidosCiudadano;
    private String numeroDocumentoCiudadano;

    public Documento(Builder builder) {
        this.id = builder.id;
        this.idCiudadano = builder.idCiudadano;
        this.tipoDocumento = builder.tipoDocumento;
        this.numeroSerie = builder.numeroSerie;
        this.fechaExpedicion = builder.fechaExpedicion;
        this.fechaVencimiento = builder.fechaVencimiento;
        this.estado = builder.estado;
        this.observaciones = builder.observaciones;
        this.fechaRegistro = builder.fechaRegistro;
        this.nombresCiudadano = builder.nombresCiudadano;
        this.apellidosCiudadano = builder.apellidosCiudadano;
        this.numeroDocumentoCiudadano = builder.numeroDocumentoCiudadano;
    }

    public static class Builder {
        private int id;
        private int idCiudadano;
        private String tipoDocumento;
        private String numeroSerie;
        private LocalDate fechaExpedicion;
        private LocalDate fechaVencimiento;
        private String estado;
        private String observaciones;
        private LocalDateTime fechaRegistro;
        private String nombresCiudadano;
        private String apellidosCiudadano;
        private String numeroDocumentoCiudadano;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder idCiudadano(int idCiudadano) {
            this.idCiudadano = idCiudadano;
            return this;
        }

        public Builder tipoDocumento(String tipoDocumento) {
            this.tipoDocumento = tipoDocumento;
            return this;
        }

        public Builder numeroSerie(String numeroSerie) {
            this.numeroSerie = numeroSerie;
            return this;
        }

        public Builder fechaExpedicion(LocalDate fechaExpedicion) {
            this.fechaExpedicion = fechaExpedicion;
            return this;
        }

        public Builder fechaVencimiento(LocalDate fechaVencimiento) {
            this.fechaVencimiento = fechaVencimiento;
            return this;
        }

        public Builder estado(String estado) {
            this.estado = estado;
            return this;
        }

        public Builder observaciones(String observaciones) {
            this.observaciones = observaciones;
            return this;
        }

        public Builder fechaRegistro(LocalDateTime fechaRegistro) {
            this.fechaRegistro = fechaRegistro;
            return this;
        }

        public Builder nombresCiudadano(String nombresCiudadano) {
            this.nombresCiudadano = nombresCiudadano;
            return this;
        }

        public Builder apellidosCiudadano(String apellidosCiudadano) {
            this.apellidosCiudadano = apellidosCiudadano;
            return this;
        }

        public Builder numeroDocumentoCiudadano(String numeroDocumentoCiudadano) {
            this.numeroDocumentoCiudadano = numeroDocumentoCiudadano;
            return this;
        }

        public Documento build() {
            return new Documento(this);
        }
    }

    // GETTERS
    public int getId() {
        return id;
    }

    public int getIdCiudadano() {
        return idCiudadano;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public LocalDate getFechaExpedicion() {
        return fechaExpedicion;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public String getEstado() {
        return estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public String getNombresCiudadano() {
        return nombresCiudadano;
    }

    public String getApellidosCiudadano() {
        return apellidosCiudadano;
    }

    public String getNumeroDocumentoCiudadano() {
        return numeroDocumentoCiudadano;
    }

    // SETTERS
    public void setId(int id) {
        this.id = id;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public void setFechaExpedicion(LocalDate fechaExpedicion) {
        this.fechaExpedicion = fechaExpedicion;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    // HELPERS
    public String getNombreCompletoCiudadano() {
        if (nombresCiudadano != null && apellidosCiudadano != null) {
            return nombresCiudadano + " " + apellidosCiudadano;
        }
        return null;
    }

    public boolean isEstaVigente() {
        return "vigente".equals(estado);
    }

    public boolean estaVencido() {
        if ("vencido".equals(estado))
            return true;
        if (fechaVencimiento != null && fechaVencimiento.isBefore(LocalDate.now())) {
            return true;
        }
        return false;
    }
}