package com.adso.backend.model;

public class Zona {
    private int id;
    private int idCiudad;
    private String nombreZona;
    private String puestoVotacion;
    private String direccion;

    // Campo adicional para JOIN
    private String nombreCiudad;

    public Zona(Builder builder) {
        this.id = builder.id;
        this.idCiudad = builder.idCiudad;
        this.nombreZona = builder.nombreZona;
        this.puestoVotacion = builder.puestoVotacion;
        this.direccion = builder.direccion;
        this.nombreCiudad = builder.nombreCiudad;
    }

    public static class Builder {
        private int id;
        private int idCiudad;
        private String nombreZona;
        private String puestoVotacion;
        private String direccion;
        private String nombreCiudad;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder idCiudad(int idCiudad) {
            this.idCiudad = idCiudad;
            return this;
        }

        public Builder nombreZona(String nombreZona) {
            this.nombreZona = nombreZona;
            return this;
        }

        public Builder puestoVotacion(String puestoVotacion) {
            this.puestoVotacion = puestoVotacion;
            return this;
        }

        public Builder direccion(String direccion) {
            this.direccion = direccion;
            return this;
        }

        public Builder nombreCiudad(String nombreCiudad) {
            this.nombreCiudad = nombreCiudad;
            return this;
        }

        public Zona build() {
            return new Zona(this);
        }
    }

    // GETTERS
    public int getId() {
        return id;
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public String getNombreZona() {
        return nombreZona;
    }

    public String getPuestoVotacion() {
        return puestoVotacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    // SETTERS
    public void setId(int id) {
        this.id = id;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    public void setNombreZona(String nombreZona) {
        this.nombreZona = nombreZona;
    }

    public void setPuestoVotacion(String puestoVotacion) {
        this.puestoVotacion = puestoVotacion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }
}