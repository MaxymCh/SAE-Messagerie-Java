import java.io.DataInputStream;
import java.net.Socket;
import javafx.application.Platform;


public class ClientReaderIHM implements Runnable{
    private ClientIHM clientIHM;
    private Socket socket;
    private boolean clientLance;

    private AppClient appClient;

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
                        break;
                    }
                    else{
                        appClient.ajouterMessage(mes);
                        System.out.println(mes);
                    }               
                }
    
                while(clientLance){
                    mes = dis.readUTF();
                    appClient.ajouterMessage(mes);
                    System.out.println(mes);
                }
            }
            catch(Exception e){
                System.out.println("Erreur : "+e);
            }
    
        }

}

