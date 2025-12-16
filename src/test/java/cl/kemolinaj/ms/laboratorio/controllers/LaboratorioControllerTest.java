
package cl.kemolinaj.ms.laboratorio.controllers;

import cl.kemolinaj.ms.laboratorio.dtos.DeleteRqDto;
import cl.kemolinaj.ms.laboratorio.dtos.LaboratorioRqDto;
import cl.kemolinaj.ms.laboratorio.dtos.LaboratorioRsDto;
import cl.kemolinaj.ms.laboratorio.services.LaboratorioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para LaboratorioController")
class LaboratorioControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LaboratorioService laboratorioService;

    @InjectMocks
    private LaboratorioController laboratorioController;

    private ObjectMapper objectMapper;

    private LaboratorioRqDto laboratorioRqDto;
    private LaboratorioRqDto laboratorioConIdRqDto;

    private LaboratorioRsDto laboratorioRsDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(laboratorioController).build();
        objectMapper = new ObjectMapper();

        laboratorioRqDto = new LaboratorioRqDto(null, "Laboratorio A", "Ubicación 1", "tipo");
        laboratorioConIdRqDto = new LaboratorioRqDto(1L, "Laboratorio A", "Ubicación 1", "tipo");

        laboratorioRsDto = new LaboratorioRsDto(1L, "Laboratorio A", "Ubicación 1", "tipo");
    }

    // ==================== PRUEBAS POST (AGREGAR) ====================

    @Test
    @DisplayName("Debe agregar un laboratorio exitosamente")
    void testAgregarLaboratorioExitoso() throws Exception {

        when(laboratorioService.agregar(any(LaboratorioRqDto.class)))
                .thenReturn(laboratorioRsDto);

        // Act & Assert
        mockMvc.perform(post("/laboratorio")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(laboratorioRqDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Ubicación 1")));

        verify(laboratorioService, times(1)).agregar(any(LaboratorioRqDto.class));
    }

    @Test
    @DisplayName("Debe actualizar laboratorio exitosamente")
    void testActualizarLaboratorioExitoso() throws Exception {

        when(laboratorioService.actualizar(any(LaboratorioRqDto.class)))
                .thenReturn(laboratorioRsDto);

        // Act & Assert
        mockMvc.perform(put("/laboratorio")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(laboratorioConIdRqDto)))
                .andExpect(status().isOk());

        verify(laboratorioService, times(1)).actualizar(any(LaboratorioRqDto.class));
    }

    // ==================== PRUEBAS GET (CONSULTAR) ====================

    @Test
    @DisplayName("Debe consultar todos los laboratorios exitosamente")
    void testConsultarTodoExitoso() throws Exception {
        // Arrange
        List<LaboratorioRsDto> laboratorios = Arrays.asList(
                laboratorioRsDto,
                laboratorioRsDto
        );

        when(laboratorioService.consultarTodo())
                .thenReturn(laboratorios);

        // Act & Assert
        mockMvc.perform(get("/laboratorio")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)));

        verify(laboratorioService, times(1)).consultarTodo();
    }

    @Test
    @DisplayName("Debe consultar lista vacía cuando no hay laboratorios")
    void testConsultarTodoListaVacia() throws Exception {
        // Arrange
        when(laboratorioService.consultarTodo())
                .thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(get("/laboratorio")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(laboratorioService, times(1)).consultarTodo();
    }

    // ==================== PRUEBAS DELETE (BORRAR) ====================

    @Test
    @DisplayName("Debe eliminar un laboratorio exitosamente")
    void testEliminarLaboratorioExitoso() throws Exception {
        // Arrange
        DeleteRqDto rqDto = new DeleteRqDto(1L);

        doNothing().when(laboratorioService).eliminar(any(DeleteRqDto.class));

        // Act & Assert
        mockMvc.perform(delete("/laboratorio")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(rqDto)))
                .andExpect(status().isOk());

        verify(laboratorioService, times(1)).eliminar(any(DeleteRqDto.class));
    }

    @Test
    @DisplayName("Debe retornar error al eliminar laboratorio con ID inválido")
    void testEliminarLaboratorioConIdInvalido() throws Exception {

        // Act & Assert
        mockMvc.perform(delete("/laboratorio")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());

        verify(laboratorioService, never()).eliminar(any(DeleteRqDto.class));
    }

    // ==================== PRUEBAS DE VALIDACIÓN GENERAL ====================

    @Test
    @DisplayName("Debe validar que el content-type sea application/json")
    void testValidarContentTypeEnAgregar() throws Exception {

        // Act & Assert - Falla porque el controlador requiere application/json
        mockMvc.perform(post("/laboratorio")
                        .contentType(MediaType.APPLICATION_XML_VALUE)
                        .content(objectMapper.writeValueAsString(laboratorioConIdRqDto)))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    @DisplayName("Debe retornar 404 para endpoint no existente")
    void testEndpointNoExistente() throws Exception {
        mockMvc.perform(get("/laboratorio/inexistente")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }
}