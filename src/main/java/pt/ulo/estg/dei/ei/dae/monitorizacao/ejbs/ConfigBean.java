package pt.ulo.estg.dei.ei.dae.monitorizacao.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

@Singleton
@Startup
public class ConfigBean {
    @EJB
    private UserBean userBean;

    @PostConstruct
    public void populateDB() {
        System.out.println("A inicializar base de dados de Monitorização...");
        userBean.createManager("gestor1", "1234", "Carlos Gestor", "gestor@embalagens.pt");
        userBean.createLogisticsOperator("logistica1", "1234", "Sistema Logística", "logistica@embalagens.pt");
        userBean.createClient("maria", "1234", "Maria Silva", "maria@cliente.pt", 10001L);
    }
}