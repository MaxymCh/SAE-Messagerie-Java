
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;


public class ControleTrapezeSalon implements EventHandler<MouseEvent>  {
    private AppClient appClient;
    private Polygon trapeze;
    private ClientIHM clientIHM;
    private VBox vbSalonButtons;
    private ScrollPane scrollPaneSallons;

    public ControleTrapezeSalon(AppClient appClient, Polygon trapeze) {
        this.appClient = appClient;
        this.trapeze = trapeze;
    }



    @Override
    public void handle(MouseEvent event) {
        Button but = this.appClient.getAffichesalon();
        but.fire();
        if (! but.isDisabled()){
        if (but.getText().equals("Cacher Salon")){
            this.trapeze.setRotate(270);
        }
        else{
            this.trapeze.setRotate(90);}
        }
    }

}