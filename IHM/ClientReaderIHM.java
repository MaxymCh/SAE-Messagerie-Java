import java.io.DataInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class ClientReaderIHM implements Runnable{
    private ClientIHM clientIHM;
    private Socket socket;

    private AppClient appClient;
    private List<String> listeSalon;
    
    private List<String> messageActuelleDansSalon;

    public ClientReaderIHM( ClientIHM clientIHM, Socket socket, AppClient appClient){
        this.clientIHM = clientIHM;
        this.socket = socket;
        this.appClient = appClient;

    }
        @Override
        public void run() {
            try{
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                String mes;            
                while(true){
                    mes = dis.readUTF();
                    String[] messagePlusieursPartie = mes.split(":", 2);
                    String entete = messagePlusieursPartie[0];
                    String contenu = messagePlusieursPartie[1];
                    System.out.println(entete+"  "+contenu);
                    if(entete.equals("message")){appClient.ajouterMessage(contenu);}
                    else if (entete.equals("name")){
                        if (contenu.equals("true")){
                            mes = dis.readUTF();
                            clientIHM.setNomClient(mes);
                            appClient.activeButtonAffiche();
                        }
                        
                    }
                    else if(entete.equals("listeSalon")){
                        this.listeSalon = new ArrayList<>(Arrays.asList(contenu.replace(" ", "").split(",")));
                        System.out.println(listeSalon);
                        this.appClient.majSalon(this.listeSalon);
                    }

                    else if(entete.equals("listeMessages")){
                        System.out.println(contenu);
                        this.messageActuelleDansSalon = new ArrayList<>(Arrays.asList(contenu.split(",:,;,")));
                        System.out.println(this.messageActuelleDansSalon);
                        this.appClient.majMessage(this.messageActuelleDansSalon);

                    }

                    else if(entete.equals("quit")){
                        if (contenu.equals("tout")){
                            this.appClient.quitterApp();
                            break;
                            
                        }
                    }
                                        
                }
            }
            catch(Exception e){
                System.out.println("Erreur l54 : "+e);
            }
    
        }

        

}

