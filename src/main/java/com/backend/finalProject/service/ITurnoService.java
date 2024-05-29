package com.backend.finalProject.service;

import com.backend.finalProject.entity.Turno;

import java.util.List;

public interface ITurnoService {
    Turno registrarTurno(Turno turno);
    List<Turno> listarTurnos();
}
