package pt.ulo.estg.dei.ei.dae.monitorizacao.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Client")
public class Client extends User {
    @Column(unique = true)
    private Long clientId;

    public Client() {
    }

    public Client(String username, String password, String name, String email, Long clientId) {
        super(username, password, name, email);
        this.clientId = clientId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}