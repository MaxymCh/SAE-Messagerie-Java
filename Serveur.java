import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


class Serveur{
    private int compteur;
    private int port;
    public Socket socket;
    private ThreadAccepterClient tac;
    private HashMap<String, ArrayList<String>> listeSalon;
    public Serveur(int port){
        this.compteur = 0;
        this.port = port;
        this.listeSalon = new HashMap<>();
        this.listeSalon.put("Salon 1", new ArrayList<>());
        this.listeSalon.put("Salon 2", new ArrayList<>());
        this.listeSalon.put("Salon 3", new ArrayList<>());
        this.listeSalon.put("Salon 4", new ArrayList<>());
    }

    public HashSet<String> getListeSalon(){
        HashSet<String> ensembleSalon = new HashSet<>();
        for (String nomS : this.listeSalon.keySet()){
            ensembleSalon.add(nomS);
        }
        return ensembleSalon;
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
            if (session.getNomClient()!= null && session.getNomClient().equals(nom)){
                return false;
            }
        }
        return true;
    }
    public void changerSalon(Session session, String nouveauSallon){
        if (session.getSallonActuelle() != null){
            this.listeSalon.get(session.getSallonActuelle()).remove(session.getNomClient());
        }
        session.setSallonActuelle(nouveauSallon);
        this.listeSalon.get(nouveauSallon).add(session.getNomClient());
        System.out.println(this.listeSalon);



    }

    public void envoyerMessageSallon(Session sessionEnvoyer,String message){
        for (Session sessionExistante : this.tac.getSessions()){
            if(this.listeSalon.get(sessionEnvoyer.getSallonActuelle()).contains(sessionExistante.getNomClient())){
                if(!sessionExistante.getNomClient().equals(sessionEnvoyer.getNomClient())){
                    sessionExistante.envoyerMessageClient(sessionEnvoyer.getNomClient(),message);
                }
            }
        }


    }
}
