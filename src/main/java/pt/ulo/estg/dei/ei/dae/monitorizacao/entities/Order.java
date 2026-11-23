package pt.ulo.estg.dei.ei.dae.monitorizacao.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@NamedQueries({
        @NamedQuery(name = "getAllOrders", query = "SELECT o FROM Order o ORDER BY o.createdAt DESC")
})
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotBlank
    private String code;

    @NotBlank
    private String status; // PENDING, IN_TRANSIT, DELIVERED, CANCELLED

    @NotNull
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "client_username")
    @NotNull
    private Client client;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Volume> volumes;

    @Version
    private int version;

    public Order() {
        this.volumes = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
    }

    public Order(String code, String status, Client client) {
        this();
        this.code = code;
        this.status = status;
        this.client = client;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public List<Volume> getVolumes() { return volumes; }
    public void setVolumes(List<Volume> volumes) { this.volumes = volumes; }

    public int getVersion() { return version; }
    public void setVersion(int version) { this.version = version; }
}
