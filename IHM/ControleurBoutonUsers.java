import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;



public class ControleurBoutonUsers implements EventHandler<ActionEvent>  {

    private Button bt;
    private AppClient appClient;
    public ControleurBoutonUsers(Button bt, AppClient appClient) {
        this.bt = bt;
        this.appClient = appClient;
    }


    @Override
    public void handle(ActionEvent event) {
        this.appClient.setTI("@"+this.bt.getText()+" ");
    }

}