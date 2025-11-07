package cl.kemolinaj.ms.laboratorio.dtos;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.LowerCaseStrategy.class)
public record ErrorDto (
        Integer code,
        String message,
        Object description
){}
