import java.net.Socket;
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Session extends Thread {
    private Serveur serv;
    private Socket sock;
    public Session(Serveur serv, Socket sock){
        this.sock = sock;
        this.serv = serv;
    }
    @Override
    public void run(){
        try{
            DataInputStream dis = new DataInputStream(this.sock.getInputStream());
            String str = (String)dis.readUTF();
            System.out.println("message "+str);
        }
        catch(Exception e){
            System.out.println("Erreur "+e);
        }
        
    }
    
}
