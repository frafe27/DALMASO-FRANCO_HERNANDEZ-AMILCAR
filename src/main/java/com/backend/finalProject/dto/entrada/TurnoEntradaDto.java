package com.backend.finalProject.dto.entrada;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class TurnoEntradaDto {

    @NotBlank(message = "Debe especificar un paciente")
    @Valid
    private PacienteEntradaDto pacienteEntradaDto;

    @NotBlank(message = "Debe especificar un odontologo")
    @Valid
    private OdontologoEntradaDto odontologoEntradaDto;

    @FutureOrPresent(message = "La fecha no puede ser anterior al día de hoy")
    @NotNull(message = "Debe especificarse la fecha y hora")
    // investigar si esto es correcto? @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime fechaYHora;

    public TurnoEntradaDto() {
    }

    public TurnoEntradaDto(PacienteEntradaDto pacienteEntradaDto, OdontologoEntradaDto odontologoEntradaDto, LocalDateTime fechaYHora) {
        this.pacienteEntradaDto = pacienteEntradaDto;
        this.odontologoEntradaDto = odontologoEntradaDto;
        this.fechaYHora = fechaYHora;
    }

    public PacienteEntradaDto getPacienteEntradaDto() {
        return pacienteEntradaDto;
    }

    public void setPacienteEntradaDto(PacienteEntradaDto pacienteEntradaDto) {
        this.pacienteEntradaDto = pacienteEntradaDto;
    }

    public OdontologoEntradaDto getOdontologoEntradaDto() {
        return odontologoEntradaDto;
    }

    public void setOdontologoEntradaDto(OdontologoEntradaDto odontologoEntradaDto) {
        this.odontologoEntradaDto = odontologoEntradaDto;
    }

    public LocalDateTime getFechaYHora() {
        return fechaYHora;
    }

    public void setFechaYHora(LocalDateTime fechaYHora) {
        this.fechaYHora = fechaYHora;
    }
}
