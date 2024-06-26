package com.backend.finalProject.service.impl;

import com.backend.finalProject.dto.entrada.OdontologoEntradaDto;
import com.backend.finalProject.dto.salida.OdontologoSalidaDto;
import com.backend.finalProject.entity.Odontologo;
import com.backend.finalProject.entity.Turno;
import com.backend.finalProject.exceptions.BadRequestException;
import com.backend.finalProject.exceptions.ResourceNotFoundException;
import com.backend.finalProject.repository.OdontologoRepository;
import com.backend.finalProject.repository.TurnoRepository;
import com.backend.finalProject.service.IOdontologoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OdontologoService implements IOdontologoService {
    //Se mapea de DTO a entidad y viceversa
    private final Logger LOGGER = LoggerFactory.getLogger(OdontologoService.class);
    private final OdontologoRepository odontologoRepository;

    private final TurnoRepository turnoRepository;
    private final ModelMapper modelMapper;



    public OdontologoService(OdontologoRepository odontologoRepository, TurnoRepository turnoRepository, ModelMapper modelMapper) {
        this.turnoRepository = turnoRepository;
        this.odontologoRepository = odontologoRepository;
        this.modelMapper = modelMapper;

    }

    @Override
    public OdontologoSalidaDto registrarOdontologo(OdontologoEntradaDto odontologoEntradaDto) {
        //mapeo de DTO a entidad
        LOGGER.info("OdontologoEntradaDTO: " + odontologoEntradaDto.toString());
        Odontologo odontologo = modelMapper.map(odontologoEntradaDto, Odontologo.class);
        LOGGER.info("OdontologoEntidad: " + odontologo);
        //mapeo de entidad a DTO
        OdontologoSalidaDto odontologoSalidaDto = modelMapper.map(odontologoRepository.save(odontologo), OdontologoSalidaDto.class);
        LOGGER.info("OdontologoSalidaDto: " + odontologoSalidaDto);
        return odontologoSalidaDto;
    }

    @Override
    public List<OdontologoSalidaDto> listarTodos() {
        //mapeo de lista de entidades a listas de datos
        List<OdontologoSalidaDto> odontologos = odontologoRepository.findAll()
                .stream()
                .map(odontologo -> modelMapper.map(odontologo, OdontologoSalidaDto.class))
                .toList();
        LOGGER.info("Listado de todos los odontologos: {}", odontologos);

        return odontologos;
    }


    @Override
    public OdontologoSalidaDto buscarOdontologoPorId(Long id) {
        //mapeo de entidad a DTO
        Odontologo odontologoBuscado = odontologoRepository.findById(id).orElse(null);
        OdontologoSalidaDto odontologoEncontrado = null;

        if (odontologoBuscado != null) {
            odontologoEncontrado = modelMapper.map(odontologoBuscado, OdontologoSalidaDto.class);
            LOGGER.info("Odontologo encontrado: {}", odontologoEncontrado);
        } else {
            LOGGER.error("No se ha podido encontrar el Odontologo con id {}", id);
        }

        return odontologoEncontrado;
    }


    @Override
    public void eliminarOdontologo(Long id) throws ResourceNotFoundException, BadRequestException {
        if (buscarOdontologoPorId(id) != null) {
            List<Turno> turnosAsociados = turnoRepository.findByOdontologoId(id);
            if (turnosAsociados.isEmpty()) {
                odontologoRepository.deleteById(id);
                LOGGER.info("Se ha eliminado el Odontologo con id {}", id);
            } else {
                throw new BadRequestException("No se puede eliminar el odontólogo con id " + id + " porque tiene turnos asociados.");
            }
        } else {
            throw new ResourceNotFoundException("No existe registro de odontologo con id " + id);
        }
    }

    @Override
    public OdontologoSalidaDto actualizarOdontologo(OdontologoEntradaDto odontologoEntradaDto, Long id) {

        Odontologo odontologoRecibido = modelMapper.map(odontologoEntradaDto, Odontologo.class);
        Odontologo odontologoAActualizar = odontologoRepository.findById(id).orElse(null);
        OdontologoSalidaDto odontologoSalidaDto = null;

        if (odontologoAActualizar != null) {

            odontologoRecibido.setId(odontologoAActualizar.getId());
            odontologoAActualizar = odontologoRecibido;

            odontologoRepository.save(odontologoAActualizar);
            odontologoSalidaDto = modelMapper.map(odontologoAActualizar, OdontologoSalidaDto.class);
            LOGGER.info("Odontologo actualizado: {}", odontologoSalidaDto);
        } else {
            LOGGER.error("No se ha podido actualizar el Odontologo con id {}", id);
        }

        return odontologoSalidaDto;
    }

}
