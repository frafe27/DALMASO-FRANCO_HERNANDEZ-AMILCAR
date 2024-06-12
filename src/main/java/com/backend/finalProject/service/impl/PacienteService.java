package com.backend.finalProject.service.impl;

import com.backend.finalProject.dto.entrada.PacienteEntradaDto;
import com.backend.finalProject.dto.salida.PacienteSalidaDto;
import com.backend.finalProject.entity.Paciente;
import com.backend.finalProject.exceptions.ResourceNotFoundException;
import com.backend.finalProject.repository.PacienteRepository;
import com.backend.finalProject.service.IPacienteService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class PacienteService implements IPacienteService {
    //se mapea de DTO a entidad y viceversa
    private final Logger LOGGER = LoggerFactory.getLogger(PacienteService.class);
    private final PacienteRepository pacienteRepository;
    private final ModelMapper modelMapper;

    public PacienteService(PacienteRepository pacienteRepository, ModelMapper modelMapper) {
        this.pacienteRepository = pacienteRepository;
        this.modelMapper = modelMapper;
        configureMapping();
    }

    @Override
    public PacienteSalidaDto registrarPaciente(PacienteEntradaDto pacienteEntradaDto) {
        //logica de negocio
        //mapeo de dto a entidad
        LOGGER.info("PacienteEntradaDto: " + pacienteEntradaDto);
        Paciente paciente = modelMapper.map(pacienteEntradaDto, Paciente.class);
        LOGGER.info("PacienteEntidad: " + paciente);
        //Paciente pacienteRegistrado = pacienteIDao.registrar(paciente);
        //mapeo de entidad a dto
        PacienteSalidaDto pacienteSalidaDto = modelMapper.map(pacienteRepository.save(paciente), PacienteSalidaDto.class);
        LOGGER.info("PacienteSalidaDto: " + pacienteSalidaDto);
        return pacienteSalidaDto;
    }

    @Override
    public List<PacienteSalidaDto> listarPacientes() {
        //mapeo de lista de entidades a lista de dtos
        List<PacienteSalidaDto> pacientes = pacienteRepository.findAll()
                .stream()
                .map(paciente -> modelMapper.map(paciente, PacienteSalidaDto.class))
                .toList();
        LOGGER.info("Listado de todos los pacientes: {}", pacientes);

        return pacientes;
    }

    @Override
    public PacienteSalidaDto buscarPacientePorId(Long id) {

        Paciente pacienteBuscado = pacienteRepository.findById(id).orElse(null);
        PacienteSalidaDto pacienteEncontrado = null;

        if (pacienteBuscado != null){
            pacienteEncontrado = modelMapper.map(pacienteBuscado, PacienteSalidaDto.class);
            LOGGER.info("Paciente encontrado: {}", pacienteEncontrado);
        } else LOGGER.error("No se ha encontrado el paciente con id {}", id);

        return pacienteEncontrado;
    }

    @Override
    public void eliminarPaciente(Long id) throws ResourceNotFoundException {
        if(buscarPacientePorId(id) != null){
            pacienteRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el paciente con id {}", id);
        } else {
            throw new ResourceNotFoundException("No existe registro de paciente con id " + id);
        }
    }

    @Override
    public PacienteSalidaDto actualizarPaciente(PacienteEntradaDto pacienteEntradaDto, Long id) {

        Paciente pacienteRecibido = modelMapper.map(pacienteEntradaDto, Paciente.class);
        Paciente pacienteAActualizar = pacienteRepository.findById(id).orElse(null);
        PacienteSalidaDto pacienteSalidaDto = null;

        if(pacienteAActualizar != null){

            pacienteRecibido.setId(pacienteAActualizar.getId());
            pacienteRecibido.getDomicilio().setId(pacienteAActualizar.getDomicilio().getId());
            pacienteAActualizar = pacienteRecibido;

            pacienteRepository.save(pacienteAActualizar);
            pacienteSalidaDto = modelMapper.map(pacienteAActualizar, PacienteSalidaDto.class);
            LOGGER.warn("Paciente actualizado: {}", pacienteSalidaDto);

        } else {
            LOGGER.error("No fue posible actualizar el paciente porque no se encuentra en nuestra base de datos");
        }

        return pacienteSalidaDto;
    }


    private void configureMapping(){
        modelMapper.typeMap(PacienteEntradaDto.class, Paciente.class)
                .addMappings(mapper -> mapper.map(PacienteEntradaDto::getDomicilioEntradaDto, Paciente::setDomicilio));
        modelMapper.typeMap(Paciente.class, PacienteSalidaDto.class)
                .addMappings(mapper -> mapper.map(Paciente::getDomicilio, PacienteSalidaDto::setDomicilioSalidaDto));
    }
}

