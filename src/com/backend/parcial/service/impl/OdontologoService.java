package com.backend.parcial.service.impl;

import com.backend.parcial.entity.Odontologo;
import com.backend.parcial.service.IOdontologoService;
import com.backend.parcial.repository.IDao;

import java.util.List;

public class OdontologoService implements IOdontologoService{
    private IDao<Odontologo> odontologoIDao;

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
