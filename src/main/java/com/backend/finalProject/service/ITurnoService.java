package com.backend.finalProject.service;

import com.backend.finalProject.dto.entrada.TurnoEntradaDto;
import com.backend.finalProject.dto.salida.TurnoSalidaDto;
import com.backend.finalProject.exceptions.BadRequestException;

import java.util.List;

public interface ITurnoService {
    TurnoSalidaDto registrarTurno(TurnoEntradaDto turnoEntradaDto) throws BadRequestException;
    List<TurnoSalidaDto> listarTurnos();

    TurnoSalidaDto buscarTurnoPorId(Long id);
    void eliminarTurno(Long id);
    TurnoSalidaDto actualizarTurno(TurnoEntradaDto turnoEntradaDto, Long id);

}
