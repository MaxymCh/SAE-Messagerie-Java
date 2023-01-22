import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;



public class ControleurBoutonSalon implements EventHandler<ActionEvent>  {

    private Button bt;
    private ClientIHM clientIHM;
    private AppClient appClient;
    public ControleurBoutonSalon(Button bt, ClientIHM clientIHM, AppClient appClient) {
        this.bt = bt;
        this.clientIHM = clientIHM;
        this.appClient = appClient;
    }


    @Override
    public void handle(ActionEvent event) {
        this.appClient.clearLesMessageEnCours();
        this.clientIHM.envoyerMessage("/join "+this.bt.getText());
        this.clientIHM.envoyerMessage("/demandeMessage");
    }

}