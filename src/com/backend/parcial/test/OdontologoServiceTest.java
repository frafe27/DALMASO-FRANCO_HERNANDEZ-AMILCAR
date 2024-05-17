package com.backend.parcial.test;

import com.backend.parcial.entity.Odontologo;
import com.backend.parcial.repository.Impl.OdontologoDaoH2;
import com.backend.parcial.service.impl.OdontologoService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class OdontologoServiceTest {

    private OdontologoService odontologoService;
    /*@Test
    void deberiaRegistrarseUnOdontologoEnLaBaseDeDatos(){

        odontologoService = new OdontologoService(new OdontologoDaoH2());
        Odontologo odontologo = new Odontologo(32412, "JUAN", "PEREZ");
        Odontologo odontologoRegistrado = odontologoService.registrar(odontologo);
        assertNotNull(odontologoRegistrado.toString());

    }*/

    @Test
    void deberiaRetornarUnaListaNoVaciaDeOdontologosEnH2(){
        odontologoService = new OdontologoService(new OdontologoDaoH2());
        assertFalse(odontologoService.listarTodos().isEmpty());
    }

}