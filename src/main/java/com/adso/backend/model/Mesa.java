package com.adso.backend.model;

public class Mesa {
    private int id;
    private int idZona;
    private int numeroMesa;
    private int capacidad;

    // Campos adicionales para JOIN
    private String nombreZona;
    private String nombreCiudad;
    private String puestoVotacion;

    public Mesa(Builder builder) {
        this.id = builder.id;
        this.idZona = builder.idZona;
        this.numeroMesa = builder.numeroMesa;
        this.capacidad = builder.capacidad;
        this.nombreZona = builder.nombreZona;
        this.nombreCiudad = builder.nombreCiudad;
        this.puestoVotacion = builder.puestoVotacion;
    }

    public static class Builder {
        private int id;
        private int idZona;
        private int numeroMesa;
        private int capacidad;
        private String nombreZona;
        private String nombreCiudad;
        private String puestoVotacion;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder idZona(int idZona) {
            this.idZona = idZona;
            return this;
        }

        public Builder numeroMesa(int numeroMesa) {
            this.numeroMesa = numeroMesa;
            return this;
        }

        public Builder capacidad(int capacidad) {
            this.capacidad = capacidad;
            return this;
        }

        public Builder nombreZona(String nombreZona) {
            this.nombreZona = nombreZona;
            return this;
        }

        public Builder nombreCiudad(String nombreCiudad) {
            this.nombreCiudad = nombreCiudad;
            return this;
        }

        public Builder puestoVotacion(String puestoVotacion) {
            this.puestoVotacion = puestoVotacion;
            return this;
        }

        public Mesa build() {
            return new Mesa(this);
        }
    }

    // GETTERS
    public int getId() {
        return id;
    }

    public int getIdZona() {
        return idZona;
    }

    public int getNumeroMesa() {
        return numeroMesa;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public String getNombreZona() {
        return nombreZona;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public String getPuestoVotacion() {
        return puestoVotacion;
    }

    // SETTERS
    public void setId(int id) {
        this.id = id;
    }

    public void setIdZona(int idZona) {
        this.idZona = idZona;
    }

    public void setNumeroMesa(int numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    // HELPERS
    public String getIdentificacionCompleta() {
        return "Mesa " + numeroMesa + " - " + (nombreZona != null ? nombreZona : "Zona " + idZona);
    }
}