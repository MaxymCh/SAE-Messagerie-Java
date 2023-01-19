

import javax.sound.sampled.Control;
import javax.swing.JButton;
import javax.swing.JPanel;

import javafx.stage.Stage;
import javafx.scene.Node;
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
import javafx.scene.input.KeyCode;
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

    private VBox vbSalonButtons;

	private Button envoyer = new Button("Envoyer");

    private Button afficherSalon = new Button("Afficher salon");

    private TextField monMessage = new TextField();

    private ScrollPane scrollPaneMessage = new ScrollPane();


    @Override
    public void init() {
        try{
        this.vbSalonButtons = new VBox();
        this.vBoxMessages = new VBox();
        this.panelCentral = new BorderPane();
        this.clientIHM = new ClientIHM(this);
        Thread clientReader = this.clientIHM.creeClientReader();
        this.monMessage.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                this.envoyer.fire();
            }
        });
        this.afficherSalon.setOnAction(new ControleurBoutonAfficherSalon(this, this.clientIHM, this.afficherSalon));
        this.afficherSalon.setDisable(true);
        this.envoyer.setOnAction(new ControleurBoutonTFEnvoyer(this.monMessage, this.clientIHM, this));
        this.monMessage.setId("inputBox" );
        this.monMessage.setPrefSize(500, 20);
        this.monMessage.setPromptText("Entrer votre message ici");}
        //vBoxMessages.heightProperty().addListener(null);}
        catch(ConnectException e){
            System.out.println(e);
        }

        
    }

private BorderPane fenetreMessagerie(){

        this.scrollPaneMessage.setContent(this.vBoxMessages);
        BorderPane interface1 = new BorderPane();
        ScrollPane spButtonSalon = new ScrollPane();
        spButtonSalon.setId("scrollpaneSalon");
        spButtonSalon.setContent(this.vbSalonButtons);
        interface1.setLeft(spButtonSalon);
        HBox HbenvoyerRecevoir = new HBox();
        HbenvoyerRecevoir.getChildren().addAll(this.afficherSalon,this.envoyer,this.monMessage);
        HbenvoyerRecevoir.setPadding(new Insets(10));
        interface1.setCenter(this.scrollPaneMessage);
        interface1.setBottom(HbenvoyerRecevoir);
        HbenvoyerRecevoir.setAlignment(Pos.CENTER);
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
                scrollPaneMessage.setVvalue(1.0);
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
    public void activeButtonAffiche(){
        this.afficherSalon.setDisable(false);
    }
    public VBox getSalon(){
        return this.vbSalonButtons;
    }
    public void clearSalon(){
        this.vbSalonButtons.getChildren().clear();
    }
    public void activeSalon(){
        for (Node bt : this.vbSalonButtons.getChildren()){
            Button button = (Button) bt;
            button.setDisable(false);
        }
    }
    public void majSalon(List<String> listeSalon){
        AppClient appCl = this;
        VBox vbSalonButtons = this.vbSalonButtons;

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vbSalonButtons.getChildren().clear();
                for (String salon : listeSalon) {
                    Button salonButton = new Button(salon);
                    salonButton.setId("salon");
                    salonButton.setOnAction(new ControleurBoutonSalon(salonButton, clientIHM, appCl));
                    vbSalonButtons.getChildren().add(salonButton);
                }
                vbSalonButtons.setSpacing(30);
                //salonButtons.setPrefHeight(50);
            }
        });
        

        }

    private Scene laScene(){
        BorderPane fenetre = new BorderPane();
        fenetre.setCenter(this.panelCentral);
        Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        fenetre.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        int height = (int) dimension.getHeight()/2;
        int width = (int) dimension.getWidth()/2; 
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
