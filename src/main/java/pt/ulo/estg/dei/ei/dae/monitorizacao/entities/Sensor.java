package pt.ulo.estg.dei.ei.dae.monitorizacao.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sensors")
@NamedQueries({
        @NamedQuery(name = "getAllSensors", query = "SELECT s FROM Sensor s ORDER BY s.code")
})
public class Sensor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotBlank
    private String code;

    @NotBlank
    private String type; // TEMPERATURE, HUMIDITY, SHOCK, GPS

    @NotBlank
    private String unit; // °C, %, G, lat/lon

    private boolean active;

    @ManyToOne
    @JoinColumn(name = "volume_id")
    @NotNull
    private Volume volume;

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL)
    private List<SensorReading> readings;

    @Version
    private int version;

    public Sensor() {
        this.readings = new ArrayList<>();
        this.active = true;
    }

    public Sensor(String code, String type, String unit, Volume volume) {
        this();
        this.code = code;
        this.type = type;
        this.unit = unit;
        this.volume = volume;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public Volume getVolume() { return volume; }
    public void setVolume(Volume volume) { this.volume = volume; }

    public List<SensorReading> getReadings() { return readings; }
    public void setReadings(List<SensorReading> readings) { this.readings = readings; }

    public int getVersion() { return version; }
    public void setVersion(int version) { this.version = version; }
}
