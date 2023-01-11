import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;



public class ControleurBoutonTFEnvoyer implements EventHandler<ActionEvent>  {

    private TextField textField;
    private ClientIHM clientIHM;
    private AppClient appClient;
    public ControleurBoutonTFEnvoyer(TextField textField, ClientIHM clientIHM, AppClient appClient) {
        this.textField = textField;
        this.clientIHM = clientIHM;
        this.appClient = appClient;
    }


    @Override
    public void handle(ActionEvent event) {
        System.out.println(this.textField.getText());
        this.clientIHM.envoyerMessage(this.textField.getText());
        this.appClient.clearTFMessage();
    }

}