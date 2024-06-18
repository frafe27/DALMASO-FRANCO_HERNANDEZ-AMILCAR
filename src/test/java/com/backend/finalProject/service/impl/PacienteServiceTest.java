package com.backend.finalProject.service.impl;

import com.backend.finalProject.dto.entrada.DomicilioEntradaDto;
import com.backend.finalProject.dto.entrada.PacienteEntradaDto;
import com.backend.finalProject.dto.salida.PacienteSalidaDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PacienteServiceTest {

    @Autowired
    private PacienteService pacienteService;

    @Test
    @Order(1)
    void deberiaRegistrarseUnPacienteDeNombreRoberto_yRetornarSuId() {

        PacienteEntradaDto pacienteEntradaDto = new PacienteEntradaDto("Roberto", "Gomez", 999999, LocalDate.of(2024, 7, 30), new DomicilioEntradaDto("Calle", 421, "Buenos Aires", "Argentina"));

        PacienteSalidaDto pacienteSalidaDto = pacienteService.registrarPaciente(pacienteEntradaDto);

        //assert
        assertNotNull(pacienteSalidaDto);
        assertNotNull(pacienteSalidaDto.getId());
        assertEquals("Roberto", pacienteSalidaDto.getNombre());
    }


    @Test
    @Order(2)
    void deberiaDevolverUnaListaNoVaciaDePacientes() {
        List<PacienteSalidaDto> listadoDePacientes = pacienteService.listarPacientes();
        assertFalse(listadoDePacientes.isEmpty());
    }

    @Test
    @Order(3)
    void deberiaEliminarseElPacienteConId1() {

        assertDoesNotThrow(() -> pacienteService.eliminarPaciente(1L));
    }

    @Test
    @Order(4)
    void deberiaDevolverUnaListaVaciaDePacientes() {
        List<PacienteSalidaDto> listadoDePacientes = pacienteService.listarPacientes();
        assertTrue(listadoDePacientes.isEmpty());
    }


}