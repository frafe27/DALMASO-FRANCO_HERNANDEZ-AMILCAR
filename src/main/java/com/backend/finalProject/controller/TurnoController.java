package com.backend.finalProject.controller;

import com.backend.finalProject.dto.entrada.TurnoEntradaDto;
import com.backend.finalProject.dto.salida.TurnoSalidaDto;
import com.backend.finalProject.service.ITurnoService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    private ITurnoService turnoService;

    public TurnoController(ITurnoService turnoService) {
        this.turnoService = turnoService;
    }

    //POST
    @PostMapping("/registrar")
    public TurnoSalidaDto registrarTurno(@RequestBody @Valid TurnoEntradaDto turnoEntradaDto){
        return turnoService.registrarTurno(turnoEntradaDto);
    }

    //GET
    @GetMapping("/listar")
    public List<TurnoSalidaDto> listarTurnos(){
        return turnoService.listarTurnos();
    }
}
