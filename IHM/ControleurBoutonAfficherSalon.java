import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import javafx.animation.TranslateTransition;
import javafx.util.Duration;


public class ControleurBoutonAfficherSalon implements EventHandler<ActionEvent>  {
    private AppClient appClient;
    private Button but;
    private ClientIHM clientIHM;
    private VBox vbSalonButtons;
    private ScrollPane scrollPaneSallons;

    public ControleurBoutonAfficherSalon(AppClient appClient, ClientIHM clientIHM, Button but, VBox vbSalonButtons, ScrollPane scrollPaneSallons) {
        this.appClient = appClient;
        this.but = but;
        this.clientIHM = clientIHM;
        this.vbSalonButtons = vbSalonButtons;
        this.scrollPaneSallons = scrollPaneSallons;
    }


    @Override
    public void handle(ActionEvent event) {
        if (this.appClient.getSalon().getChildren().size()>0){
                        
            scrollPaneSallons.setVisible(false); 
            vbSalonButtons.setVisible(false);
            this.appClient.clearSalon(); 

            this.but.setText("Afficher salon");
        }
        else{
            this.clientIHM.demandeSalon();;
            this.but.setText("Cacher Salon");
            scrollPaneSallons.setTranslateX(-500);
            scrollPaneSallons.setVisible(true); // pour rendre la VBox visible
            TranslateTransition transition = new TranslateTransition(Duration.seconds(1), scrollPaneSallons);
            transition.setByX(500); // pour déplacer la VBox vers la droite de 500 pixels
            transition.play();

            vbSalonButtons.setTranslateX(-500);
            vbSalonButtons.setVisible(true); // pour rendre la VBox visible
            transition = new TranslateTransition(Duration.seconds(1), vbSalonButtons);
            transition.setByX(500); // pour déplacer la VBox vers la droite de 500 pixels
            transition.play();
        }
    }

}