package com.backend.finalProject.service;

import com.backend.finalProject.entity.Odontologo;

import java.util.List;

public interface IOdontologoService {
    Odontologo registrar(Odontologo odontologo);

    List<Odontologo> listarTodos();
}
