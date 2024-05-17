package com.backend.parcial.service;

import com.backend.parcial.entity.Odontologo;

import java.util.List;

public interface IOdontologoService {
    Odontologo registrar(Odontologo odontologo);

    List<Odontologo> listarTodos();
}
