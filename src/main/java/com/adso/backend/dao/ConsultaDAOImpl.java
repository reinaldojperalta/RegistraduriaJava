package com.adso.backend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.adso.backend.config.ConexionDB;
import com.adso.backend.model.ConsultaDTO;
import com.adso.backend.model.Mesa;
import com.adso.backend.model.Zona;

public class ConsultaDAOImpl implements ConsultaDAO {

    @Override
    public ConsultaDTO consultarMesaPorDocumento(String numeroDocumento) throws Exception {
        String sql = "SELECT c.numero_documento, c.nombres, c.apellidos, "
                + "ci.nombre as ciudad, ci.codigo_dane, "
                + "z.nombre_zona, z.puesto_votacion, z.direccion, "
                + "m.numero_mesa, m.capacidad "
                + "FROM ciudadanos c "
                + "LEFT JOIN mesas_votacion m ON c.id_mesa = m.id "
                + "LEFT JOIN zonas_votacion z ON m.id_zona = z.id "
                + "LEFT JOIN ciudades ci ON z.id_ciudad = ci.id "
                + "WHERE c.numero_documento = ?";

        try (Connection con = ConexionDB.getInstancia().getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, numeroDocumento);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ConsultaDTO.Builder()
                            .numeroDocumento(rs.getString("numero_documento"))
                            .nombres(rs.getString("nombres"))
                            .apellidos(rs.getString("apellidos"))
                            .ciudad(rs.getString("ciudad"))
                            .codigoDane(rs.getString("codigo_dane"))
                            .nombreZona(rs.getString("nombre_zona"))
                            .puestoVotacion(rs.getString("puesto_votacion"))
                            .direccion(rs.getString("direccion"))
                            .numeroMesa(rs.getObject("numero_mesa") != null ? rs.getInt("numero_mesa") : null)
                            .capacidad(rs.getObject("capacidad") != null ? rs.getInt("capacidad") : null)
                            .build();
                }
            }
        }
        return null;
    }

    @Override
    public List<Mesa> listarMesasPorZona(int idZona) throws Exception {
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
                    lista.add(mapearMesa(rs));
                }
            }
        }
        return lista;
    }

    @Override
    public List<Zona> listarZonasPorCiudad(int idCiudad) throws Exception {
        List<Zona> lista = new ArrayList<>();
        String sql = "SELECT z.id, z.id_ciudad, z.nombre_zona, z.puesto_votacion, z.direccion, c.nombre as nombre_ciudad "
                + "FROM zonas_votacion z "
                + "INNER JOIN ciudades c ON z.id_ciudad = c.id "
                + "WHERE z.id_ciudad = ? ORDER BY z.nombre_zona";

        try (Connection con = ConexionDB.getInstancia().getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCiudad);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearZona(rs));
                }
            }
        }
        return lista;
    }

    private Mesa mapearMesa(ResultSet rs) throws Exception {
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

    private Zona mapearZona(ResultSet rs) throws Exception {
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