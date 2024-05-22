package com.backend.finalProject.service;

import com.backend.finalProject.entity.Paciente;

import java.util.List;

public interface IPacienteService {

    Paciente registrarPaciente(Paciente paciente);
    List<Paciente> listarPacientes();
}
