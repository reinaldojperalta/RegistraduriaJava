package com.adso.backend.model;

public class Ciudad {
    private int id;
    private String nombre;
    private String departamento;
    private String codigoDane;

    public Ciudad(Builder builder) {
        this.id = builder.id;
        this.nombre = builder.nombre;
        this.departamento = builder.departamento;
        this.codigoDane = builder.codigoDane;
    }

    public static class Builder {
        private int id;
        private String nombre;
        private String departamento;
        private String codigoDane;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder departamento(String departamento) {
            this.departamento = departamento;
            return this;
        }

        public Builder codigoDane(String codigoDane) {
            this.codigoDane = codigoDane;
            return this;
        }

        public Ciudad build() {
            return new Ciudad(this);
        }
    }

    // GETTERS
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDepartamento() {
        return departamento;
    }

    public String getCodigoDane() {
        return codigoDane;
    }

    // SETTERS
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public void setCodigoDane(String codigoDane) {
        this.codigoDane = codigoDane;
    }
}