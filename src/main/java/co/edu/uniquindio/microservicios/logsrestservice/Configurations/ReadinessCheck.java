package co.edu.uniquindio.microservicios.logsrestservice.Configurations;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReadinessCheck implements HealthIndicator {

    private LocalDateTime startTime;

    @EventListener(ContextRefreshedEvent.class)
    public void init() {
        this.startTime = LocalDateTime.now();
    }

    @Override
    public Health health() {
        return Health.up()
                .withDetail("from", startTime.toString())
                .withDetail("status", "READY")
                .build();
    }
}
