import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;


public class ControleurBoutonAfficherSalon implements EventHandler<ActionEvent>  {
    private AppClient appClient;
    private Button but;
    private ClientIHM clientIHM;
    public ControleurBoutonAfficherSalon(AppClient appClient, ClientIHM clientIHM, Button but) {
        this.appClient = appClient;
        this.but = but;
        this.clientIHM = clientIHM;
    }


    @Override
    public void handle(ActionEvent event) {
        if (this.appClient.getSalon().getChildren().size()>0){
            this.appClient.clearSalon();
            this.but.setText("Afficher salon");
        }
        else{
            this.clientIHM.envoyerMessage("/salon");
            this.but.setText("Cacher Salon");
        }
    }

}