package co.edu.uniquindio.microservicios.logsrestservice.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Data
@Table(name = "logs")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String application;

    @Column(nullable = false)
    private String logType;

    @Column(nullable = false)
    private String className;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private String summary;

    @Column(nullable = false)
    private String description;

}