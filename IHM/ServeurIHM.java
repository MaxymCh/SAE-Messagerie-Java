import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


class ServeurIHM{
    private int port;
    public Socket socket;

    private HashMap<String, Salon> listeSalon;
    private HashMap<String, SessionIHM> dicoPseudoSession;
    private long tempCreationServeur;
    public ServeurIHM(int port){
        this.port = port;
        this.listeSalon = new HashMap<String, Salon>();

        List<String> mesSalon = Arrays.asList("Salon1","Salon2","Salon3","Salon4","Salon5","Salon6","Salon7","Salon8","Salon9","Salon10");
        for(String nomSal: mesSalon){
            this.listeSalon.put(nomSal, new Salon(nomSal));
        }
        this.tempCreationServeur = System.currentTimeMillis();
        this.dicoPseudoSession = new HashMap<>();
    }

    public Set<String> getListeSalon(){
        return this.listeSalon.keySet();
    }

    public List<String> getMessageDansSalon(String nomSallon){
        return this.listeSalon.get(nomSallon).getListeMessage();
    }

    public long getTempsDepuisCreation(){
        return System.currentTimeMillis() - this.tempCreationServeur;
    }

    public void retirerClientSalon(SessionIHM s){
        Boolean reussi = this.listeSalon.get(s.getSallonActuelle()).retirerClient(s);
        
    }

    public void ajouterClientSalon(SessionIHM s, String nomSallon){
        Boolean reussi = this.listeSalon.get(s.getSallonActuelle()).ajouterClient(s);
    }

    public synchronized void retirerSalon(String nomSallon, SessionIHM membre){
        synchronized(this.listeSalon){
            if((this.listeSalon.containsKey(nomSallon))){
                Salon salon = this.listeSalon.get(nomSallon);
                if(salon.getNombreMembre() == 0){
                    this.listeSalon.remove(nomSallon);
                }
                else if(salon.getNombreMembre() == 1 && salon.estPresent(membre)){
                    salon.retirerClient(membre);
                    this.listeSalon.remove(nomSallon);
                }
                
            }
        }

        
    }

    public synchronized void ajouterSalon(String nomSallon){
        synchronized(this.listeSalon){
            if(!this.listeSalon.containsKey(nomSallon)){
                this.listeSalon.put(nomSallon, new Salon(nomSallon));
            }
        }
        
    }

    public void ajouterSession(SessionIHM s, String nom){
        synchronized(this.dicoPseudoSession){
            this.dicoPseudoSession.put(nom, s);
        }
    }

    public void removeUtilisateur(SessionIHM utilisateur){
        if(utilisateur.getSallonActuelle() != null){
            this.listeSalon.get(utilisateur.getSallonActuelle()).retirerClient(utilisateur);
        }
        synchronized(this.dicoPseudoSession){
            this.dicoPseudoSession.remove(utilisateur.getNomClient());
        }
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
        return !ensembleClient.contains(nom);
    }

    public void changerSalon(SessionIHM session, String nouveauSallon){
        if (session.getSallonActuelle() != null){
            this.retirerClientSalon(session);
        }
        if(this.listeSalon.containsKey(nouveauSallon)){
            session.setSallonActuelle(nouveauSallon);
            this.ajouterClientSalon(session, nouveauSallon);
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
        this.listeSalon.get(sessionEnvoyer.getSallonActuelle()).envoyerMessageATous(sessionEnvoyer, date, messagePourAutres);
        
    }
    
    public void envoyerMessagePrive(SessionIHM sessionEnvoyer,String message, String destinataire){
        if(sessionEnvoyer.getNomClient().equals(destinataire)){
            sessionEnvoyer.envoyerMessageClientDeServeur("C'est vous !!!");
        }
        else if(!this.nomEstLibre(destinataire)){
            SessionIHM clientDestinataire = this.dicoPseudoSession.get(destinataire);
            clientDestinataire.envoyerMessageClientDeServeur("Message privé de "+sessionEnvoyer.getNomClient()+" : "+message);
        }
        else{
            sessionEnvoyer.envoyerMessageClientDeServeur("Le client "+destinataire+" n'existe pas");
        }

        


    }

    public void aujouterMessageDansUnSallon(String message, String nomSallon){
        this.listeSalon.get(nomSallon).ajouterMessage(message);
    }
}
