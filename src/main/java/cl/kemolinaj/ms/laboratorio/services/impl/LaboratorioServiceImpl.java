package cl.kemolinaj.ms.laboratorio.services.impl;

import cl.kemolinaj.ms.laboratorio.dtos.DeleteRqDto;
import cl.kemolinaj.ms.laboratorio.dtos.LaboratorioRqDto;
import cl.kemolinaj.ms.laboratorio.dtos.LaboratorioRsDto;
import cl.kemolinaj.ms.laboratorio.entities.LaboratorioEntity;
import cl.kemolinaj.ms.laboratorio.exceptions.BadRequestException;
import cl.kemolinaj.ms.laboratorio.exceptions.LaboratorioException;
import cl.kemolinaj.ms.laboratorio.mappers.LaboratorioMapper;
import cl.kemolinaj.ms.laboratorio.repositories.LaboratorioRepository;
import cl.kemolinaj.ms.laboratorio.services.LaboratorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("laboratorioService")
public class LaboratorioServiceImpl implements LaboratorioService {
    private final LaboratorioRepository laboratorioRepository;
    private final LaboratorioMapper laboratorioMapper;

    @Autowired
    public LaboratorioServiceImpl(LaboratorioRepository laboratorioRepository, LaboratorioMapper laboratorioMapper) {
        this.laboratorioRepository = laboratorioRepository;
        this.laboratorioMapper = laboratorioMapper;
    }

    @Override
    public LaboratorioRsDto agregar(LaboratorioRqDto rqDto) throws LaboratorioException {
        try {
            if (rqDto.id() != null) throw new BadRequestException("No se puede agregar un laboratorio con id");
            return laboratorioMapper.toRsDto(laboratorioRepository.save(laboratorioMapper.toEntity(rqDto)));
        } catch (Exception e){
            throw new LaboratorioException("Error al agregar laboratorio");
        }
    }

    @Override
    public LaboratorioRsDto actualizar(LaboratorioRqDto rqDto) throws LaboratorioException {
        try {
            if (rqDto.id() == null) throw new BadRequestException("No se puede actualizar un laboratorio sin id");
            return laboratorioMapper.toRsDto(laboratorioRepository.save(laboratorioMapper.toEntity(rqDto)));
        } catch (Exception e){
            throw new LaboratorioException("Error al actualizar laboratorio");
        }
    }

    @Override
    public List<LaboratorioRsDto> consultarTodo() throws LaboratorioException {
        try {
            return laboratorioMapper.toRsDtoList((List<LaboratorioEntity>) laboratorioRepository.findAll());
        } catch (Exception e){
            throw new LaboratorioException("Error al consultar laboratorios");
        }
    }

    @Override
    public void eliminar(DeleteRqDto rqDto) throws LaboratorioException {
        try {
            laboratorioRepository.deleteById(rqDto.id());
        } catch (Exception e){
            throw new LaboratorioException("Error al eliminar laboratorio");
        }
    }
}
