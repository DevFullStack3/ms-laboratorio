package cl.kemolinaj.ms.laboratorio.controllers;

import cl.kemolinaj.ms.laboratorio.dtos.DeleteRqDto;
import cl.kemolinaj.ms.laboratorio.dtos.LaboratorioRqDto;
import cl.kemolinaj.ms.laboratorio.dtos.LaboratorioRsDto;
import cl.kemolinaj.ms.laboratorio.exceptions.LaboratorioException;
import cl.kemolinaj.ms.laboratorio.services.LaboratorioService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/laboratorio")
@Slf4j
public class LaboratorioController {
    private final LaboratorioService laboratorioService;

    @Autowired
    public LaboratorioController(LaboratorioService laboratorioService) {
        this.laboratorioService = laboratorioService;
    }

    @PostMapping()
    public ResponseEntity<LaboratorioRsDto> agregar(@RequestBody @Valid LaboratorioRqDto rqDto) throws LaboratorioException {
        log.info("Agregando laboratorio: {}", rqDto);
        return ResponseEntity.ok(laboratorioService.agregar(rqDto));
    }

    @GetMapping()
    public ResponseEntity<List<LaboratorioRsDto>> consultarTodo() throws LaboratorioException {
        log.info("Consultando laboratorios");
        return ResponseEntity.ok(laboratorioService.consultarTodo());
    }

    @PutMapping()
    public ResponseEntity<LaboratorioRsDto> actualizar(@RequestBody @Valid LaboratorioRqDto rqDto) throws LaboratorioException {
        log.info("Actualizando laboratorio: {}", rqDto);
        return ResponseEntity.ok(laboratorioService.actualizar(rqDto));
    }

    @DeleteMapping()
    public void borrar(@RequestBody @Valid DeleteRqDto rqDto) throws LaboratorioException {
        log.info("Eliminando laboratorio con id: {}", rqDto.id());
        laboratorioService.eliminar(rqDto);
    }
}
