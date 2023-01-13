import java.io.DataInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class ClientReaderIHM implements Runnable{
    private ClientIHM clientIHM;
    private Socket socket;
    private boolean clientLance;

    private AppClient appClient;
    private List<String> listeSalon;

    public ClientReaderIHM( ClientIHM clientIHM, Socket socket, AppClient appClient){
        this.clientIHM = clientIHM;
        this.socket = socket;
        this.clientLance = true;
        this.appClient = appClient;

    }

    public void stop_thread(){
        this.clientLance = false;
    }



        @Override
        public void run() {
            try{
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                String mes;
                while(clientLance){
                    mes = dis.readUTF();
    
                    if(mes.equals("true")){
                        mes = dis.readUTF();
                        clientIHM.setNomClient(mes);
                        //mes = dis.readUTF();
                        //appClient.setListSalon(mes);
                        break;
                    }
                    else{
                        appClient.ajouterMessage(mes);
                        System.out.println(mes);
                    }               
                }
               
                while(clientLance){
                    mes = dis.readUTF();
                    System.out.println(mes);
                    String[] messagePlusieursPartie = mes.split(":", 2);
                    String entete = messagePlusieursPartie[0];
                    String contenu = messagePlusieursPartie[1];
                    if(entete.equals("message")){appClient.ajouterMessage(contenu);}
                    else if(entete.equals("listeSalon")){
                        this.listeSalon = new ArrayList<>(Arrays.asList(contenu.replace(" ", "").split(",")));
                        System.out.println(this.listeSalon);
                        this.appClient.majSalon(this.listeSalon);

                    }
                                        
                }
            }
            catch(Exception e){
                System.out.println("Erreur l54 : "+e);
            }
    
        }

        

}

