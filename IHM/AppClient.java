
import javafx.stage.Stage;
import javafx.util.Duration;
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
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.List;
import java.net.ConnectException;

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

    private ScrollPane scrollPaneSallons = new ScrollPane();


    @Override
    public void init() {
        try{
        this.vbSalonButtons = new VBox();
        this.scrollPaneSallons.setVisible(false);

        this.vBoxMessages = new VBox();
        this.panelCentral = new BorderPane();
        this.clientIHM = new ClientIHM(this);
        Thread clientReader = this.clientIHM.creeClientReader();
        this.monMessage.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                this.envoyer.fire();
            }
        });
        this.afficherSalon.setOnAction(new ControleurBoutonAfficherSalon(this, this.clientIHM, this.afficherSalon, this.vbSalonButtons, scrollPaneSallons));
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
        this.scrollPaneMessage.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        BorderPane interface1 = new BorderPane();
        this.scrollPaneSallons.setId("scrollpaneSalon");
        this.scrollPaneSallons.setContent(this.vbSalonButtons);
        this.scrollPaneSallons.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        BorderPane bpLeft = new BorderPane();
        bpLeft.setCenter(this.scrollPaneSallons);
        bpLeft.setRight(this.afficherSalon);
        bpLeft.setAlignment(this.afficherSalon, Pos.CENTER);
        interface1.setLeft(bpLeft);
        HBox HbenvoyerRecevoir = new HBox();
        HbenvoyerRecevoir.getChildren().addAll(this.envoyer,this.monMessage);
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
                textFlow.setPrefWidth(650);
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
