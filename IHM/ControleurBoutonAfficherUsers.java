import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import javafx.animation.TranslateTransition;
import javafx.util.Duration;


public class ControleurBoutonAfficherUsers implements EventHandler<ActionEvent>  {
    private AppClient appClient;
    private Button but;
    private ClientIHM clientIHM;
    private VBox vbSalonUsers;
    private ScrollPane scrollPaneUsers;

    public ControleurBoutonAfficherUsers(AppClient appClient, ClientIHM clientIHM, Button but, VBox vbSalonUsers, ScrollPane scrollPaneUsers) {
        this.appClient = appClient;
        this.but = but;
        this.clientIHM = clientIHM;
        this.vbSalonUsers = vbSalonUsers;
        this.scrollPaneUsers = scrollPaneUsers;
    }


    @Override
    public void handle(ActionEvent event) {
        if (this.but.getText().equals("Cacher Salon")){
                        
            scrollPaneUsers.setVisible(false); 
            vbSalonUsers.setVisible(false);
            this.appClient.clearUser(); 

            this.but.setText("Afficher salon");
        }
        else{
            this.clientIHM.envoyerMessage("/users");
            this.but.setText("Cacher Salon");
            scrollPaneUsers.setTranslateX(-500);
            scrollPaneUsers.setVisible(true); // pour rendre la VBox visible
            TranslateTransition transition = new TranslateTransition(Duration.seconds(1), scrollPaneUsers);
            transition.setByX(500); // pour déplacer la VBox vers la droite de 500 pixels
            transition.play();

            vbSalonUsers.setTranslateX(-500);
            vbSalonUsers.setVisible(true); // pour rendre la VBox visible
            transition = new TranslateTransition(Duration.seconds(1), vbSalonUsers);
            transition.setByX(500); // pour déplacer la VBox vers la droite de 500 pixels
            transition.play();
        }
    }

}