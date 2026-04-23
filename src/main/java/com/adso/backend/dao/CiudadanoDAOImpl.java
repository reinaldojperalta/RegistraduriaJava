package com.adso.backend.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.adso.backend.config.ConexionDB;
import com.adso.backend.model.Ciudadano;

public class CiudadanoDAOImpl implements CiudadanoDAO {

    @Override
    public List<Ciudadano> listarTodos() throws Exception {
        List<Ciudadano> lista = new ArrayList<>();
        String sql = "SELECT id, numero_documento, nombres, apellidos, fecha_nacimiento, "
                + "vereda_barrio, telefono, correo, id_mesa, fecha_registro "
                + "FROM ciudadanos ORDER BY id";

        try (Connection con = ConexionDB.getInstancia().getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    @Override
    public Ciudadano buscarPorId(int id) throws Exception {
        String sql = "SELECT id, numero_documento, nombres, apellidos, fecha_nacimiento, "
                + "vereda_barrio, telefono, correo, id_mesa, fecha_registro "
                + "FROM ciudadanos WHERE id = ?";

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
    public Ciudadano buscarPorDocumento(String numeroDocumento) throws Exception {
        String sql = "SELECT id, numero_documento, nombres, apellidos, fecha_nacimiento, "
                + "vereda_barrio, telefono, correo, id_mesa, fecha_registro "
                + "FROM ciudadanos WHERE numero_documento = ?";

        try (Connection con = ConexionDB.getInstancia().getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, numeroDocumento);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }
        return null;
    }

    @Override
    public void insertar(Ciudadano c) throws Exception {
        String sql = "INSERT INTO ciudadanos (numero_documento, nombres, apellidos, fecha_nacimiento, "
                + "vereda_barrio, telefono, correo, id_mesa) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.getInstancia().getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getNumeroDocumento());
            ps.setString(2, c.getNombres());
            ps.setString(3, c.getApellidos());
            ps.setDate(4, Date.valueOf(c.getFechaNacimiento()));
            ps.setString(5, c.getVeredaBarrio());
            ps.setString(6, c.getTelefono());
            ps.setString(7, c.getCorreo());
            if (c.getIdMesa() != null) {
                ps.setInt(8, c.getIdMesa());
            } else {
                ps.setNull(8, Types.INTEGER);
            }
            ps.executeUpdate();
        }
    }

    @Override
    public void actualizar(Ciudadano c) throws Exception {
        String sql = "UPDATE ciudadanos SET nombres = ?, apellidos = ?, fecha_nacimiento = ?, "
                + "vereda_barrio = ?, telefono = ?, correo = ?, id_mesa = ? WHERE id = ?";

        try (Connection con = ConexionDB.getInstancia().getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getNombres());
            ps.setString(2, c.getApellidos());
            ps.setDate(3, Date.valueOf(c.getFechaNacimiento()));
            ps.setString(4, c.getVeredaBarrio());
            ps.setString(5, c.getTelefono());
            ps.setString(6, c.getCorreo());
            if (c.getIdMesa() != null) {
                ps.setInt(7, c.getIdMesa());
            } else {
                ps.setNull(7, Types.INTEGER);
            }
            ps.setInt(8, c.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM ciudadanos WHERE id = ?";

        try (Connection con = ConexionDB.getInstancia().getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public boolean tieneDocumentos(int idCiudadano) throws Exception {
        String sql = "SELECT COUNT(*) FROM documentos_expedidos WHERE id_ciudadano = ?";

        try (Connection con = ConexionDB.getInstancia().getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCiudadano);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    @Override
    public int insertarConId(Ciudadano c) throws Exception {
        String sql = "INSERT INTO ciudadanos (numero_documento, nombres, apellidos, fecha_nacimiento, "
                + "vereda_barrio, telefono, correo, id_mesa) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.getInstancia().getConexion();
                PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, c.getNumeroDocumento());
            ps.setString(2, c.getNombres());
            ps.setString(3, c.getApellidos());
            ps.setDate(4, Date.valueOf(c.getFechaNacimiento()));
            ps.setString(5, c.getVeredaBarrio());
            ps.setString(6, c.getTelefono());
            ps.setString(7, c.getCorreo());
            if (c.getIdMesa() != null) {
                ps.setInt(8, c.getIdMesa());
            } else {
                ps.setNull(8, Types.INTEGER);
            }
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("No se pudo obtener el ID generado del ciudadano");
    }

    private Ciudadano mapear(ResultSet rs) throws Exception {
        return new Ciudadano.Builder()
                .id(rs.getInt("id"))
                .numeroDocumento(rs.getString("numero_documento"))
                .nombres(rs.getString("nombres"))
                .apellidos(rs.getString("apellidos"))
                .fechaNacimiento(rs.getDate("fecha_nacimiento") != null
                        ? rs.getDate("fecha_nacimiento").toLocalDate()
                        : null)
                .veredaBarrio(rs.getString("vereda_barrio"))
                .telefono(rs.getString("telefono"))
                .correo(rs.getString("correo"))
                .idMesa(rs.getObject("id_mesa") != null ? rs.getInt("id_mesa") : null)
                .fechaRegistro(rs.getTimestamp("fecha_registro") != null
                        ? rs.getTimestamp("fecha_registro").toLocalDateTime()
                        : null)
                .build();
    }
}