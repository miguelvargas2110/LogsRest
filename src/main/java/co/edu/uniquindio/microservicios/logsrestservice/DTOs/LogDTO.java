package co.edu.uniquindio.microservicios.logsrestservice.DTOs;

import java.time.LocalDateTime;

public record LogDTO (
        String application,
        String logType,
        String className,
        LocalDateTime timestamp,
        String summary,
        String description
){}
