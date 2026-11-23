package pt.ulo.estg.dei.ei.dae.monitorizacao.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("Client")
public class Client extends User {
    @Column(unique = true)
    private Long clientId;

    @OneToMany(mappedBy = "client", cascade = CascadeType.REMOVE)
    private List<Order> orders;

    public Client() {
        this.orders = new ArrayList<>();
    }

    public Client(String username, String password, String name, String email, Long clientId) {
        super(username, password, name, email);
        this.clientId = clientId;
        this.orders = new ArrayList<>();
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}