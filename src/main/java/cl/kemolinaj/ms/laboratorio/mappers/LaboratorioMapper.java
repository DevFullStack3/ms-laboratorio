package cl.kemolinaj.ms.laboratorio.mappers;

import cl.kemolinaj.ms.laboratorio.dtos.LaboratorioRqDto;
import cl.kemolinaj.ms.laboratorio.dtos.LaboratorioRsDto;
import cl.kemolinaj.ms.laboratorio.entities.LaboratorioEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LaboratorioMapper {
    LaboratorioEntity toEntity(LaboratorioRqDto rqDto);
    LaboratorioRsDto toRsDto(LaboratorioEntity entity);
    List<LaboratorioRsDto> toRsDtoList(List<LaboratorioEntity> entities);
}
