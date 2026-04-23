package com.adso.backend.dao;

import java.util.List;

import com.adso.backend.model.Documento;

public interface DocumentoDAO {
    List<Documento> listarTodos() throws Exception; // JOIN con ciudadano

    List<Documento> listarPorCiudadano(int idCiudadano) throws Exception;

    Documento buscarPorId(int id) throws Exception;

    void insertar(Documento d) throws Exception;

    void actualizar(Documento d) throws Exception;

    void cambiarEstado(int id, String estado) throws Exception;

    void eliminar(int id) throws Exception;
}