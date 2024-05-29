package com.backend.finalProject.repository.impl;

import com.backend.finalProject.dbconnection.H2Connection;
import com.backend.finalProject.entity.Odontologo;
import com.backend.finalProject.entity.Paciente;
import com.backend.finalProject.entity.Turno;
import com.backend.finalProject.repository.IDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TurnoDaoH2 implements IDao<Turno> {
    private final Logger LOGGER = LoggerFactory.getLogger(TurnoDaoH2.class);

    private PacienteDaoH2 pacienteDaoH2;
    private OdontologoDaoH2 odontologoDaoH2;

    @Override
    public Turno registrar(Turno turno) {
        Connection connection = null;
        Turno turnoregistrado = null;

        pacienteDaoH2 = new PacienteDaoH2();
        Paciente pacienteRegistrado = pacienteDaoH2.registrar(turno.getPaciente());

        odontologoDaoH2 = new OdontologoDaoH2();
        Odontologo odontologRegistrado = odontologoDaoH2.registrar(turno.getOdontologo());

        try {
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO TURNOS (PACIENTE_ID, ODONTOLOGO_ID, FECHAYHORA) VALUES(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setLong(1, pacienteRegistrado.getId());
            preparedStatement.setLong(2, odontologRegistrado.getID());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(turno.getFechaYHora()));
            preparedStatement.execute();

            turnoregistrado = new Turno(pacienteRegistrado, odontologRegistrado, turno.getFechaYHora());

            ResultSet resulset = preparedStatement.getGeneratedKeys();
            while(resulset.next()) {
                turnoregistrado.setId(resulset.getLong("id"));
            }

            connection.commit();

            LOGGER.info("Se ha registrado el turno: " + turnoregistrado);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                    LOGGER.info("Tuvimos un problema");
                    LOGGER.error(e.getMessage());
                    e.printStackTrace();
                } catch (SQLException exception) {
                    LOGGER.error(exception.getMessage());
                    exception.printStackTrace();
                }
            }
        } finally {
            try {
                connection.close();
            } catch (Exception ex) {
                LOGGER.error("No se pudo cerrar la conexion: " + ex.getMessage());
            }
        }

        return turnoregistrado;
    }

    @Override
    public Turno buscarPorId(Long id) {
        Turno turnoBuscado = null;
        Connection connection = null;

        try{
            connection = H2Connection.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM TURNOS WHERE ID = ?");
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){

                pacienteDaoH2 = new PacienteDaoH2();
                Paciente pacienteId = pacienteDaoH2.buscarPorId(resultSet.getLong(1));

                odontologoDaoH2 = new OdontologoDaoH2();
                Odontologo odontologoId = odontologoDaoH2.buscarPorId(resultSet.getLong(2));

                turnoBuscado = new Turno(pacienteId, odontologoId, resultSet.getTimestamp(3).toLocalDateTime());
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception ex) {
                LOGGER.error("Ha ocurrido un error al intentar cerrar la bdd. " + ex.getMessage());
                ex.printStackTrace();
            }
        }

        if(turnoBuscado == null) LOGGER.error("No se ha encontrado el turno con id: " + id);
        else LOGGER.info("Se ha encontrado el turno: " + turnoBuscado);

        return turnoBuscado;
    }

    @Override
    public List<Turno> listarTodos() {
        Connection connection = null;
        List<Turno> turnos = new ArrayList<>();

        try{
            connection = H2Connection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM TURNOS");
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()){
                pacienteDaoH2 = new PacienteDaoH2();
                Paciente pacienteListado = pacienteDaoH2.buscarPorId(resultSet.getLong(1));

                odontologoDaoH2 = new OdontologoDaoH2();
                Odontologo odontologoListado = odontologoDaoH2.buscarPorId(resultSet.getLong(2));

                Turno turno = new Turno(pacienteListado, odontologoListado, resultSet.getTimestamp(3).toLocalDateTime());

                turnos.add(turno);
            }

        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();

        } finally {
            try {
                connection.close();
            } catch (Exception ex) {
                LOGGER.error("Ha ocurrido un error al intentar cerrar la bdd. " + ex.getMessage());
                ex.printStackTrace();
            }
        }

        LOGGER.info("Listado de turnos obtenido: " + turnos);

        return turnos;
    }
}
