import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ThreadAccepterClient extends Thread{
    private ServerSocket ss;
    private HashMap<String, Session> dicoPseudoSession;
    private Serveur serv;
    public ThreadAccepterClient(ServerSocket ss, Serveur serv){
        this.ss = ss;
        this.dicoPseudoSession = new HashMap<>();
        this.serv  = serv;
    }
    @Override
    public void run(){
        try{

            while (true){
                Socket sock = ss.accept();
                Session cl = new Session(sock, this.serv);
                cl.start();
            }
        } 
        catch(Exception e){
            System.out.println("Erreur "+e);
        }
    }

    public HashMap<String, Session> getDicoSession(){
        return this.dicoPseudoSession;
    }

    public void addSessions(Session s, String nom){
        this.dicoPseudoSession.put(nom, s);
    }

    public void removeSessions(String nom){
        this.dicoPseudoSession.remove(nom);
    }
}