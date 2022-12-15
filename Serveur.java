import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


class Serveur{
    private int port;
    public Socket socket;
    private ThreadAccepterClient tac;
    private HashMap<String, ArrayList<String>> listeSalon;
    private long tempCreationServeur;
    public Serveur(int port){
        this.port = port;
        this.listeSalon = new HashMap<>();
        this.listeSalon.put("Salon1", new ArrayList<>());
        this.listeSalon.put("Salon2", new ArrayList<>());
        this.listeSalon.put("Salon3", new ArrayList<>());
        this.listeSalon.put("Salon4", new ArrayList<>());
        this.tempCreationServeur = System.currentTimeMillis();
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

    public void ajouterSession(Session s, String nom){
        this.tac.addSessions(s, nom);
    }

    public void removeSession(String nom){
        this.tac.removeSessions(nom);
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
        Set<String> ensembleClient = this.tac.getDicoSession().keySet();
        if(!ensembleClient.contains(nom)){
            return true;
        }
        return false;
    }
    public void changerSalon(Session session, String nouveauSallon){
        if (session.getSallonActuelle() != null){
            this.listeSalon.get(session.getSallonActuelle()).remove(session.getNomClient());
        }
        if(this.listeSalon.containsKey(nouveauSallon)){
            session.setSallonActuelle(nouveauSallon);
            this.listeSalon.get(nouveauSallon).add(session.getNomClient());
            session.envoyerMessageClientDeServeur("Bienvenue dans le sallon "+nouveauSallon);
        }
        else{
            session.envoyerMessageClientDeServeur("Le salon n'existe pas");
        }
    
    }

    public int getNombreUser(){
        return this.tac.getDicoSession().size();
    }

    public String getListeClient(){
        Set<String> ensembleClient = this.tac.getDicoSession().keySet();
        return ensembleClient.toString();
    }
    public void envoyerMessageSallon(Session sessionEnvoyer,String message, String date){
        for(String pseudoSallon : this.listeSalon.get(sessionEnvoyer.getSallonActuelle())){
            Session clientDestinataire = this.tac.getDicoSession().get(pseudoSallon);
            if(!clientDestinataire.equals(sessionEnvoyer)){
                clientDestinataire.envoyerMessageClient(sessionEnvoyer.getNomClient(), message, date);
            }
        }


    }
    
    public void envoyerMessagePrive(Session sessionEnvoyer,String message, String destinataire){
        if(sessionEnvoyer.getNomClient().equals(destinataire)){
            sessionEnvoyer.envoyerMessageClientDeServeur("C'est vous !!!");
        }
        else if(!this.nomEstLibre(destinataire)){
            Session clientDestinataire = this.tac.getDicoSession().get(destinataire);
            clientDestinataire.envoyerMessagePriveClient(sessionEnvoyer.getNomClient(), message);
        }
        else{
            sessionEnvoyer.envoyerMessageClientDeServeur("Le client "+destinataire+" n'existe pas");
        }

        


    }
}
