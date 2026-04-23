package com.adso.backend.dao;

import java.util.List;

import com.adso.backend.model.Ciudadano;

public interface CiudadanoDAO {
    List<Ciudadano> listarTodos() throws Exception;

    Ciudadano buscarPorId(int id) throws Exception;

    Ciudadano buscarPorDocumento(String numeroDocumento) throws Exception;

    void insertar(Ciudadano c) throws Exception;

    void actualizar(Ciudadano c) throws Exception;

    void eliminar(int id) throws Exception;

    boolean tieneDocumentos(int idCiudadano) throws Exception;

    int insertarConId(Ciudadano c) throws Exception;
}