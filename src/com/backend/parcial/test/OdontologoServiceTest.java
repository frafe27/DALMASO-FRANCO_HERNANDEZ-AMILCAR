package com.backend.parcial.test;

import com.backend.parcial.entity.Odontologo;
import com.backend.parcial.repository.Impl.OdontologoDaoH2;
import com.backend.parcial.repository.Impl.OdontologoMemoria;
import com.backend.parcial.service.impl.OdontologoService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OdontologoServiceTest {

    private OdontologoService odontologoService;

    @Test
    void deberiaRegistrarseUnOdontologoEnLaBaseDeDatos() {

        odontologoService = new OdontologoService(new OdontologoDaoH2());
        Odontologo odontologo = new Odontologo(32412, "JUAN", "PEREZ");
        Odontologo odontologoRegistrado = odontologoService.registrar(odontologo);
        assertNotNull(odontologoRegistrado.toString());

    }

    @Test
    void deberiaRetornarUnaListaNoVaciaDeOdontologosEnH2() {
        odontologoService = new OdontologoService(new OdontologoDaoH2());
        assertFalse(odontologoService.listarTodos().isEmpty());
    }

    @Test
    void registrarOdontologoEnemoria() {
        odontologoService = new OdontologoService(new OdontologoMemoria());
        Odontologo odontologo = crearOdontologo();
        Odontologo odontologoRegistrado = odontologoService.registrar(odontologo);
        assertNotNull(odontologoRegistrado.toString());
    }

    @Test
    void deberiaRetornarUnaListaNoVaciaDeOdontologosEnMemoria() {
        odontologoService = new OdontologoService(new OdontologoMemoria());
        Odontologo odontologo = crearOdontologo();
        odontologoService.registrar(odontologo);
        assertFalse(odontologoService.listarTodos().isEmpty());
    }

    private Odontologo crearOdontologo() {
        return new Odontologo(32412, "JUAN", "PEREZ");
    }
}