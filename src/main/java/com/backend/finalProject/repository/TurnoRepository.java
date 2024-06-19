package com.backend.finalProject.repository;

import com.backend.finalProject.entity.Odontologo;
import com.backend.finalProject.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TurnoRepository extends JpaRepository<Turno, Long> {
    List<Turno> findByOdontologoId(Long odontologoId);

    List<Turno> findByPacienteId(Long pacienteId);
}
