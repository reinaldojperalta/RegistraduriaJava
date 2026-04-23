package com.adso.backend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.adso.backend.config.ConexionDB;
import com.adso.backend.model.Usuario;

public class UsuarioDAOImpl implements UsuarioDAO {

    @Override
    public Usuario buscarPorDocumento(String numeroDocumento) throws Exception {
        String sql = "SELECT id, numero_documento, nombres, apellidos, rol, password_hash, fecha_registro, activo "
                + "FROM usuarios WHERE numero_documento = ?";

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
    public void insertar(Usuario u) throws Exception {
        String sql = "INSERT INTO usuarios (numero_documento, nombres, apellidos, rol, password_hash, fecha_registro, activo) "
                + "VALUES (?, ?, ?, ?, ?, NOW(), ?)";

        try (Connection con = ConexionDB.getInstancia().getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getNumeroDocumento());
            ps.setString(2, u.getNombres());
            ps.setString(3, u.getApellidos());
            ps.setString(4, u.getRol());
            ps.setString(5, u.getPasswordHash());
            ps.setBoolean(6, u.isActivo());

            ps.executeUpdate();
        }
    }

    @Override
    public void actualizarRolPorDocumento(String numeroDocumento, String rol) throws Exception {
        String sql = "UPDATE usuarios SET rol = ? WHERE numero_documento = ?";

        try (Connection con = ConexionDB.getInstancia().getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, rol);
            ps.setString(2, numeroDocumento);
            ps.executeUpdate();
        }
    }

    @Override
    public boolean validarCredenciales(String numeroDocumento, String password) throws Exception {
        Usuario u = buscarPorDocumento(numeroDocumento);
        if (u == null || !u.esAdmin() || u.getPasswordHash() == null) {
            return false;
        }
        // return org.mindrot.jbcrypt.BCrypt.checkpw(password, u.getPasswordHash());
        return true; // HASH DESACTIVADO TEMPORALMENTE
    }

    private Usuario mapear(ResultSet rs) throws Exception {
        return new Usuario.Builder()
                .id(rs.getInt("id"))
                .numeroDocumento(rs.getString("numero_documento"))
                .nombres(rs.getString("nombres"))
                .apellidos(rs.getString("apellidos"))
                .rol(rs.getString("rol"))
                .passwordHash(rs.getString("password_hash"))
                .fechaRegistro(rs.getTimestamp("fecha_registro") != null
                        ? rs.getTimestamp("fecha_registro").toLocalDateTime()
                        : null)
                .activo(rs.getBoolean("activo"))
                .build();
    }
}