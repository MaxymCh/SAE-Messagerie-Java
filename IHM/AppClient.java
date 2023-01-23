
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import java.awt.Dimension;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.List;
import java.net.ConnectException;

public class AppClient extends Application {

    private BorderPane panelCentral =  new BorderPane();

    private Stage affichage;

    private ClientIHM clientIHM;

    private VBox vBoxMessages = new VBox();

    private VBox vbSalonButtons = new VBox();

    private VBox vbUsersButtons = new VBox();

    private Label salonActuel = new Label("Vous étes dans : Accueil");

    private Button quitter = new Button("Quitter");

	private Button envoyer = new Button("Envoyer");

    private Button afficherSalon = new Button("Afficher salon");

    private Button afficherUsers = new Button("Afficher salon");

    private TextField monMessage = new TextField();

    private ScrollPane scrollPaneMessage = new ScrollPane();

    private ScrollPane scrollPaneSallons = new ScrollPane();

    private ScrollPane scrollPaneUsers = new ScrollPane();

    private Group groupSalon = new Group();

    private Group groupUser = new Group();


    @Override
    public void init() {
        try{
        this.quitter.setId("Quitter");
        this.scrollPaneSallons.setVisible(false);
        this.scrollPaneUsers.setVisible(false);
        this.clientIHM = new ClientIHM(this);
        this.clientIHM.creeClientReader();
        this.monMessage.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                this.envoyer.fire();
            }
        });
        this.afficherUsers.setOnAction(new ControleurBoutonAfficherUsers(this, this.clientIHM, this.afficherUsers, this.vbUsersButtons, this.scrollPaneUsers));
        this.afficherSalon.setOnAction(new ControleurBoutonAfficherSalon(this, this.clientIHM, this.afficherSalon, this.vbSalonButtons, this.scrollPaneSallons));
        this.afficherSalon.setDisable(true);
        this.envoyer.setOnAction(new ControleurBoutonTFEnvoyer(this.monMessage, this.clientIHM, this));
        this.monMessage.setId("inputBox" );
        this.monMessage.setPrefSize(500, 20);
        this.monMessage.setPromptText("Entrer votre message ici");

        this.quitter.setOnAction(new ControleurBoutonQuitter(this.quitter, this.clientIHM, this));
        }
        //vBoxMessages.heightProperty().addListener(null);}
        catch(ConnectException e){
            System.out.println(e);
        }

        
    }
    public void quitterApp(){
        Platform.exit();
    }


    public Button getAfficheUser(){
        return this.afficherUsers;
    }
    public Button getAffichesalon(){
        return this.afficherSalon;
    }
    private BorderPane fenetreMessagerie(){
            this.scrollPaneUsers.setId("scrollpaneSalon");
            this.scrollPaneUsers.setContent(this.vbUsersButtons);
            this.scrollPaneUsers.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

            this.scrollPaneMessage.setContent(this.vBoxMessages);
            this.scrollPaneMessage.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

            BorderPane interface1 = new BorderPane();
            this.scrollPaneSallons.setId("scrollpaneSalon");
            this.scrollPaneSallons.setContent(this.vbSalonButtons);
            this.scrollPaneSallons.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            Polygon trapezeSalon = new Polygon();
            trapezeSalon.setFill(Color.rgb(64, 68, 75));
            trapezeSalon.getPoints().addAll(new Double[]{
                200.0, 125.0,
                225.0, 150.0,
                125.0, 150.0,
                150.0, 125.0
            });
            trapezeSalon.setRotate(90);
            BorderPane bpLeft = new BorderPane();
            bpLeft.setCenter(this.scrollPaneSallons);
            double centerXSalon = (trapezeSalon.getPoints().get(0) + trapezeSalon.getPoints().get(2) + trapezeSalon.getPoints().get(4) + trapezeSalon.getPoints().get(6)) / 4;
            double centerYSalon = (trapezeSalon.getPoints().get(1) + trapezeSalon.getPoints().get(3) + trapezeSalon.getPoints().get(5) + trapezeSalon.getPoints().get(7)) / 4;
            Text textSalon = new Text(">");

            textSalon.setX(centerXSalon-5);
            textSalon.setY(centerYSalon+5);
            textSalon.setFont(Font.font(24));
            textSalon.setFill(Color.WHITE);
            this.groupSalon.getChildren().add(trapezeSalon);
            this.groupSalon.getChildren().add(textSalon);
            this.groupSalon.setOnMouseClicked(new ControleTrapezeSalon(this, this.groupSalon));
            bpLeft.setRight(this.groupSalon);
            bpLeft.setPadding(new Insets(20));
            bpLeft.setAlignment(this.groupSalon, Pos.CENTER);
            interface1.setLeft(bpLeft);
            HBox HbenvoyerRecevoir = new HBox();
            HbenvoyerRecevoir.getChildren().addAll(this.envoyer,this.monMessage);
            HbenvoyerRecevoir.setPadding(new Insets(10));
            interface1.setCenter(this.scrollPaneMessage);
            interface1.setBottom(HbenvoyerRecevoir);
            Polygon trapezeUser = new Polygon();
            trapezeUser.setFill(Color.rgb(64, 68, 75));
            trapezeUser.getPoints().addAll(new Double[]{
                200.0, 125.0,
                225.0, 150.0,
                125.0, 150.0,
                150.0, 125.0
            });
            trapezeUser.setRotate(270);
            BorderPane bpRight = new BorderPane();
            bpRight.setCenter(this.scrollPaneUsers);
            double centerXUser = (trapezeUser.getPoints().get(0) + trapezeUser.getPoints().get(2) + trapezeUser.getPoints().get(4) + trapezeUser.getPoints().get(6)) / 4;
            double centerYUser = (trapezeUser.getPoints().get(1) + trapezeUser.getPoints().get(3) + trapezeUser.getPoints().get(5) + trapezeUser.getPoints().get(7)) / 4;
            Text textUser = new Text("<");

            textUser.setX(centerXUser-5);
            textUser.setY(centerYUser+5);
            textUser.setFont(Font.font(24));
            textUser.setFill(Color.WHITE);
            this.groupUser.getChildren().add(trapezeUser);
            this.groupUser.getChildren().add(textUser);
            this.groupUser.setOnMouseClicked(new ControleGroupeUser(this, this.groupUser));
            bpRight.setRight(this.groupUser);
            bpRight.setPadding(new Insets(20));
            bpRight.setAlignment(this.groupUser, Pos.CENTER);
            interface1.setRight(bpRight);
            HbenvoyerRecevoir.setAlignment(Pos.CENTER);

            BorderPane header = new BorderPane();
            header.setRight(this.quitter);

            Label titre = new Label("DISCOURD");
            header.setCenter(titre);
            header.setBottom(this.salonActuel);
            header.setPadding(new Insets(20));
            //header.setAlignment(salonActuel, Pos.CENTER);
            interface1.setTop(header);
            return interface1;
        }

     public void modeMessagerie(){
        this.panelCentral.setCenter(this.fenetreMessagerie());
    }
