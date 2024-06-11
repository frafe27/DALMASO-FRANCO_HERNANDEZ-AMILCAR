package com.backend.finalProject.service;

import com.backend.finalProject.dto.entrada.TurnoEntradaDto;
import com.backend.finalProject.dto.salida.TurnoSalidaDto;

import java.util.List;

public interface ITurnoService {
    TurnoSalidaDto registrarTurno(TurnoEntradaDto turnoEntradaDto);
    List<TurnoSalidaDto> listarTurnos();

    TurnoSalidaDto buscarTurnoPorId(Long id);
    void eliminarTurno(Long id);
    TurnoSalidaDto actualizarTurno(TurnoEntradaDto turnoEntradaDto, Long id);

}
