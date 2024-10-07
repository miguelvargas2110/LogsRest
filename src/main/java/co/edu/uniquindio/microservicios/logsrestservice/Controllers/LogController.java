package co.edu.uniquindio.microservicios.logsrestservice.Controllers;

import co.edu.uniquindio.microservicios.logsrestservice.DTOs.LogDTO;
import co.edu.uniquindio.microservicios.logsrestservice.Entities.ApiError;
import co.edu.uniquindio.microservicios.logsrestservice.Entities.ApiSuccess;
import co.edu.uniquindio.microservicios.logsrestservice.Entities.Log;
import co.edu.uniquindio.microservicios.logsrestservice.Services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/logs")
public class LogController {

    @Autowired
    private LogService logService;

    // Método para crear un log
    @PostMapping
    public ResponseEntity<?> createLog(@RequestBody LogDTO logDTO, HttpServletRequest request) {
        try {
            Log log = logService.createLog(logDTO);
            ApiSuccess apiSuccess = new ApiSuccess(
                    HttpStatus.CREATED.value(),
                    HttpStatus.CREATED.getReasonPhrase(),
                    "Log creado con éxito",
                    request.getRequestURI()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
        } catch (Exception e) {
            ApiError apiError = new ApiError(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    "Error al crear el log",
                    request.getRequestURI()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
        }
    }

    // Método para obtener los logs con filtros
    @GetMapping
    public ResponseEntity<?> getLogs(
            @RequestParam(required = false) String application,
            @RequestParam(required = false) String logType,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {

        try {
            Page<Log> logs = logService.getLogsByFilters(application, logType, startDate, endDate, page, size);

            // Empaquetar la respuesta con metadatos de la paginación
            ApiSuccess apiSuccess = new ApiSuccess(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    "Logs obtenidos con éxito",
                    request.getRequestURI(),
                    logs.getContent(),   // Lista de logs
                    logs.getTotalPages(), // Número total de páginas
                    logs.getTotalElements()  // Total de logs
            );

            return ResponseEntity.ok(apiSuccess);

        } catch (NoSuchElementException e) {
            ApiError apiError = new ApiError(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    "No se encontraron logs con los filtros especificados",
                    request.getRequestURI()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
        } catch (Exception e) {
            ApiError apiError = new ApiError(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    "Error al obtener los logs",
                    request.getRequestURI()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
        }
    }

    // Método para eliminar un log por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLogById(@PathVariable Long id, HttpServletRequest request) {
        try {
            logService.deleteLogById(id);
            ApiSuccess apiSuccess = new ApiSuccess(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    "Log eliminado con éxito",
                    request.getRequestURI()
            );
            return ResponseEntity.ok(apiSuccess);
        } catch (NoSuchElementException e) {
            ApiError apiError = new ApiError(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    "Log no encontrado",
                    request.getRequestURI()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
        } catch (Exception e) {
            ApiError apiError = new ApiError(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    "Error al eliminar el log",
                    request.getRequestURI()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
        }
    }

    // Método para eliminar todos los logs
    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAllLogs(HttpServletRequest request) {
        try {
            logService.deleteAllLogs();
            ApiSuccess apiSuccess = new ApiSuccess(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    "Todos los logs fueron eliminados con éxito",
                    request.getRequestURI()
            );
            return ResponseEntity.ok(apiSuccess);
        } catch (Exception e) {
            ApiError apiError = new ApiError(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    "Error al eliminar todos los logs",
                    request.getRequestURI()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
        }
    }
}

