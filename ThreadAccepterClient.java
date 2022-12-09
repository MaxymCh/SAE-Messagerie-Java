import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ThreadAccepterClient extends Thread{
    private ServerSocket ss;
    private List<Session> sessions;
    private Serveur serv;
    public ThreadAccepterClient(ServerSocket ss, Serveur serv){
        this.ss = ss;
        this.sessions = new ArrayList<>();
        this.serv  = serv;
    }
    @Override
    public void run(){
        try{

            while (true){
                Socket sock = ss.accept();
                Session cl = new Session(sock, this.serv);
                cl.start();
                this.sessions.add(cl);
            }
        } 
        catch(Exception e){
            System.out.println("Erreur "+e);
        }
    }

    public List<Session> getSessions(){
        return this.sessions;
    }
}