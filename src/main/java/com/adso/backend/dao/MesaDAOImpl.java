package com.adso.backend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.adso.backend.config.ConexionDB;
import com.adso.backend.model.Mesa;

public class MesaDAOImpl implements MesaDAO {

    @Override
    public List<Mesa> listarTodas() throws Exception {
        List<Mesa> lista = new ArrayList<>();
        String sql = "SELECT m.id, m.id_zona, m.numero_mesa, m.capacidad, "
                + "z.nombre_zona, z.puesto_votacion, c.nombre as nombre_ciudad "
                + "FROM mesas_votacion m "
                + "INNER JOIN zonas_votacion z ON m.id_zona = z.id "
                + "INNER JOIN ciudades c ON z.id_ciudad = c.id "
                + "ORDER BY m.id";

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
    public List<Mesa> listarPorZona(int idZona) throws Exception {
        List<Mesa> lista = new ArrayList<>();
        String sql = "SELECT m.id, m.id_zona, m.numero_mesa, m.capacidad, "
                + "z.nombre_zona, z.puesto_votacion, c.nombre as nombre_ciudad "
                + "FROM mesas_votacion m "
                + "INNER JOIN zonas_votacion z ON m.id_zona = z.id "
                + "INNER JOIN ciudades c ON z.id_ciudad = c.id "
                + "WHERE m.id_zona = ? ORDER BY m.numero_mesa";

        try (Connection con = ConexionDB.getInstancia().getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idZona);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        }
        return lista;
    }

    @Override
    public Mesa buscarPorId(int id) throws Exception {
        String sql = "SELECT m.id, m.id_zona, m.numero_mesa, m.capacidad, "
                + "z.nombre_zona, z.puesto_votacion, c.nombre as nombre_ciudad "
                + "FROM mesas_votacion m "
                + "INNER JOIN zonas_votacion z ON m.id_zona = z.id "
                + "INNER JOIN ciudades c ON z.id_ciudad = c.id "
                + "WHERE m.id = ?";

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
    public void insertar(Mesa m) throws Exception {
        String sql = "INSERT INTO mesas_votacion (id_zona, numero_mesa, capacidad) VALUES (?, ?, ?)";

        try (Connection con = ConexionDB.getInstancia().getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, m.getIdZona());
            ps.setInt(2, m.getNumeroMesa());
            ps.setInt(3, m.getCapacidad());
            ps.executeUpdate();
        }
    }

    @Override
    public void actualizar(Mesa m) throws Exception {
        String sql = "UPDATE mesas_votacion SET id_zona = ?, numero_mesa = ?, capacidad = ? WHERE id = ?";

        try (Connection con = ConexionDB.getInstancia().getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, m.getIdZona());
            ps.setInt(2, m.getNumeroMesa());
            ps.setInt(3, m.getCapacidad());
            ps.setInt(4, m.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM mesas_votacion WHERE id = ?";

        try (Connection con = ConexionDB.getInstancia().getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Mesa mapear(ResultSet rs) throws Exception {
        return new Mesa.Builder()
                .id(rs.getInt("id"))
                .idZona(rs.getInt("id_zona"))
                .numeroMesa(rs.getInt("numero_mesa"))
                .capacidad(rs.getInt("capacidad"))
                .nombreZona(rs.getString("nombre_zona"))
                .puestoVotacion(rs.getString("puesto_votacion"))
                .nombreCiudad(rs.getString("nombre_ciudad"))
                .build();
    }
}