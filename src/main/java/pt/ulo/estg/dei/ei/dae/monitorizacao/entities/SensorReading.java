package pt.ulo.estg.dei.ei.dae.monitorizacao.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "sensor_readings")
@NamedQueries({
        @NamedQuery(name = "getAllReadings", query = "SELECT r FROM SensorReading r ORDER BY r.timestamp DESC")
})
public class SensorReading implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Double readingValue;

    @NotNull
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    @NotNull
    private Sensor sensor;

    public SensorReading() {
        this.timestamp = LocalDateTime.now();
    }

    public SensorReading(Double readingValue, Sensor sensor) {
        this();
        this.readingValue = readingValue;
        this.sensor = sensor;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getReadingValue() { return readingValue; }
    public void setReadingValue(Double readingValue) { this.readingValue = readingValue; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public Sensor getSensor() { return sensor; }
    public void setSensor(Sensor sensor) { this.sensor = sensor; }
}
