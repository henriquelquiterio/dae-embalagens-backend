package pt.ulo.estg.dei.ei.dae.monitorizacao.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ulo.estg.dei.ei.dae.monitorizacao.entities.Client;
import pt.ulo.estg.dei.ei.dae.monitorizacao.entities.LogisticsOperator;
import pt.ulo.estg.dei.ei.dae.monitorizacao.entities.Manager;
import pt.ulo.estg.dei.ei.dae.monitorizacao.entities.User;

import java.util.List;

@Stateless
public class UserBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void createClient(String username, String password, String name, String email, Long clientId) {
        var client = new Client(username, password, name, email, clientId);
        entityManager.persist(client);
    }

    public void createManager(String username, String password, String name, String email) {
        var manager = new Manager(username, password, name, email);
        entityManager.persist(manager);
    }

    public void createLogisticsOperator(String username, String password, String name, String email) {
        var operator = new LogisticsOperator(username, password, name, email);
        entityManager.persist(operator);
    }

    public User find(String username) {
        return entityManager.find(User.class, username);
    }

    public List<User> getAll() {
        return entityManager.createNamedQuery("getAllUsers", User.class).getResultList();
    }
}