package com.backend.finalProject.entity;


import javax.persistence.Entity;
import javax.persistence.*;


@Entity
@Table(name = "ODONTOLOGOS")
public class Odontologo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20)
    private int numeroMatricula;
    @Column(length = 50)
    private String nombre;
    @Column(length = 50)
    private String apellido;

    public Odontologo(Long id, int numeroMatricula, String nombre, String apellido) {
        this.id = id;
        this.numeroMatricula = numeroMatricula;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Odontologo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long Id) {
        this.id = id;
    }

    public int getNumeroMatricula() {
        return numeroMatricula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    @Override
    public String toString() {
        return "Odontologo{" +
                "id=" + id +
                ", numeroMatricula=" + numeroMatricula +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                '}';
    }
}