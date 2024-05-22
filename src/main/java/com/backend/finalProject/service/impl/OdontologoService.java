package com.backend.finalProject.service.impl;

import com.backend.finalProject.entity.Odontologo;
import com.backend.finalProject.repository.IDao;
import com.backend.finalProject.service.IOdontologoService;

import java.util.List;

public class OdontologoService implements IOdontologoService {
    private final IDao<Odontologo> odontologoIDao;

    public OdontologoService(IDao<Odontologo> odontologoIDao) {
        this.odontologoIDao = odontologoIDao;
    }

    @Override
    public Odontologo registrar(Odontologo odontologo) {
        return odontologoIDao.registrar(odontologo);
    }

    @Override
    public List<Odontologo> listarTodos() {
        return odontologoIDao.listarTodos();
    }
}
