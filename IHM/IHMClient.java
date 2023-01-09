import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.application.Application;

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
import java.util.ArrayList;

public class IHMClient extends Application {

    private BorderPane panelCentral;
    private Stage affichage;


    private Client client;

	JButton bgo = new JButton("GO/STOP");
	JButton bstop = new JButton("Clear");

    @Override
    public void init() {
        this.panelCentral = new BorderPane();
        this.client = new Client(this);
        ClientReader clientReader = this.client.creeClientReader();
        this.client.mainSession();

        
    }

    private BorderPane fenetreMessagerie(){
         
        BorderPane interface1 = new BorderPane();
        VBox jeu = new VBox();
        TextField message1 = new TextField("Test textfield");
        message1.setEditable(false);
        jeu.getChildren().add(message1);
        interface1.setCenter(jeu);
        return interface1;
     }

     public void modeMessagerie(){
        this.panelCentral.setCenter(this.fenetreMessagerie());
    }

    public void ajouterMessage(){
        this.panelCentral.getCenter().getCenter().getChildren().add(new TextField("MEssage"));
    }
/*

	public void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource();

		if (source == bgo) chrono.go();
        else if  (source == bstop) chrono.stop();
	}
*/
	public void paintComponent(Graphics g)  {
		super.paintComponent(g);
		/*chrono.dessine(g);*/
	}

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
