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
    private HashSet<String> listeClient;
    private long tempCreationServeur;
    public Serveur(int port){
        this.compteur = 0;
        this.port = port;
        this.listeSalon = new HashMap<>();
        this.listeSalon.put("Salon 1", new ArrayList<>());
        this.listeSalon.put("Salon 2", new ArrayList<>());
        this.listeSalon.put("Salon 3", new ArrayList<>());
        this.listeSalon.put("Salon 4", new ArrayList<>());
        this.tempCreationServeur = System.currentTimeMillis();
        this.listeClient = new HashSet<>();
    }

    public Set<String> getListeSalon(){
        return this.listeSalon.keySet();
    }

    public long getTempsDepuisCreation(){
        return System.currentTimeMillis() - this.tempCreationServeur;
    }
    public void retirerClientSalon(Session s){
        this.listeSalon.get(s.getSallonActuelle()).remove(s.getNomClient());
    }

    public void ajouterSession(Session s){
        this.tac.addSessions(s);
        this.listeClient.add(s.getNomClient());
    }

    public void removeSession(Session s){
        this.tac.removeSessions(s);
        this.listeClient.remove(s.getNomClient());
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
        if(this.listeSalon.containsKey(nouveauSallon)){
            session.setSallonActuelle(nouveauSallon);
            this.listeSalon.get(nouveauSallon).add(session.getNomClient());
            System.out.println(this.listeSalon);
            session.envoyerMessageClient("Serveur", "Bienvenue dans le sallon "+nouveauSallon);
        }
        else{
            session.envoyerMessageClient("Serveur", "Le salon n'existe pas");
        }
    
    }

    public int getNombreUser(){
        return this.tac.getSessions().size();
    }

    public String getListeClient(){
        String listeClient = this.listeClient.toString();
        return listeClient;
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
    
    public void envoyerMessage(Session sessionEnvoyer,String message){
        for (Session sessionExistante : this.tac.getSessions()){
            if(!sessionExistante.getNomClient().equals(sessionEnvoyer.getNomClient())){
                sessionExistante.envoyerMessageClient(sessionEnvoyer.getNomClient(),message);
            }
        }


    }
}
