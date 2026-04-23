package com.adso.backend.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.adso.backend.config.ConexionDB;
import com.adso.backend.model.Documento;

public class DocumentoDAOImpl implements DocumentoDAO {

    @Override
    public List<Documento> listarTodos() throws Exception {
        List<Documento> lista = new ArrayList<>();
        String sql = "SELECT d.id, d.id_ciudadano, d.tipo_documento, d.numero_serie, "
                + "d.fecha_expedicion, d.fecha_vencimiento, d.estado, d.observaciones, d.fecha_registro, "
                + "c.nombres, c.apellidos, c.numero_documento as doc_ciudadano "
                + "FROM documentos_expedidos d "
                + "INNER JOIN ciudadanos c ON d.id_ciudadano = c.id "
                + "ORDER BY d.fecha_expedicion DESC";

        try (Connection con = ConexionDB.getInstancia().getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearConJoin(rs));
            }
        }
        return lista;
    }

    @Override
    public List<Documento> listarPorCiudadano(int idCiudadano) throws Exception {
        List<Documento> lista = new ArrayList<>();
        String sql = "SELECT id, id_ciudadano, tipo_documento, numero_serie, "
                + "fecha_expedicion, fecha_vencimiento, estado, observaciones, fecha_registro "
                + "FROM documentos_expedidos WHERE id_ciudadano = ? ORDER BY fecha_expedicion DESC";

        try (Connection con = ConexionDB.getInstancia().getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCiudadano);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        }
        return lista;
    }

    @Override
    public Documento buscarPorId(int id) throws Exception {
        String sql = "SELECT id, id_ciudadano, tipo_documento, numero_serie, "
                + "fecha_expedicion, fecha_vencimiento, estado, observaciones, fecha_registro "
                + "FROM documentos_expedidos WHERE id = ?";

        try (Connection con = ConexionDB.getInstancia().getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }
        return null;
    }

    @Override
    public void insertar(Documento d) throws Exception {
        String sql = "INSERT INTO documentos_expedidos (id_ciudadano, tipo_documento, numero_serie, "
                + "fecha_expedicion, fecha_vencimiento, estado, observaciones) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.getInstancia().getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, d.getIdCiudadano());
            ps.setString(2, d.getTipoDocumento());
            ps.setString(3, d.getNumeroSerie());
            ps.setDate(4, Date.valueOf(d.getFechaExpedicion()));
            if (d.getFechaVencimiento() != null) {
                ps.setDate(5, Date.valueOf(d.getFechaVencimiento()));
            } else {
                ps.setNull(5, Types.DATE);
            }
            ps.setString(6, d.getEstado());
            ps.setString(7, d.getObservaciones());
            ps.executeUpdate();
        }
    }

    @Override
    public void actualizar(Documento d) throws Exception {
        String sql = "UPDATE documentos_expedidos SET tipo_documento = ?, numero_serie = ?, "
                + "fecha_expedicion = ?, fecha_vencimiento = ?, estado = ?, observaciones = ? WHERE id = ?";

        try (Connection con = ConexionDB.getInstancia().getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, d.getTipoDocumento());
            ps.setString(2, d.getNumeroSerie());
            ps.setDate(3, Date.valueOf(d.getFechaExpedicion()));
            if (d.getFechaVencimiento() != null) {
                ps.setDate(4, Date.valueOf(d.getFechaVencimiento()));
            } else {
                ps.setNull(4, Types.DATE);
            }
            ps.setString(5, d.getEstado());
            ps.setString(6, d.getObservaciones());
            ps.setInt(7, d.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void cambiarEstado(int id, String estado) throws Exception {
        String sql = "UPDATE documentos_expedidos SET estado = ? WHERE id = ?";

        try (Connection con = ConexionDB.getInstancia().getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, estado);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM documentos_expedidos WHERE id = ?";

        try (Connection con = ConexionDB.getInstancia().getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Documento mapear(ResultSet rs) throws Exception {
        return new Documento.Builder()
                .id(rs.getInt("id"))
                .idCiudadano(rs.getInt("id_ciudadano"))
                .tipoDocumento(rs.getString("tipo_documento"))
                .numeroSerie(rs.getString("numero_serie"))
                .fechaExpedicion(rs.getDate("fecha_expedicion") != null
                        ? rs.getDate("fecha_expedicion").toLocalDate()
                        : null)
                .fechaVencimiento(rs.getDate("fecha_vencimiento") != null
                        ? rs.getDate("fecha_vencimiento").toLocalDate()
                        : null)
                .estado(rs.getString("estado"))
                .observaciones(rs.getString("observaciones"))
                .fechaRegistro(rs.getTimestamp("fecha_registro") != null
                        ? rs.getTimestamp("fecha_registro").toLocalDateTime()
                        : null)
                .build();
    }

    private Documento mapearConJoin(ResultSet rs) throws Exception {
        return new Documento.Builder()
                .id(rs.getInt("id"))
                .idCiudadano(rs.getInt("id_ciudadano"))
                .tipoDocumento(rs.getString("tipo_documento"))
                .numeroSerie(rs.getString("numero_serie"))
                .fechaExpedicion(rs.getDate("fecha_expedicion") != null
                        ? rs.getDate("fecha_expedicion").toLocalDate()
                        : null)
                .fechaVencimiento(rs.getDate("fecha_vencimiento") != null
                        ? rs.getDate("fecha_vencimiento").toLocalDate()
                        : null)
                .estado(rs.getString("estado"))
                .observaciones(rs.getString("observaciones"))
                .fechaRegistro(rs.getTimestamp("fecha_registro") != null
                        ? rs.getTimestamp("fecha_registro").toLocalDateTime()
                        : null)
                .nombresCiudadano(rs.getString("nombres"))
                .apellidosCiudadano(rs.getString("apellidos"))
                .numeroDocumentoCiudadano(rs.getString("doc_ciudadano"))
                .build();
    }
}