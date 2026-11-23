package pt.ulo.estg.dei.ei.dae.monitorizacao.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "alerts")
@NamedQueries({
        @NamedQuery(name = "getAllAlerts", query = "SELECT a FROM Alert a ORDER BY a.createdAt DESC")
})
public class Alert implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String severity; // LOW, MEDIUM, HIGH, CRITICAL

    @NotBlank
    private String message;

    private boolean resolved;

    @NotNull
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "volume_id")
    @NotNull
    private Volume volume;

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;

    @Version
    private int version;

    public Alert() {
        this.createdAt = LocalDateTime.now();
        this.resolved = false;
    }

    public Alert(String severity, String message, Volume volume, Sensor sensor) {
        this();
        this.severity = severity;
        this.message = message;
        this.volume = volume;
        this.sensor = sensor;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public boolean isResolved() { return resolved; }
    public void setResolved(boolean resolved) { this.resolved = resolved; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Volume getVolume() { return volume; }
    public void setVolume(Volume volume) { this.volume = volume; }

    public Sensor getSensor() { return sensor; }
    public void setSensor(Sensor sensor) { this.sensor = sensor; }

    public int getVersion() { return version; }
    public void setVersion(int version) { this.version = version; }
}
