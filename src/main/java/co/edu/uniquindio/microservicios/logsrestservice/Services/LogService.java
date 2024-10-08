package co.edu.uniquindio.microservicios.logsrestservice.Services;

import co.edu.uniquindio.microservicios.logsrestservice.DTOs.LogDTO;
import co.edu.uniquindio.microservicios.logsrestservice.Entities.Log;
import co.edu.uniquindio.microservicios.logsrestservice.Repositories.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class LogService {
    @Autowired
    private LogRepository logRepository;

    public Log createLog(LogDTO logDTO) {
        Log log = new Log();
        log.setApplication(logDTO.application());
        log.setLogType(logDTO.logType());
        log.setClassName(logDTO.className());
        log.setTimestamp(logDTO.timestamp());
        log.setSummary(logDTO.summary());
        log.setDescription(logDTO.description());
        return logRepository.save(log);
    }

    public Page<Log> getLogsByFilters(
            String application, String logType, LocalDateTime startDate, LocalDateTime endDate, int page, int size) {

        // Si no se proveen las fechas, establecer un rango por defecto
        if (startDate == null) {
            startDate = LocalDateTime.of(1970, 1, 1, 0, 0); // Fecha inicial muy lejana
        }
        if (endDate == null) {
            endDate = LocalDateTime.now(); // Fecha actual
        }

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Log> logs;

        // Filtrar según los parámetros provistos
        if (application != null && logType != null) {
            logs = logRepository.findByApplicationAndLogTypeAndTimestampBetween(application, logType, startDate, endDate, pageRequest);
        } else if (application != null) {
            logs = logRepository.findByApplicationAndTimestampBetween(application, startDate, endDate, pageRequest);
        } else if (logType != null) {
            logs = logRepository.findByLogTypeAndTimestampBetween(logType, startDate, endDate, pageRequest);
        } else {
            logs = logRepository.findByTimestampBetween(startDate, endDate, pageRequest);
        }

        // Si no se encuentran registros, lanzar una excepción
        if (logs.isEmpty()) {
            throw new NoSuchElementException("No se encontraron logs con los filtros especificados");
        }

        return logs;
    }

    public void deleteLogById(Long id) {
        Log log = logRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Log no encontrado"));
        logRepository.delete(log);
    }

    public void deleteAllLogs() {
        logRepository.deleteAll();
    }

    public Log findByDescription(String description){
        return(logRepository.findByDescription(description));
    }
}