/*
    String entre = this.textField.getText();
        String entre2 = null;
        Label lb = new Label(entre);
        Label lb2 = null;
        int i = 1;
        while (entre.length()>30){
            lb.setText(entre.substring(i));
            lb2 = new Label(entre.substring(i, entre.length()));
            i ++;
        }
        this.clientIHM.envoyerMessage(lb.getText());
        if (lb2 != null){
            this.clientIHM.envoyerMessage(lb2.getText());
        }
        this.appClient.clearTFMessage();
    }
    }
*/

    public void ajouterMessage(String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                TextFlow textFlow = new TextFlow();
                textFlow.setPrefWidth(400);
                Text txt = new Text(message);
                txt.setFill(Color.WHITE);
                textFlow.getChildren().add(txt);
                vBoxMessages.getChildren().add(textFlow);
                scrollPaneMessage.setVvalue(1.0);
                Timeline timeline = new Timeline();
                KeyValue kv = new KeyValue(scrollPaneMessage.vvalueProperty(), 1.0);
                KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
                timeline.getKeyFrames().add(kf);
                timeline.play();

            }
        });
    }

    public void setTI(String texte){
        this.monMessage.setText(texte);
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
    public Label getLabSalonActuel(){
        return this.salonActuel;
    }
    public void setSalonActuel(String nom){
        this.salonActuel.setText(nom);
    }
    public void clearSalon(){
        this.vbSalonButtons.getChildren().clear();
    }

    public void clearUser(){
        this.vbUsersButtons.getChildren().clear();
    }

    public void clearLesMessageEnCours(){
        this.vBoxMessages.getChildren().clear();
    }

    public void ajouterLesMessagesEnCoursSalon(){
        this.vBoxMessages.getChildren().clear();
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
                if(scrollPaneSallons.isVisible()){
                    scrollPaneSallons.setVisible(false); 
                    vbSalonButtons.setVisible(false);
                    scrollPaneSallons.setVisible(true); 
                    vbSalonButtons.setVisible(true);
                }
                
                vbSalonButtons.getChildren().clear();
                for (String salon : listeSalon) {
                    Button salonButton = new Button(salon);
                    salonButton.setId("salon");
                    salonButton.setOnAction(new ControleurBoutonSalon(salonButton, clientIHM, appCl));
                    vbSalonButtons.getChildren().add(salonButton);
                }
                vbSalonButtons.setSpacing(30);
                vbSalonButtons.setPadding(new Insets(20));
                //salonButtons.setPrefHeight(50);
            }
        });
        

    }

    public void majUser(List<String> listeUsers){
        AppClient appCl = this;
        VBox vbUsersButtons = this.vbUsersButtons;

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vbUsersButtons.getChildren().clear();
                if(scrollPaneUsers.isVisible()){
                    scrollPaneUsers.setVisible(false); 
                    vbUsersButtons.setVisible(false);
                    scrollPaneUsers.setVisible(true); 
                    vbUsersButtons.setVisible(true);
                }
                for (String user : listeUsers) {
                    Button userButton = new Button(user);
                    userButton.setId("salon");
                    userButton.setOnAction(new ControleurBoutonUsers(userButton, appCl));
                    vbUsersButtons.getChildren().add(userButton);
                }
                vbUsersButtons.setSpacing(30);
                vbUsersButtons.setPadding(new Insets(20));
            }
        });
        

    }

    public void majMessage(List<String> listeMessages){
        for(String message: listeMessages){
            this.ajouterMessage(message);
        }
        

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
