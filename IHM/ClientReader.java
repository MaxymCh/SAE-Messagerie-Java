import java.io.DataInputStream;
import java.net.Socket;

public class ClientReader extends Thread{
    private ClientIHM clientIHM;
    private Socket socket;
    private boolean clientLance;

    private AppClient appClient;

    public ClientReader( ClientIHM clientIHM, Socket socket, AppClient appClient){
        this.client = client;
        this.socket = socket;
        this.clientLance = true;
        this.appClient = appClient;

    }

    public void stop_thread(){
        this.clientLance = false;
    }
    public void run(){
        try{
            this.appClient.ajouterMessage();
            DataInputStream dis = new DataInputStream(this.socket.getInputStream());
            String mes;
            while(this.clientLance){
                mes = dis.readUTF();
                if(mes.equals("true")){
                    mes = dis.readUTF();
                    this.client.setNomClient(mes);
                    break;
                }
                else{
                    System.out.println(mes);
                }               
            }

            while(this.clientLance){
                mes = dis.readUTF();
                System.out.println(mes);
            }
        }
        catch(Exception e){
            System.out.println("Erreur : "+e);
        }

    }
}
