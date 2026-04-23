package com.adso.backend.model;

/**
 * DTO para mostrar el resultado de consulta de mesa por documento.
 * JOIN de 4 tablas: ciudadanos, mesas_votacion, zonas_votacion, ciudades.
 */
public class ConsultaDTO {

    private final String numeroDocumento;
    private final String nombres;
    private final String apellidos;
    private final String ciudad;
    private final String codigoDane;
    private final String nombreZona;
    private final String puestoVotacion;
    private final String direccion;
    private final Integer numeroMesa;
    private final Integer capacidad;

    private ConsultaDTO(Builder builder) {
        this.numeroDocumento = builder.numeroDocumento;
        this.nombres = builder.nombres;
        this.apellidos = builder.apellidos;
        this.ciudad = builder.ciudad;
        this.codigoDane = builder.codigoDane;
        this.nombreZona = builder.nombreZona;
        this.puestoVotacion = builder.puestoVotacion;
        this.direccion = builder.direccion;
        this.numeroMesa = builder.numeroMesa;
        this.capacidad = builder.capacidad;
    }

    // ==================== GETTERS (JavaBean compatible con JSP/EL)
    // ====================

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getCodigoDane() {
        return codigoDane;
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

    public Integer getNumeroMesa() {
        return numeroMesa;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    // ==================== HELPERS (compatibles con JSP/EL) ====================

    /**
     * Nombre completo del ciudadano. Compatible con EL: ${resultado.nombreCompleto}
     */
    public String getNombreCompleto() {
        String nom = nombres != null ? nombres.trim() : "";
        String ape = apellidos != null ? apellidos.trim() : "";
        return (nom + " " + ape).trim();
    }

    /**
     * Indica si tiene mesa asignada. Compatible con EL:
     * ${resultado.tieneMesaAsignada}
     * IMPORTANTE: para booleanos en EL, el getter debe empezar con 'is'.
     */
    public boolean isTieneMesaAsignada() {
        return numeroMesa != null;
    }

    /**
     * Descripción formateada de la mesa. Compatible con EL:
     * ${resultado.mesaFormateada}
     */
    public String getMesaFormateada() {
        if (!isTieneMesaAsignada()) {
            return "No inscrito";
        }
        return "Mesa " + numeroMesa + " - " + (nombreZona != null ? nombreZona : "");
    }

    // ==================== BUILDER ====================

    public static class Builder {
        private String numeroDocumento;
        private String nombres;
        private String apellidos;
        private String ciudad;
        private String codigoDane;
        private String nombreZona;
        private String puestoVotacion;
        private String direccion;
        private Integer numeroMesa;
        private Integer capacidad;

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

        public Builder ciudad(String ciudad) {
            this.ciudad = ciudad;
            return this;
        }

        public Builder codigoDane(String codigoDane) {
            this.codigoDane = codigoDane;
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

        public Builder numeroMesa(Integer numeroMesa) {
            this.numeroMesa = numeroMesa;
            return this;
        }

        public Builder capacidad(Integer capacidad) {
            this.capacidad = capacidad;
            return this;
        }

        public ConsultaDTO build() {
            return new ConsultaDTO(this);
        }
    }

    // ==================== DEBUG ====================

    @Override
    public String toString() {
        return "ConsultaDTO{" +
                "numeroDocumento='" + numeroDocumento + '\'' +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", numeroMesa=" + numeroMesa +
                ", tieneMesaAsignada=" + isTieneMesaAsignada() +
                '}';
    }
}