package cl.kemolinaj.ms.laboratorio.dtos;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record LaboratorioRsDto (
        Long id,

        String codigo,

        String nombre,

        String tipo
) {}
