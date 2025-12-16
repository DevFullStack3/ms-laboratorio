package cl.kemolinaj.ms.laboratorio.services;

import cl.kemolinaj.ms.laboratorio.dtos.DeleteRqDto;
import cl.kemolinaj.ms.laboratorio.dtos.LaboratorioRqDto;
import cl.kemolinaj.ms.laboratorio.dtos.LaboratorioRsDto;
import cl.kemolinaj.ms.laboratorio.entities.LaboratorioEntity;
import cl.kemolinaj.ms.laboratorio.exceptions.LaboratorioException;
import cl.kemolinaj.ms.laboratorio.mappers.LaboratorioMapper;
import cl.kemolinaj.ms.laboratorio.repositories.LaboratorioRepository;
import cl.kemolinaj.ms.laboratorio.services.impl.LaboratorioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para LaboratorioServiceImpl")
class LaboratorioServiceImplTest {

    @Mock
    private LaboratorioRepository laboratorioRepository;

    @Mock
    private LaboratorioMapper laboratorioMapper;

    @InjectMocks
    private LaboratorioServiceImpl laboratorioService;

    private LaboratorioRqDto laboratorioRqDto;
    private LaboratorioRqDto laboratorioConIdRqDto;

    private LaboratorioRsDto laboratorioRsDto;
    private LaboratorioEntity laboratorioEntity;
    private DeleteRqDto deleteRqDto;

    @BeforeEach
    void setUp() {
        // Inicializar datos de prueba
        laboratorioRqDto = new LaboratorioRqDto(null, "Laboratorio A", "Ubicación 1", "");
        laboratorioConIdRqDto = new LaboratorioRqDto(1L, "Laboratorio A", "Ubicación 1", "");

        laboratorioRsDto = new LaboratorioRsDto(1L, "Laboratorio A", "Ubicación 1", "");
        laboratorioEntity = new LaboratorioEntity(1L, "Laboratorio A", "Ubicación 1", "");
        deleteRqDto = new DeleteRqDto(1L);
    }

    // ============ PRUEBAS PARA AGREGAR ============

