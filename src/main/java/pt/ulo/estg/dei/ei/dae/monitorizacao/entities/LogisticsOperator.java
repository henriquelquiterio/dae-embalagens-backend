package pt.ulo.estg.dei.ei.dae.monitorizacao.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("LogisticsOperator")
public class LogisticsOperator extends User {
    public LogisticsOperator() {
    }

    public LogisticsOperator(String username, String password, String name, String email) {
        super(username, password, name, email);
    }
}