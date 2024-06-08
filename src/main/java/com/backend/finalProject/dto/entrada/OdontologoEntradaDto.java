package com.backend.finalProject.dto.entrada;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class OdontologoEntradaDto {

    @NotNull(message = "Debe proveerse el numero de matricula")
    private int numeroMatricula;

    @NotBlank(message = "Debe proveerse el nombre")
    private String nombre;

    @NotBlank(message = "Debe proveerse el apellido")
    private String apellido;

    public OdontologoEntradaDto() {
    }

    public OdontologoEntradaDto(int numeroMatricula, String nombre, String apellido) {
        this.numeroMatricula = numeroMatricula;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public int getNumeroMatricula() {
        return numeroMatricula;
    }

    public void setNumeroMatricula(int numeroMatricula) {
        this.numeroMatricula = numeroMatricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
