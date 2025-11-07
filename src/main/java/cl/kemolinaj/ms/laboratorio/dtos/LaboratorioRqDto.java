package cl.kemolinaj.ms.laboratorio.dtos;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record LaboratorioRqDto(
        Long id,

        @NotNull(message = "Es obligatorio")
        @NotEmpty(message = "No puede ser vacio")
        String codigo,

        @NotNull(message = "Es obligatorio")
        @NotEmpty(message = "No puede ser vacio")
        String nombre,

        @NotNull(message = "Es obligatorio")
        @NotEmpty(message = "No puede ser vacio")
        String tipo
) {
}
