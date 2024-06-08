package com.backend.finalProject;

import com.backend.finalProject.entity.Odontologo;
import com.backend.finalProject.service.impl.OdontologoService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OdontologoServiceTest {

    private OdontologoService odontologoService;

    @Test
    void deberiaRegistrarseUnOdontologoEnLaBaseDeDatos() {

//        odontologoService = new OdontologoService(new OdontologoDaoH2());
//        OdontologoEntradaDto odontologoEntradaDto = new OdontologoEntradaDto(32412, "JUAN", "PEREZ");
//
//        Odontologo odontologoRegistrado = odontologoService.registrarOdontologo(odontologo);
//        assertNotNull(odontologoRegistrado.toString());

    }

    @Test
    void deberiaRetornarUnaListaNoVaciaDeOdontologosEnH2() {
//        odontologoService = new OdontologoService(new OdontologoDaoH2());
//        assertFalse(odontologoService.listarTodos().isEmpty());
    }
    private Odontologo crearOdontologo() {
        return new Odontologo(32412, "JUAN", "PEREZ");
    }
}