package com.backend.finalProject.service;

import com.backend.finalProject.dto.entrada.OdontologoEntradaDto;
import com.backend.finalProject.dto.salida.OdontologoSalidaDto;

import java.util.List;

public interface IOdontologoService {
    OdontologoSalidaDto registrarOdontologo(OdontologoEntradaDto odontologoEntradaDto);

    List<OdontologoSalidaDto> listarTodos();
}
