package com.adso.backend.dao;

import java.util.List;

import com.adso.backend.model.ConsultaDTO;
import com.adso.backend.model.Mesa;
import com.adso.backend.model.Zona;

public interface ConsultaDAO {
    ConsultaDTO consultarMesaPorDocumento(String numeroDocumento) throws Exception;

    List<Mesa> listarMesasPorZona(int idZona) throws Exception;

    List<Zona> listarZonasPorCiudad(int idCiudad) throws Exception;
}