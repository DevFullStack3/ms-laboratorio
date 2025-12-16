package cl.kemolinaj.ms.laboratorio.exceptions;

import cl.kemolinaj.ms.laboratorio.dtos.ErrorDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("RestHandlerException - Pruebas unitarias")
class RestHandlerExceptionTest {

    private RestHandlerException restHandlerException;

    @BeforeEach
    void setUp() {
        restHandlerException = new RestHandlerException();
    }

    @Test
    @DisplayName("Debe retornar error 400 cuando se lanza MethodArgumentNotValidException")
    void testHandleMethodArgumentNotValidException() {
        // Arrange
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        var bindingResult = mock(org.springframework.validation.BindingResult.class);

        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(java.util.Collections.emptyList());

        // Act
        ResponseEntity<ErrorDto> response = restHandlerException.handleException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().code());
        assertEquals("NOK", response.getBody().message());
    }

    @Test
    @DisplayName("Debe retornar error 400 cuando se lanza BadRequestException")
    void testHandleBadRequestException() {
        // Arrange
        String mensaje = "Solicitud inválida";
        BadRequestException exception = new BadRequestException(mensaje);

        // Act
        ResponseEntity<ErrorDto> response = restHandlerException.handleException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().code());
        assertEquals("NOK", response.getBody().message());
        assertEquals(mensaje, response.getBody().description());
    }

    @Test
    @DisplayName("Debe retornar error 500 cuando se lanza LaboratorioException")
    void testHandleLaboratorioException() {
        // Arrange
        String mensaje = "Error en el laboratorio";
        LaboratorioException exception = new LaboratorioException(mensaje);

        // Act
        ResponseEntity<ErrorDto> response = restHandlerException.handleException(exception);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().code());
        assertEquals("NOK", response.getBody().message());
        assertEquals(mensaje, response.getBody().description());
    }

    @Test
    @DisplayName("Debe retornar error 500 para excepciones genéricas no controladas")
    void testHandleGenericException() {
        // Arrange
        Exception exception = new Exception("Error inesperado");

        // Act
        ResponseEntity<ErrorDto> response = restHandlerException.handleException(exception);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Debe retornar error 400 para BadRequestException con mensaje vacío")
    void testHandleBadRequestExceptionWithEmptyMessage() {
        // Arrange
        BadRequestException exception = new BadRequestException("");

        // Act
        ResponseEntity<ErrorDto> response = restHandlerException.handleException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("", response.getBody().description());
    }

    @Test
    @DisplayName("Debe retornar error 500 para LaboratorioException con mensaje vacío")
    void testHandleLaboratorioExceptionWithEmptyMessage() {
        // Arrange
        LaboratorioException exception = new LaboratorioException("");

        // Act
        ResponseEntity<ErrorDto> response = restHandlerException.handleException(exception);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("", response.getBody().description());
    }

    @Test
    @DisplayName("La clase debe tener anotación RestControllerAdvice")
    void testClassHasRestControllerAdviceAnnotation() {
        // Assert
        assertTrue(RestHandlerException.class.isAnnotationPresent(RestControllerAdvice.class));
    }

    @Test
    @DisplayName("Debe manejar excepciones hijas de BadRequestException")
    void testHandleSubclassOfBadRequestException() {
        // Arrange
        class CustomBadRequestException extends BadRequestException {
            public CustomBadRequestException(String message) {
                super(message);
            }
        }

        CustomBadRequestException exception = new CustomBadRequestException("Error personalizado");

        // Act
        ResponseEntity<ErrorDto> response = restHandlerException.handleException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Error personalizado", response.getBody().description());
    }

    @Test
    @DisplayName("Debe manejar excepciones hijas de LaboratorioException")
    void testHandleSubclassOfLaboratorioException() {
        // Arrange
        class CustomLaboratorioException extends LaboratorioException {
            public CustomLaboratorioException(String message) {
                super(message);
            }
        }

        CustomLaboratorioException exception = new CustomLaboratorioException("Error de laboratorio personalizado");

        // Act
        ResponseEntity<ErrorDto> response = restHandlerException.handleException(exception);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Error de laboratorio personalizado", response.getBody().description());
    }
}