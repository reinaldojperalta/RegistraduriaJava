package com.adso.backend.dao;

import java.util.List;

import com.adso.backend.model.Zona;

public interface ZonaDAO {
    List<Zona> listarTodas() throws Exception;

    List<Zona> listarPorCiudad(int idCiudad) throws Exception;

    Zona buscarPorId(int id) throws Exception;

    void insertar(Zona z) throws Exception;

    void actualizar(Zona z) throws Exception;

    void eliminar(int id) throws Exception;
}