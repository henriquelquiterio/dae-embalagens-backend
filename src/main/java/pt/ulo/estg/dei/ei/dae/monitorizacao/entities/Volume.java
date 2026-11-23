package pt.ulo.estg.dei.ei.dae.monitorizacao.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "volumes")
@NamedQueries({
        @NamedQuery(name = "getAllVolumes", query = "SELECT v FROM Volume v ORDER BY v.code")
})
public class Volume implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotBlank
    private String code;

    @NotBlank
    private String description;

    @NotBlank
    private String status; // PACKED, IN_TRANSIT, DELIVERED, DAMAGED

    private Double minTemperature;
    private Double maxTemperature;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @NotNull
    private Order order;

    @OneToMany(mappedBy = "volume", cascade = CascadeType.ALL)
    private List<Sensor> sensors;

    @OneToMany(mappedBy = "volume", cascade = CascadeType.ALL)
    private List<Alert> alerts;

    @Version
    private int version;

    public Volume() {
        this.sensors = new ArrayList<>();
        this.alerts = new ArrayList<>();
    }

    public Volume(String code, String description, String status, Double minTemperature, Double maxTemperature, Order order) {
        this();
        this.code = code;
        this.description = description;
        this.status = status;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.order = order;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getMinTemperature() { return minTemperature; }
    public void setMinTemperature(Double minTemperature) { this.minTemperature = minTemperature; }

    public Double getMaxTemperature() { return maxTemperature; }
    public void setMaxTemperature(Double maxTemperature) { this.maxTemperature = maxTemperature; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public List<Sensor> getSensors() { return sensors; }
    public void setSensors(List<Sensor> sensors) { this.sensors = sensors; }

    public List<Alert> getAlerts() { return alerts; }
    public void setAlerts(List<Alert> alerts) { this.alerts = alerts; }

    public int getVersion() { return version; }
    public void setVersion(int version) { this.version = version; }
}