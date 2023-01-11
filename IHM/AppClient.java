

import javax.sound.sampled.Control;
import javax.swing.JButton;
import javax.swing.JPanel;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Application;
import java.awt.Dimension;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.ButtonBar.ButtonData ;

import java.util.Set;

import javax.swing.border.LineBorder;


import java.util.HashSet;
import java.util.List;
import java.util.Arrays;
import java.io.File;
import java.net.ConnectException;
import java.util.ArrayList;

public class AppClient extends Application {

    private BorderPane panelCentral;
    private Stage affichage;


    private ClientIHM clientIHM;

    private VBox vBoxMessages;

	private Button envoyer = new Button("Envoyer");

    private TextField monMessage = new TextField();

    private ScrollPane scrollPaneMessage = new ScrollPane();

    @Override
    public void init() {
        try{
        this.vBoxMessages = new VBox();
        this.panelCentral = new BorderPane();
        this.clientIHM = new ClientIHM(this);
        Thread clientReader = this.clientIHM.creeClientReader();
        this.envoyer.setOnAction(new ControleurBoutonTFEnvoyer(this.monMessage, this.clientIHM, this));
        }
        catch(ConnectException e){
            System.out.println(e);
        }

        
    }

    private BorderPane fenetreMessagerie(){
        this.scrollPaneMessage.setContent(this.vBoxMessages);
        BorderPane interface1 = new BorderPane();
        HBox hBox = new HBox();
        VBox envoyerRecevoir = new VBox();
        envoyerRecevoir.getChildren().addAll(this.monMessage, this.envoyer);
        hBox.getChildren().addAll(this.scrollPaneMessage, envoyerRecevoir);
        interface1.setCenter(hBox);
        return interface1;
     }

     public void modeMessagerie(){
        this.panelCentral.setCenter(this.fenetreMessagerie());
    }
/*
    public void ajouterMessage(String message){
        TextField tfMessage = new TextField(message);
        tfMessage.setEditable(false);
        this.vBoxMessages.getChildren().add(tfMessage);

    }
*/
    public void ajouterMessage(String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Label labelMessage = new Label(message);
                vBoxMessages.getChildren().add(labelMessage);
                scrollPaneMessage.setVvalue(1);
            }
        });
  
    }


    public void clearTFMessage() {
        this.monMessage.clear();
    }
/*

	public void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource();

		if (source == bgo) chrono.go();
        else if  (source == bstop) chrono.stop();
	}
*/


    private Scene laScene(){
        BorderPane fenetre = new BorderPane();
        fenetre.setCenter(this.panelCentral);
        Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int) dimension.getHeight();
        int width = (int) dimension.getWidth(); 
        return new  Scene(fenetre,width,height);
    }

     /**
     * créer le graphe de scène et lance le jeu
     * @param stage la fenêtre principale
     */
    @Override
    public void start(Stage stage) {
        this.affichage = stage;

        this.affichage.setTitle("Messagerie Client");
        this.affichage.setScene(this.laScene());
        this.modeMessagerie();
        
        this.affichage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
