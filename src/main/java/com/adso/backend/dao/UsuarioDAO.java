package com.adso.backend.dao;

import com.adso.backend.model.Usuario;

public interface UsuarioDAO {
    Usuario buscarPorDocumento(String numeroDocumento) throws Exception;

    boolean validarCredenciales(String numeroDocumento, String password) throws Exception;

    void insertar(Usuario u) throws Exception;

    void actualizarRolPorDocumento(String numeroDocumento, String rol) throws Exception;
}