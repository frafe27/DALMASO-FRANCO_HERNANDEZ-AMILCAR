package com.backend.finalProject.service.impl;

import com.backend.finalProject.dto.entrada.TurnoEntradaDto;
import com.backend.finalProject.dto.salida.OdontologoSalidaDto;
import com.backend.finalProject.dto.salida.PacienteSalidaDto;
import com.backend.finalProject.dto.salida.TurnoSalidaDto;
import com.backend.finalProject.entity.Domicilio;
import com.backend.finalProject.entity.Odontologo;
import com.backend.finalProject.entity.Paciente;
import com.backend.finalProject.entity.Turno;
import com.backend.finalProject.exceptions.BadRequestException;
import com.backend.finalProject.repository.OdontologoRepository;
import com.backend.finalProject.repository.PacienteRepository;
import com.backend.finalProject.repository.TurnoRepository;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TurnoServiceTest {

    @Autowired
    private TurnoService turnoService;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private OdontologoRepository odontologoRepository;

    @Autowired
    private TurnoRepository turnoRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Test

    @Order(1)
    void deberiaRegistrarUnTurno_yRetornarSuIdCorrespondiente() throws BadRequestException {
        // Crear y guardar un paciente y un odontólogo para asociar al turno
        Domicilio domicilio = new Domicilio(1L, "Calle", 123, "Ciudad", "Provincia");
        Paciente paciente = new Paciente(1L, "PacienteNombre", "PacienteApellido", 123456, LocalDate.now(), domicilio);
        pacienteRepository.save(paciente);
        Odontologo odontologo = new Odontologo(1L, 1234, "OdontologoNombre", "OdontologoApellido");
        odontologoRepository.save(odontologo);

        // Crear el DTO de entrada del turno
        TurnoEntradaDto turnoEntradaDto = new TurnoEntradaDto();
        turnoEntradaDto.setPacienteSalidaDto(modelMapper.map(paciente, PacienteSalidaDto.class));
        turnoEntradaDto.setOdontologoSalidaDto(modelMapper.map(odontologo, OdontologoSalidaDto.class));
        turnoEntradaDto.setFechaYHora(LocalDateTime.now());

        // Registrar el turno
        TurnoSalidaDto turnoSalidaDto = turnoService.registrarTurno(turnoEntradaDto);

        // Asserts
        assertNotNull(turnoSalidaDto);
        assertNotNull(turnoSalidaDto.getId());
        assertEquals(paciente.getId(), turnoSalidaDto.getPacienteSalidaDto().getId());
        assertEquals(odontologo.getId(), turnoSalidaDto.getOdontologoSalidaDto().getId());
    }

    @Test
    @Order(2)
    void deberiaDevolverUnaListaNoVaciaDeTurnos() {
        List<TurnoSalidaDto> listadoDeTurnos = turnoService.listarTurnos();
        assertFalse(listadoDeTurnos.isEmpty());
    }

    @Test
    @Order(3)
    void deberiaEliminarseElTurnoConId1() {
        assertDoesNotThrow(() -> turnoService.eliminarTurno(1L));
    }

    @Test
    @Order(4)
    void deberiaDevolverUnaListaVaciaDeTurnos() {
        List<TurnoSalidaDto> listadoDeTurnos = turnoService.listarTurnos();
        assertTrue(listadoDeTurnos.isEmpty());
    }

    @Test
    @Order(5)
    void deberiaActualizarUnTurno() throws BadRequestException {
        // Crear y guardar domicilio de prueba
        Domicilio domicilio = new Domicilio();
        domicilio.setCalle("Calle Falsa");
        domicilio.setNumero(123);
        domicilio.setLocalidad("Springfield");
        domicilio.setProvincia("Provincia");

        // Crear y guardar paciente de prueba
        Paciente paciente = new Paciente();
        paciente.setNombre("Juan");
        paciente.setApellido("Perez");
        paciente.setDni(12345678);
        paciente.setFechaIngreso(LocalDate.now());
        paciente.setDomicilio(domicilio);
        paciente = pacienteRepository.save(paciente);

        // Crear y guardar odontólogo de prueba
        Odontologo odontologo = new Odontologo();
        odontologo.setNombre("Ana");
        odontologo.setApellido("Lopez");
        odontologo.setNumeroMatricula(1234);
        odontologo = odontologoRepository.save(odontologo);

        // Crear y guardar turno de prueba
        Turno turno = new Turno();
        turno.setPaciente(paciente);
        turno.setOdontologo(odontologo);
        turno.setFechaYHora(LocalDateTime.now());
        turno = turnoRepository.save(turno);

        // Configurar TurnoEntradaDto de prueba
        TurnoEntradaDto turnoEntradaDto = new TurnoEntradaDto();
        turnoEntradaDto.setPacienteSalidaDto(modelMapper.map(paciente, PacienteSalidaDto.class));
        turnoEntradaDto.setOdontologoSalidaDto(modelMapper.map(odontologo, OdontologoSalidaDto.class));
        turnoEntradaDto.setFechaYHora(LocalDateTime.now().plusDays(1));

        // Llamar al método a probar
        TurnoSalidaDto turnoSalidaDto = turnoService.actualizarTurno(turnoEntradaDto, turno.getId());

        // Verificar resultados
        assertNotNull(turnoSalidaDto);
        assertEquals(turno.getId(), turnoSalidaDto.getId());
        assertEquals(paciente.getId(), turnoSalidaDto.getPacienteSalidaDto().getId());
        assertEquals(odontologo.getId(), turnoSalidaDto.getOdontologoSalidaDto().getId());
    }
}