
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class ControleGroupeUser implements EventHandler<MouseEvent>  {
    private AppClient appClient;
    private Group grp;

    public ControleGroupeUser(AppClient appClient, Group grp) {
        this.appClient = appClient;
        this.grp = grp;
    }



    @Override
    public void handle(MouseEvent event) {
        Button but = this.appClient.getAfficheUser();
        but.fire();
        if (! but.isDisabled()){
        if (but.getText().equals("Cacher Salon")){
            this.grp.setRotate(180);
        }
        else{
            this.grp.setRotate(0);}
        }
    }

}