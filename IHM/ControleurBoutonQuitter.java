import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;



public class ControleurBoutonQuitter implements EventHandler<ActionEvent>  {

    private Button boutonQuitter;
    private ClientIHM clientIHM;
    private AppClient appClient;
    public ControleurBoutonQuitter(Button boutonQuitter, ClientIHM clientIHM, AppClient appClient) {
        this.boutonQuitter = boutonQuitter;
        this.clientIHM = clientIHM;
        this.appClient = appClient;
    }


    @Override
    public void handle(ActionEvent event) {
        this.clientIHM.envoyerMessage("/quit");
    }

}