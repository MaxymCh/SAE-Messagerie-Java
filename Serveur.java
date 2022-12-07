import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;


class Serveur{
    private int compteur;
    private int port;
    public Socket socket;
    public Serveur(int port){
        this.compteur = 0;
        this.port = port;
    }

    public void mainServer(){
        try{
            ServerSocket ss = new ServerSocket(this.port);
            while (true){
                Socket sock = ss.accept();
                Session cl = new Session(this, sock);
                cl.start();
               
            }
            
        }
        catch(Exception e){
            System.out.println("Erreur "+e);
        }


        
    }
    
}
