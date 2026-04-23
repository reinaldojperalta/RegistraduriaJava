package com.adso.backend.dao;

import java.util.List;

import com.adso.backend.model.Mesa;

public interface MesaDAO {
    List<Mesa> listarTodas() throws Exception;

    List<Mesa> listarPorZona(int idZona) throws Exception;

    Mesa buscarPorId(int id) throws Exception;

    void insertar(Mesa m) throws Exception;

    void actualizar(Mesa m) throws Exception;

    void eliminar(int id) throws Exception;
}