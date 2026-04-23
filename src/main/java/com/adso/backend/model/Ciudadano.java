package com.adso.backend.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Ciudadano {
    private int id;
    private String numeroDocumento;
    private String nombres;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private String veredaBarrio;
    private String telefono;
    private String correo;
    private Integer idMesa; // puede ser null si no está inscrito
    private LocalDateTime fechaRegistro;

    public Ciudadano(Builder builder) {
        this.id = builder.id;
        this.numeroDocumento = builder.numeroDocumento;
        this.nombres = builder.nombres;
        this.apellidos = builder.apellidos;
        this.fechaNacimiento = builder.fechaNacimiento;
        this.veredaBarrio = builder.veredaBarrio;
        this.telefono = builder.telefono;
        this.correo = builder.correo;
        this.idMesa = builder.idMesa;
        this.fechaRegistro = builder.fechaRegistro;
    }

    public static class Builder {
        private int id;
        private String numeroDocumento;
        private String nombres;
        private String apellidos;
        private LocalDate fechaNacimiento;
        private String veredaBarrio;
        private String telefono;
        private String correo;
        private Integer idMesa;
        private LocalDateTime fechaRegistro;

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

        public Builder fechaNacimiento(LocalDate fechaNacimiento) {
            this.fechaNacimiento = fechaNacimiento;
            return this;
        }

        public Builder veredaBarrio(String veredaBarrio) {
            this.veredaBarrio = veredaBarrio;
            return this;
        }

        public Builder telefono(String telefono) {
            this.telefono = telefono;
            return this;
        }

        public Builder correo(String correo) {
            this.correo = correo;
            return this;
        }

        public Builder idMesa(Integer idMesa) {
            this.idMesa = idMesa;
            return this;
        }

        public Builder fechaRegistro(LocalDateTime fechaRegistro) {
            this.fechaRegistro = fechaRegistro;
            return this;
        }

        public Ciudadano build() {
            return new Ciudadano(this);
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

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getVeredaBarrio() {
        return veredaBarrio;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public Integer getIdMesa() {
        return idMesa;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    // SETTERS
    public void setId(int id) {
        this.id = id;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setVeredaBarrio(String veredaBarrio) {
        this.veredaBarrio = veredaBarrio;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setIdMesa(Integer idMesa) {
        this.idMesa = idMesa;
    }

    // HELPERS
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }

    public boolean estaInscrito() {
        return idMesa != null;
    }
}