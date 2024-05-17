package com.backend.parcial.repository.Impl;

import com.backend.parcial.entity.Odontologo;
import com.backend.parcial.repository.IDao;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class OdontologoMemoria implements IDao<Odontologo> {
    private final Logger LOGGER = Logger.getLogger(OdontologoMemoria.class);
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
