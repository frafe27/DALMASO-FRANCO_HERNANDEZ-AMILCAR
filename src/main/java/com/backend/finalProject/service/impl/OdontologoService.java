package com.backend.finalProject.service.impl;
import com.backend.finalProject.dto.entrada.OdontologoEntradaDto;
import com.backend.finalProject.dto.salida.OdontologoSalidaDto;
import com.backend.finalProject.entity.Odontologo;
import com.backend.finalProject.repository.IDao;
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
    private final IDao<Odontologo> odontologoIDao;
    private final ModelMapper modelMapper;

    public OdontologoService(IDao<Odontologo> odontologoIDao, ModelMapper modelMapper) {
        this.odontologoIDao = odontologoIDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public OdontologoSalidaDto registrarOdontologo(OdontologoEntradaDto odontologoEntradaDto) {
        //mapeo de DTO a entidad
        LOGGER.info("OdontologoEntradaDTO: " + odontologoEntradaDto);
        Odontologo odontologo = modelMapper.map(odontologoEntradaDto, Odontologo.class);
        LOGGER.info("OdontologoEntidad: " + odontologo);
        //mapeo de entidad a DTO
        OdontologoSalidaDto odontologoSalidaDto = modelMapper.map(odontologoIDao.registrar(odontologo), OdontologoSalidaDto.class);

        return odontologoSalidaDto;
    }

    @Override
    public List<OdontologoSalidaDto> listarTodos() {
        //mapeo de lista de entidades a listas de datos
        List<OdontologoSalidaDto> odontologos = odontologoIDao.listarTodos()
                .stream()
                .map(odontologo -> modelMapper.map(odontologo, OdontologoSalidaDto.class))
                .toList();
        LOGGER.info("Listado de todos los odontologos: {}", odontologos);

        return odontologos;
    }


}
