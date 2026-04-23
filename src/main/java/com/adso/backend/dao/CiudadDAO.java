package com.adso.backend.dao;

import java.util.List;

import com.adso.backend.model.Ciudad;

public interface CiudadDAO {
    List<Ciudad> listarTodas() throws Exception;

    Ciudad buscarPorId(int id) throws Exception;
}