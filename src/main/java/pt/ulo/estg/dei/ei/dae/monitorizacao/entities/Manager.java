package pt.ulo.estg.dei.ei.dae.monitorizacao.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Manager")
public class Manager extends User {
    public Manager() {
    }

    public Manager(String username, String password, String name, String email) {
        super(username, password, name, email);
    }
}