    @Test
    @DisplayName("agregar - Debe agregar laboratorio exitosamente sin id")
    void testAgregarExitoso() throws LaboratorioException {
        // Arrange
        when(laboratorioMapper.toEntity(laboratorioRqDto)).thenReturn(laboratorioEntity);
        when(laboratorioRepository.save(laboratorioEntity)).thenReturn(laboratorioEntity);
        when(laboratorioMapper.toRsDto(laboratorioEntity)).thenReturn(laboratorioRsDto);

        // Act
        LaboratorioRsDto resultado = laboratorioService.agregar(laboratorioRqDto);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.id());
        assertEquals("Ubicación 1", resultado.nombre());
    }

    @Test
    @DisplayName("agregar - Debe lanzar excepción si id no es null")
    void testAgregarConIdDebeLanzarExcepcion() {

        // Act & Assert
        assertThrows(LaboratorioException.class, () -> laboratorioService.agregar(laboratorioConIdRqDto));
        verify(laboratorioRepository, never()).save(any());
    }

    @Test
    @DisplayName("agregar - Debe lanzar LaboratorioException si ocurre error en el repositorio")
    void testAgregarConErrorEnRepositorio() {
        // Arrange
        when(laboratorioMapper.toEntity(laboratorioRqDto)).thenReturn(laboratorioEntity);
        when(laboratorioRepository.save(laboratorioEntity)).thenThrow(new RuntimeException("Error BD"));

        // Act & Assert
        LaboratorioException exception = assertThrows(LaboratorioException.class,
                () -> laboratorioService.agregar(laboratorioRqDto));
        assertEquals("Error al agregar laboratorio", exception.getMessage());
    }

    // ============ PRUEBAS PARA ACTUALIZAR ============

    @Test
    @DisplayName("actualizar - Debe actualizar laboratorio exitosamente con id válido")
    void testActualizarExitoso() throws LaboratorioException {

        when(laboratorioMapper.toEntity(laboratorioConIdRqDto)).thenReturn(laboratorioEntity);
        when(laboratorioRepository.save(laboratorioEntity)).thenReturn(laboratorioEntity);
        when(laboratorioMapper.toRsDto(laboratorioEntity)).thenReturn(laboratorioRsDto);

        // Act
        LaboratorioRsDto resultado = laboratorioService.actualizar(laboratorioConIdRqDto);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.id());
        assertEquals("Ubicación 1", resultado.nombre());
    }

    @Test
    @DisplayName("actualizar - Debe lanzar excepción si id es null")
    void testActualizarSinIdDebeLanzarExcepcion() {

        // Act & Assert
        assertThrows(LaboratorioException.class, () -> laboratorioService.actualizar(laboratorioRqDto));
        verify(laboratorioRepository, never()).save(any());
    }

    @Test
    @DisplayName("actualizar - Debe lanzar LaboratorioException si ocurre error en el repositorio")
    void testActualizarConErrorEnRepositorio() {

        when(laboratorioMapper.toEntity(laboratorioConIdRqDto)).thenReturn(laboratorioEntity);
        when(laboratorioRepository.save(laboratorioEntity)).thenThrow(new RuntimeException("Error BD"));

        // Act & Assert
        LaboratorioException exception = assertThrows(LaboratorioException.class,
                () -> laboratorioService.actualizar(laboratorioConIdRqDto));
        assertEquals("Error al actualizar laboratorio", exception.getMessage());

    }

    // ============ PRUEBAS PARA CONSULTAR TODO ============

    @Test
    @DisplayName("consultarTodo - Debe retornar lista de laboratorios exitosamente")
    void testConsultarTodoExitoso() throws LaboratorioException {

        List<LaboratorioEntity> entities = Arrays.asList(laboratorioEntity, laboratorioEntity);
        List<LaboratorioRsDto> rsDtos = Arrays.asList(laboratorioRsDto, laboratorioRsDto);

        when(laboratorioRepository.findAll()).thenReturn(entities);
        when(laboratorioMapper.toRsDtoList(entities)).thenReturn(rsDtos);

        // Act
        List<LaboratorioRsDto> resultado = laboratorioService.consultarTodo();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Ubicación 1", resultado.get(0).nombre());
    }

    @Test
    @DisplayName("consultarTodo - Debe retornar lista vacía cuando no hay laboratorios")
    void testConsultarTodoListaVacia() throws LaboratorioException {
        // Arrange
        List<LaboratorioEntity> entities = List.of();
        List<LaboratorioRsDto> rsDtos = List.of();

        when(laboratorioRepository.findAll()).thenReturn(entities);
        when(laboratorioMapper.toRsDtoList(entities)).thenReturn(rsDtos);

        // Act
        List<LaboratorioRsDto> resultado = laboratorioService.consultarTodo();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(laboratorioRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("consultarTodo - Debe lanzar LaboratorioException si ocurre error en el repositorio")
    void testConsultarTodoConError() {
        // Arrange
        when(laboratorioRepository.findAll()).thenThrow(new RuntimeException("Error BD"));

        // Act & Assert
        LaboratorioException exception = assertThrows(LaboratorioException.class,
                () -> laboratorioService.consultarTodo());
        assertEquals("Error al consultar laboratorios", exception.getMessage());
    }

    // ============ PRUEBAS PARA ELIMINAR ============

    @Test
    @DisplayName("eliminar - Debe eliminar laboratorio exitosamente")
    void testEliminarExitoso() throws LaboratorioException {
        // Arrange
        doNothing().when(laboratorioRepository).deleteById(1L);

        // Act
        laboratorioService.eliminar(deleteRqDto);

        // Assert
        verify(laboratorioRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("eliminar - Debe lanzar LaboratorioException si ocurre error al eliminar")
    void testEliminarConError() {
        // Arrange
        doThrow(new RuntimeException("Error BD")).when(laboratorioRepository).deleteById(1L);

        // Act & Assert
        LaboratorioException exception = assertThrows(LaboratorioException.class,
                () -> laboratorioService.eliminar(deleteRqDto));
        assertEquals("Error al eliminar laboratorio", exception.getMessage());
    }

    @Test
    @DisplayName("eliminar - Debe lanzar excepción cuando DeleteRqDto es null")
    void testEliminarConDeleteRqDtoNull() {
        // Act & Assert
        assertThrows(LaboratorioException.class, () -> laboratorioService.eliminar(null));
    }
}