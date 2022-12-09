import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;


class Serveur{
    private int compteur;
    private int port;
    public Socket socket;
    private ThreadAccepterClient tac;
    public Serveur(int port){
        this.compteur = 0;
        this.port = port;
    }

    public void mainServer(){
        try{
            ServerSocket ss = new ServerSocket(this.port);
            this.tac = new ThreadAccepterClient(ss, this);
            this.tac.start();
            
        }
        catch(Exception e){
            System.out.println("Erreur "+e);
        }
    }
    public boolean nomEstLibre(String nom){
        for (Session session : this.tac.getSessions()){
            if (session.getClient().getNomClient()!= null && session.getClient().getNomClient().equals(nom)){
                return false;
            }
        }
        return true;
    }
    
}
