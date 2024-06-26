package com.backend.finalProject.service.impl;

import com.backend.finalProject.dto.entrada.TurnoEntradaDto;
import com.backend.finalProject.dto.salida.TurnoSalidaDto;
import com.backend.finalProject.entity.Turno;
import com.backend.finalProject.exceptions.BadRequestException;
import com.backend.finalProject.repository.OdontologoRepository;
import com.backend.finalProject.repository.PacienteRepository;
import com.backend.finalProject.repository.TurnoRepository;
import com.backend.finalProject.service.ITurnoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoService implements ITurnoService {
    private final Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);
    private final TurnoRepository turnoRepository;
    private final PacienteRepository pacienteRepository;
    private final OdontologoRepository odontologoRepository;
    private final ModelMapper modelMapper;

    public TurnoService(TurnoRepository turnoRepository, PacienteRepository pacienteRepository, OdontologoRepository odontologoRepository, ModelMapper modelMapper) {
        this.turnoRepository = turnoRepository;
        this.pacienteRepository = pacienteRepository;
        this.odontologoRepository = odontologoRepository;
        this.modelMapper = modelMapper;
        configureMapping();
    }

    @Override
    public TurnoSalidaDto registrarTurno(TurnoEntradaDto turnoEntradaDto) throws BadRequestException {
        PacienteService pacienteService = new PacienteService(pacienteRepository,turnoRepository, modelMapper);
        OdontologoService odontologoService = new OdontologoService(odontologoRepository,turnoRepository, modelMapper);


        if (pacienteService.buscarPacientePorId(turnoEntradaDto.getPacienteSalidaDto().getId()) == null) {
            throw new BadRequestException("El paciente no existe");
        }
        if (odontologoService.buscarOdontologoPorId(turnoEntradaDto.getOdontologoSalidaDto().getId()) == null) {
            throw new BadRequestException("El odontologo no existe");
        }

        //mapeo de DTO a entidad
        LOGGER.info("TurnoEntradaDto: " + turnoEntradaDto);
        Turno turno = modelMapper.map(turnoEntradaDto, Turno.class);
        LOGGER.info("TurnoEntidad: " + turno);
        Turno turnoRegistrado = turnoRepository.save(turno);
        LOGGER.info("TurnoRegistrado: " + turnoRegistrado);
        //mapeo de entidad a DTO

        return modelMapper.map(turnoRegistrado, TurnoSalidaDto.class);
    }

    @Override
    public List<TurnoSalidaDto> listarTurnos() {
        List<Turno> turnos = turnoRepository.findAll();
        List<TurnoSalidaDto> turnosSalida = turnos.stream()
                .map(turno -> modelMapper.map(turno, TurnoSalidaDto.class))
                .toList();
        LOGGER.info("Listado de todos los turnos: {}", turnosSalida);
        return turnosSalida;
    }

    @Override
    public TurnoSalidaDto buscarTurnoPorId(Long id) {
        Turno turnoBuscado = turnoRepository.findById(id).orElse(null);
        TurnoSalidaDto turnoEncontrado = null;

        if (turnoBuscado != null) {
            turnoEncontrado = modelMapper.map(turnoBuscado, TurnoSalidaDto.class);
            LOGGER.info("Turno encontrado: {}", turnoEncontrado);
        } else {
            LOGGER.error("No se ha encontrado el turno con id {}", id);
        }

        return turnoEncontrado;
    }

    @Override
    public void eliminarTurno(Long id) {
        if (buscarTurnoPorId(id) != null) {
            turnoRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el turno con id {}", id);
        } else {
            LOGGER.error("No se ha podido eliminar el turno porque no se encuentra en nuestra base de datos");
        }
    }

    @Override
    public TurnoSalidaDto actualizarTurno(TurnoEntradaDto turnoEntradaDto, Long id) throws BadRequestException {
        PacienteService pacienteService = new PacienteService(pacienteRepository,turnoRepository, modelMapper);
        OdontologoService odontologoService = new OdontologoService(odontologoRepository,turnoRepository, modelMapper);

        if (pacienteService.buscarPacientePorId(turnoEntradaDto.getPacienteSalidaDto().getId()) == null) {
            throw new BadRequestException("El paciente no existe");
        }
        if (odontologoService.buscarOdontologoPorId(turnoEntradaDto.getOdontologoSalidaDto().getId()) == null) {
            throw new BadRequestException("El odontologo no existe");
        }
        Turno turnoRecibido = modelMapper.map(turnoEntradaDto, Turno.class);
        Turno turnoAActualizar = turnoRepository.findById(id).orElse(null);
        TurnoSalidaDto turnoSalidaDto = null;

        if (turnoAActualizar != null) {
            turnoRecibido.setId(turnoAActualizar.getId());
            turnoAActualizar = turnoRecibido;

            turnoRepository.save(turnoAActualizar);
            turnoSalidaDto = modelMapper.map(turnoAActualizar, TurnoSalidaDto.class);
            LOGGER.warn("Turno actualizado: {}", turnoSalidaDto);
        } else {
            LOGGER.error("No fue posible actualizar el turno porque no se encuentra en nuestra base de datos");
        }

        return turnoSalidaDto;
    }

    private void configureMapping() {
        modelMapper.typeMap(TurnoEntradaDto.class, Turno.class)
                .addMappings(mapper -> mapper.map(TurnoEntradaDto::getPacienteSalidaDto, Turno::setPaciente));
        modelMapper.typeMap(Turno.class, TurnoSalidaDto.class)
                .addMappings(mapper -> mapper.map(Turno::getPaciente, TurnoSalidaDto::setPacienteSalidaDto));

        modelMapper.typeMap(TurnoEntradaDto.class, Turno.class)
                .addMappings(mapper -> mapper.map(TurnoEntradaDto::getOdontologoSalidaDto, Turno::setOdontologo));
        modelMapper.typeMap(Turno.class, TurnoSalidaDto.class)
                .addMappings(mapper -> mapper.map(Turno::getOdontologo, TurnoSalidaDto::setOdontologoSalidaDto));
    }
}
