import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Salon {
    private String nomSalon;
    private List<String> listeMessages;
    private Set<SessionIHM> listeMembre;
    private long tempCreationSalon;

    public Salon(String nomSalon){
        this.nomSalon = nomSalon;
        this.listeMessages = new ArrayList<>();
        this.listeMembre = new HashSet<>();
        this.tempCreationSalon = System.currentTimeMillis();
    }

    public synchronized boolean retirerClient(SessionIHM s){
        if(this.listeMembre.contains(s)){
            this.listeMembre.remove(s);
            return true;
        }
        return false;
        
        
    }

    public synchronized boolean ajouterClient(SessionIHM s){
        if(!this.listeMembre.contains(s)){
            this.listeMembre.add(s);
            return true;
        }
        return false;
        
    }

    public int getNombreMembre(){
        return this.listeMembre.size();
    }
    public synchronized void ajouterMessage(String message){
        this.listeMessages.add(message);
    }

    public long getTempsDepuisCreation(){
        return System.currentTimeMillis() - this.tempCreationSalon;
    }

    public List<String> getListeMessage(){
        return this.listeMessages;
    }

    public String getNomSalon(){
        return this.nomSalon;
    }

    public boolean estPresent(SessionIHM membre){
        return this.listeMembre.contains(membre);
    }

    public void envoyerMessageATous(SessionIHM sessionEnvoyeur, String date, String message){
        for(SessionIHM membre : this.listeMembre){
            if(!membre.equals(sessionEnvoyeur)){
                membre.envoyerMessageClientDeServeur(message);
            }
        }

    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o instanceof Salon){
            Salon s2 = (Salon)o;
            if(this.nomSalon.equals(s2.nomSalon)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode(){
        return this.nomSalon.hashCode();
    }
}
