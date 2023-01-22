import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


class ServeurIHM{
    private int port;
    public Socket socket;
    private HashMap<String, ArrayList<String>> listeSalon;
    private HashMap<String, ArrayList<String>> listeMessageSalon;
    private HashMap<String, SessionIHM> dicoPseudoSession;
    private long tempCreationServeur;
    public ServeurIHM(int port){
        this.port = port;
        this.listeSalon = new HashMap<>();
        this.listeMessageSalon = new HashMap<>();

        List<String> mesSalon = Arrays.asList("Salon1","Salon2","Salon3","Salon4","Salon5","Salon6","Salon7","Salon8","Salon9","Salon10");
        for(String nomSal: mesSalon){
            this.listeSalon.put(nomSal, new ArrayList<>());
            this.listeMessageSalon.put(nomSal, new ArrayList<>());
        }
        this.tempCreationServeur = System.currentTimeMillis();
        this.dicoPseudoSession = new HashMap<>();
    }

    public Set<String> getListeSalon(){
        return this.listeSalon.keySet();
    }

    public List<String> getMessageDansSalon(String nomSallon){
        return this.listeMessageSalon.get(nomSallon);
    }

    public long getTempsDepuisCreation(){
        return System.currentTimeMillis() - this.tempCreationServeur;
    }
    public void retirerClientSalon(SessionIHM s){
        this.listeSalon.get(s.getSallonActuelle()).remove(s.getNomClient());
    }

    public void ajouterSession(SessionIHM s, String nom){
        this.dicoPseudoSession.put(nom, s);
    }

    public void removeSession(String nom){
        this.dicoPseudoSession.remove(nom);
    }

    public void mainServer(){
        try{
            ServerSocket ss = new ServerSocket(this.port);
            while (true){
                Socket sock = ss.accept();
                SessionIHM cl = new SessionIHM(sock, this);
                cl.start();
            }
            
        }
        catch(Exception e){
            System.out.println("Erreur "+e);
        }
    }
    public boolean nomEstLibre(String nom){
        Set<String> ensembleClient = this.dicoPseudoSession.keySet();
        if(!ensembleClient.contains(nom)){
            return true;
        }
        return false;
    }
    public void changerSalon(SessionIHM session, String nouveauSallon){
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
        return this.dicoPseudoSession.size();
    }

    public String getListeClient(){
        Set<String> ensembleClient = this.dicoPseudoSession.keySet();
        return ensembleClient.toString();
    }
    public void envoyerMessageSallon(SessionIHM sessionEnvoyer,String message, String date){
        String messagePourMoi = date+ " de Moi : "+message;
        sessionEnvoyer.envoyerMessageClientDeServeur(messagePourMoi);
        String messagePourAutres = date+ " de "+sessionEnvoyer.getNomClient()+" : "+message;
        aujouterMessageDansUnSallon(messagePourAutres, sessionEnvoyer.getSallonActuelle());
        for(String pseudoSallon : this.listeSalon.get(sessionEnvoyer.getSallonActuelle())){
            SessionIHM clientDestinataire = this.dicoPseudoSession.get(pseudoSallon);
            if(!clientDestinataire.equals(sessionEnvoyer)){
                clientDestinataire.envoyerMessageClientDeServeur(messagePourAutres);
            }
        }
        System.out.println(this.listeMessageSalon);
        
    }
    
    public void envoyerMessagePrive(SessionIHM sessionEnvoyer,String message, String destinataire){
        if(sessionEnvoyer.getNomClient().equals(destinataire)){
            sessionEnvoyer.envoyerMessageClientDeServeur("C'est vous !!!");
        }
        else if(!this.nomEstLibre(destinataire)){
            SessionIHM clientDestinataire = this.dicoPseudoSession.get(destinataire);
            clientDestinataire.envoyerMessagePriveClient(sessionEnvoyer.getNomClient(), message);
        }
        else{
            sessionEnvoyer.envoyerMessageClientDeServeur("Le client "+destinataire+" n'existe pas");
        }

        


    }

    public synchronized void aujouterMessageDansUnSallon(String message, String nomSallon){
        this.listeMessageSalon.get(nomSallon).add(message);
    }
}
