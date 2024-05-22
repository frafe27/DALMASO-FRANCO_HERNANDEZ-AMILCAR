package com.backend.finalProject.repository.impl;

import com.backend.finalProject.entity.Odontologo;
import com.backend.finalProject.repository.IDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class OdontologoMemoria implements IDao<Odontologo> {
    private final Logger LOGGER = LoggerFactory.getLogger(OdontologoMemoria.class);
    ArrayList<Odontologo> odontologos = new ArrayList<>();

    @Override
    public Odontologo registrar(Odontologo odontologo) {
        try {
            if (odontologo.getID() != null) {
                odontologos.add(odontologo);
                return odontologo;
            } else {
                odontologos.add(odontologo);
                odontologo.setID((long) odontologos.size());
                return odontologo;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return odontologo;
    }

    @Override
    public Odontologo buscarPorId(Long id) {
        return null;
    }

    @Override
    public List<Odontologo> listarTodos() {
        try {
            for (Odontologo odontologo : odontologos) {
                LOGGER.info(odontologo.toString());
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return odontologos;
    }
}
