import java.io.DataInputStream;
import java.net.Socket;

public class ClientReader extends Thread{
    private Client client;
    private Socket socket;
    private boolean clientLance;

    private IHMClient ihmClient;

    public ClientReader( Client client, Socket socket, IHMClient ihmClient){
        this.client = client;
        this.socket = socket;
        this.clientLance = true;
        this.ihmClient = ihmClient;

    }

    public void stop_thread(){
        this.clientLance = false;
    }
    public void run(){
        try{
            this.ihmClient.ajouterMessage();
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
