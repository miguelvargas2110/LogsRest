package co.edu.uniquindio.microservicios.logsrestservice.Repositories;

import co.edu.uniquindio.microservicios.logsrestservice.Entities.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
    Page<Log> findByApplicationAndLogTypeAndTimestampBetween(
            String application,
            String logType,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    );

    Page<Log> findByApplicationAndTimestampBetween(
            String application,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    );

    Page<Log> findByLogTypeAndTimestampBetween(
            String logType,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    );

    Page<Log> findByTimestampBetween(
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    );

    Log findByDescription(String description);
}
