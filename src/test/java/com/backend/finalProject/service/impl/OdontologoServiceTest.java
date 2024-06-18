package com.backend.finalProject.service.impl;

import com.backend.finalProject.dto.entrada.OdontologoEntradaDto;
import com.backend.finalProject.dto.salida.OdontologoSalidaDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OdontologoServiceTest {

    @Autowired
    private OdontologoService odontologoService;


    @Test
    @Order(1)
    void deberiaRegistrarseUnOdontologoDeNombreRoberto_yRetornarSuId() {

        OdontologoEntradaDto odontologoEntradaDto = new OdontologoEntradaDto(1, "Roberto", "Hernandez");

        OdontologoSalidaDto odontologoSalidaDto = odontologoService.registrarOdontologo(odontologoEntradaDto);

        //assert
        assertNotNull(odontologoSalidaDto);
        assertNotNull(odontologoSalidaDto.getId());
        assertEquals("Roberto", odontologoSalidaDto.getNombre());
    }


    @Test
    @Order(2)
    void deberiaDevolverUnaListaNoVaciaDeOdontologos() {
        List<OdontologoSalidaDto> listadoDeOdontologos = odontologoService.listarTodos();
        assertFalse(listadoDeOdontologos.isEmpty());
    }

    @Test
    @Order(3)
    void deberiaEliminarseElOdontologoConId1() {

        assertDoesNotThrow(() -> odontologoService.eliminarOdontologo(1L));
    }

    @Test
    @Order(4)
    void deberiaDevolverUnaListaVaciaDeOdontologos() {
        List<OdontologoSalidaDto> listadoDeOdontologos = odontologoService.listarTodos();
        assertTrue(listadoDeOdontologos.isEmpty());
    }


}