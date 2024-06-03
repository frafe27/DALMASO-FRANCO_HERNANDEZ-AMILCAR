package com.backend.finalProject.service.impl;
import com.backend.finalProject.dto.entrada.TurnoEntradaDto;
import com.backend.finalProject.dto.salida.TurnoSalidaDto;
import com.backend.finalProject.entity.Turno;
import com.backend.finalProject.repository.IDao;
import com.backend.finalProject.service.ITurnoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoService implements ITurnoService {
    //Se mapea de DTO a entidad y viceversa
    private final Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);
    private IDao<Turno> turnoIDao;
    private final ModelMapper modelMapper;

    public TurnoService(IDao<Turno> turnoIDao, ModelMapper modelMapper) {
        this.turnoIDao = turnoIDao;
        this.modelMapper = modelMapper;
        configureMapping();
    }

    @Override
    public TurnoSalidaDto registrarTurno(TurnoEntradaDto turnoEntradaDto) {
        //mapeo de DTO a entidad
        LOGGER.info("TunoEntradaDto: " + turnoEntradaDto);
        Turno turno = modelMapper.map(turnoEntradaDto, Turno.class);
        LOGGER.info("TurnoEntidad: " + turno);
        //mapeo de entidad a DTO
        TurnoSalidaDto turnoSalidaDto = modelMapper.map(turnoIDao.registrar(turno), TurnoSalidaDto.class);

        return turnoSalidaDto;
    }

    @Override
    public List<TurnoSalidaDto> listarTurnos() {
        //mapeo de lista de entidades a listas de datos
        List<TurnoSalidaDto> turnos = turnoIDao.listarTodos()
                .stream()
                .map(turno -> modelMapper.map(turno, TurnoSalidaDto.class))
                .toList();
        LOGGER.info("Listado de todos los turnos: {}", turnos);

        return turnos;
    }

    private void configureMapping() {
        modelMapper.typeMap(TurnoEntradaDto.class, Turno.class)
                .addMappings(mapper -> mapper.map(TurnoEntradaDto::getPacienteEntradaDto, Turno::setPaciente));
        modelMapper.typeMap(Turno.class, TurnoSalidaDto.class)
                .addMappings(mapper -> mapper.map(Turno::getPaciente, TurnoSalidaDto::setPacienteSalidaDto));

        modelMapper.typeMap(TurnoEntradaDto.class, Turno.class)
                .addMappings(mapper -> mapper.map(TurnoEntradaDto::getOdontologoEntradaDto, Turno::setOdontologo));
        modelMapper.typeMap(Turno.class, TurnoSalidaDto.class)
                .addMappings(mapper -> mapper.map(Turno::getOdontologo, TurnoSalidaDto::setOdontologoSalidaDto));
    }
}
