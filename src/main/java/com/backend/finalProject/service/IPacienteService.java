package com.backend.finalProject.service;

import com.backend.finalProject.dto.entrada.PacienteEntradaDto;
import com.backend.finalProject.dto.salida.PacienteSalidaDto;


import java.util.List;

public interface IPacienteService {

    PacienteSalidaDto registrarPaciente(PacienteEntradaDto pacienteEntradaDto);
    List<PacienteSalidaDto> listarPacientes();

    PacienteSalidaDto buscarPacientePorId(Long id);
    void eliminarPaciente(Long id);
    PacienteSalidaDto actualizarPaciente(PacienteEntradaDto pacienteEntradaDto, Long id);

}