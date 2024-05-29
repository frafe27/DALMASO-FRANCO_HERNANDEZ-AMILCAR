package com.backend.finalProject.service.impl;
import com.backend.finalProject.entity.Turno;
import com.backend.finalProject.repository.IDao;
import com.backend.finalProject.service.ITurnoService;

import java.util.List;
public class TurnoService implements ITurnoService {
    private IDao<Turno> turnoIDao;

    public TurnoService(IDao<Turno> turnoIDao) {
        this.turnoIDao = turnoIDao;
    }

    @Override
    public Turno registrarTurno(Turno turno) {
        return turnoIDao.registrar(turno);
    }

    @Override
    public List<Turno> listarTurnos() {
        return turnoIDao.listarTodos();
    }
}
