package cl.kemolinaj.ms.laboratorio.services;

import cl.kemolinaj.ms.laboratorio.dtos.DeleteRqDto;
import cl.kemolinaj.ms.laboratorio.dtos.LaboratorioRqDto;
import cl.kemolinaj.ms.laboratorio.dtos.LaboratorioRsDto;
import cl.kemolinaj.ms.laboratorio.exceptions.LaboratorioException;

import java.util.List;

public interface LaboratorioService {
    LaboratorioRsDto agregar(LaboratorioRqDto rqDto) throws LaboratorioException;

    LaboratorioRsDto actualizar(LaboratorioRqDto rqDto) throws LaboratorioException;

    void eliminar(DeleteRqDto rqDto) throws LaboratorioException;

    List<LaboratorioRsDto> consultarTodo() throws LaboratorioException;
}
