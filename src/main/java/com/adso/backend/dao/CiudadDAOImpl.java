package com.adso.backend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.adso.backend.config.ConexionDB;
import com.adso.backend.model.Ciudad;

public class CiudadDAOImpl implements CiudadDAO {

    @Override
    public List<Ciudad> listarTodas() throws Exception {
        List<Ciudad> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, departamento, codigo_dane FROM ciudades ORDER BY nombre";

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
    public Ciudad buscarPorId(int id) throws Exception {
        String sql = "SELECT id, nombre, departamento, codigo_dane FROM ciudades WHERE id = ?";

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

    private Ciudad mapear(ResultSet rs) throws Exception {
        return new Ciudad.Builder()
                .id(rs.getInt("id"))
                .nombre(rs.getString("nombre"))
                .departamento(rs.getString("departamento"))
                .codigoDane(rs.getString("codigo_dane"))
                .build();
    }
}