package com.adso.backend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.adso.backend.config.ConexionDB;
import com.adso.backend.model.Zona;

public class ZonaDAOImpl implements ZonaDAO {

    @Override
    public List<Zona> listarTodas() throws Exception {
        List<Zona> lista = new ArrayList<>();
        String sql = "SELECT z.id, z.id_ciudad, z.nombre_zona, z.puesto_votacion, z.direccion, c.nombre as nombre_ciudad "
                + "FROM zonas_votacion z INNER JOIN ciudades c ON z.id_ciudad = c.id ORDER BY z.id";

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
    public List<Zona> listarPorCiudad(int idCiudad) throws Exception {
        List<Zona> lista = new ArrayList<>();
        String sql = "SELECT z.id, z.id_ciudad, z.nombre_zona, z.puesto_votacion, z.direccion, c.nombre as nombre_ciudad "
                + "FROM zonas_votacion z INNER JOIN ciudades c ON z.id_ciudad = c.id "
                + "WHERE z.id_ciudad = ? ORDER BY z.nombre_zona";

        try (Connection con = ConexionDB.getInstancia().getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCiudad);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        }
        return lista;
    }

    @Override
    public Zona buscarPorId(int id) throws Exception {
        String sql = "SELECT z.id, z.id_ciudad, z.nombre_zona, z.puesto_votacion, z.direccion, c.nombre as nombre_ciudad "
                + "FROM zonas_votacion z INNER JOIN ciudades c ON z.id_ciudad = c.id WHERE z.id = ?";

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
    public void insertar(Zona z) throws Exception {
        String sql = "INSERT INTO zonas_votacion (id_ciudad, nombre_zona, puesto_votacion, direccion) VALUES (?, ?, ?, ?)";

        try (Connection con = ConexionDB.getInstancia().getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, z.getIdCiudad());
            ps.setString(2, z.getNombreZona());
            ps.setString(3, z.getPuestoVotacion());
            ps.setString(4, z.getDireccion());
            ps.executeUpdate();
        }
    }

    @Override
    public void actualizar(Zona z) throws Exception {
        String sql = "UPDATE zonas_votacion SET id_ciudad = ?, nombre_zona = ?, puesto_votacion = ?, direccion = ? WHERE id = ?";

        try (Connection con = ConexionDB.getInstancia().getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, z.getIdCiudad());
            ps.setString(2, z.getNombreZona());
            ps.setString(3, z.getPuestoVotacion());
            ps.setString(4, z.getDireccion());
            ps.setInt(5, z.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM zonas_votacion WHERE id = ?";

        try (Connection con = ConexionDB.getInstancia().getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Zona mapear(ResultSet rs) throws Exception {
        return new Zona.Builder()
                .id(rs.getInt("id"))
                .idCiudad(rs.getInt("id_ciudad"))
                .nombreZona(rs.getString("nombre_zona"))
                .puestoVotacion(rs.getString("puesto_votacion"))
                .direccion(rs.getString("direccion"))
                .nombreCiudad(rs.getString("nombre_ciudad"))
                .build();
    }
}