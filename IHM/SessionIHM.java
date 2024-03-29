import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SessionIHM extends Thread {
    private Socket sock;
    private String nomClient;
    private String salonActuelle;
    private ServeurIHM serv;
    private DataInputStream dis;
    private DataOutputStream dos;
    public SessionIHM( Socket sock, ServeurIHM serv){
        this.sock = sock;
        this.serv = serv;
    }
    @Override
    public void run(){
        try{
            this.dis = new DataInputStream(this.sock.getInputStream());
            this.dos = new DataOutputStream(this.sock.getOutputStream());
            this.envoyerMessageClientDeServeur("Bienvenue dans notre messagerie");
            choisirNom(this.dis);
            this.serv.ajouterSession(this, nomClient);
            String str;
            this.envoyerMessageClientDeServeur("Vous devez tout d'abord choisir un salon");
            this.envoyerMessageClientDeServeur("/help pour voir la liste des commandes");
            while(true){
                str = (String)dis.readUTF();
                if(str.length()>=1){
                    if(str.substring(0, 1).equals("/")){
                        if (str.equals("/help")){
                            this.envoyerMessageClientDeServeur(this.getListeCommande());
                        }
                        else if (str.equals("/quit")){
                            this.serv.removeUtilisateur(this);
                            this.dos.writeUTF("quit:tout");
                            this.dos.flush();
                            break;
                        }
                        
                        else if (str.equals("/salon")){
                            this.envoyerListeSalonPourClient();
                        }

                        else if (str.equals("/demandeMessage")){
                            if(this.salonActuelle!=null){
                                this.envoyerListeMessageDansSalonPourClient();
                            }
                            
                        }
                        

                        else if(str.length()>=5 && str.substring(0,5).equals("/join")){
                            try {
                                String nomSalon =str.split(" ")[1];
                                this.serv.changerSalon(this, nomSalon);
                            } catch (Exception e) {
                                this.envoyerMessageClientDeServeur("La commande est invalide \n Pour rappel /join Salon4 pour rejoindre le salon Salon4");
                            }
                        }

                        else if(str.length()>=3 && str.substring(0,4).equals("/add")){
                            try {
                                String nomSalon =str.split(" ")[1];
                                this.serv.ajouterSalon(nomSalon);
                            } catch (Exception e) {
                                this.envoyerMessageClientDeServeur("La commande est invalide");
                            }
                        }

                        else if(str.length()>=3 && str.substring(0,4).equals("/sup")){
                            try {
                                String nomSalon =str.split(" ")[1];
                                this.serv.retirerSalon(nomSalon, this);
                            } catch (Exception e) {
                                this.envoyerMessageClientDeServeur("La commande est invalide");
                            }
                        }
                        
                        else if(str.equals("/nbuser")){
                            int nombreUser = this.serv.getNombreUser();
                            this.envoyerMessageClientDeServeur("Il y a actuellement "+String.valueOf(nombreUser)+" client enregistré");
                            
                        }
                        else if(str.equals("/uptime")){
                            if(this.salonActuelle != null){
                                long tempsCreation = this.serv.getTempsCreationSalon(this.salonActuelle)/1000;
                                this.envoyerMessageClientDeServeur("Le salon est lancé depuis  "+String.valueOf(tempsCreation)+" secondes");
                            }
                            
                            
                            
                        }

                        else if(str.equals("/users")){
                            this.envoyerListeUserPourClient();   
                        }
                        else{
                            this.envoyerMessageClientDeServeur("La commande est introuvable /help");
                        }
                        
      
                    }
                    else if(str.substring(0, 1).equals("@")){
                        try{
                            ArrayList<String> messageSepare = new ArrayList<String>(Arrays.asList(str.split(" ")));
                            String destinataire = messageSepare.get(0).substring(1,  messageSepare.get(0).length());
                            messageSepare.remove(0);
                            String messageFinal = String.join(" ", messageSepare);
                            this.serv.envoyerMessagePrive(this, messageFinal, destinataire);
                        }
                        catch(Exception e){
                            System.out.println(e);
                        }
                        
                    }
                    else if(this.salonActuelle != null){
                        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        Date date = new Date();
                        String laDate = s.format(date);
                        this.serv.envoyerMessageSallon(this, str,laDate);
                    }
                    else{
                        this.envoyerMessageClientDeServeur("Essayer tout d'abord de rejoindre un salon /help");
                    }

                    
                    
                }
            }
        }
        catch(Exception e){
            System.out.println("Erreur "+e);
        }
        
    }
    public String getNomClient(){
        return this.nomClient;
    }
    public void choisirNom(DataInputStream dis){
        try{
        
            Boolean nomValide = false;
            while(!nomValide){
                this.envoyerMessageClientDeServeur("Donner moi votre nom !");
                
                String nomChoisi = this.dis.readUTF();
                if (nomChoisi.substring(0, 1).equals("/")){
                    if (nomChoisi.equals("/quit")){
                        this.envoyerMessageClientDeServeur("tout", "quit");
                    }
                    else{
                        this.envoyerMessageClientDeServeur("Nom invalide");
                    }
                }
                else if(this.serv.nomEstLibre(nomChoisi)){
                    nomValide = true;
                    this.envoyerMessageClientDeServeur("true", "name");
                    this.envoyerMessageClientDeServeur(nomChoisi);
                    this.envoyerMessageClientDeServeur(nomChoisi+"!!! Ca sonne bien !");
                    this.nomClient = nomChoisi;
                }
                
                else{
                    this.envoyerMessageClientDeServeur("Malheureusement "+nomChoisi+" est déjà utilisé ");
                }
            }
        }
        catch(Exception e){System.out.println(e);}
    }


    public String getSallonActuelle(){
        return this.salonActuelle;
    }

    public void setSallonActuelle(String nouveauSallon){
        this.salonActuelle = nouveauSallon;
    }


    public void envoyerMessageClientDeServeur(String mes){
        try {                
            this.dos.writeUTF("message:"+mes);
            this.dos.flush();
            
        }
         catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void envoyerMessageClientDeServeur(String message, String entete){
        try {
            this.dos.writeUTF(entete+":"+message);
            this.dos.flush();
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    public void envoyerListeSalonPourClient(){
        try {

            Comparator<String> salonComparator = new Comparator<String>() {
                @Override
                public int compare(String s1, String s2) {
                    if (s1.startsWith("Salon") && s2.startsWith("Salon")) {
                        // Extraction des numéros de salon
                        try{
                            int num1 = Integer.parseInt(s1.replaceAll("\\D", ""));
                        int num2 = Integer.parseInt(s2.replaceAll("\\D", ""));
                        return Integer.compare(num1, num2);
                        }
                        catch(NumberFormatException e){
                            return s1.compareTo(s2);
                        }
                        
                    } else {
                        return s1.compareTo(s2);
                    }
                }
            };

            
            
            Set<String> listeSalon = this.serv.getListeSalon();
            TreeSet<String> sortedSet = new TreeSet<>(salonComparator);
            sortedSet.addAll(listeSalon);
            String sortedSalon = String.join(", ", sortedSet);
            /*strListeSalon = strListeSalon.substring(1,strListeSalon.length()-1);*/
            this.dos.writeUTF("listeSalon:"+sortedSalon);
            this.dos.flush();
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void envoyerListeUserPourClient(){
        try {
            Set<String> listeUsers = this.serv.getEnsembleClient();
            String strListeUsers = listeUsers.toString();
            strListeUsers = strListeUsers.substring(1,strListeUsers.length()-1);
            this.dos.writeUTF("listeUsers:"+strListeUsers);
            this.dos.flush();
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void envoyerListeMessageDansSalonPourClient(){
        try {
            List<String> listeMessageDansUnSalon = this.serv.getMessageDansSalon(this.salonActuelle);
            String strMessages = "";
            for(String message : listeMessageDansUnSalon){
                strMessages += message+",:,;,";
            }
            if(listeMessageDansUnSalon.size()>0){strMessages = strMessages.substring(0,strMessages.length()-5);}
            
            this.dos.writeUTF("listeMessages:"+strMessages);
            this.dos.flush();
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }



    
    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o instanceof SessionIHM){
            SessionIHM s2 = (SessionIHM)o;
            if(this.getNomClient().equals(s2.getNomClient())){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode(){
        return this.nomClient.hashCode();
    }


    public String getListeCommande(){
        String res = "\n Voici la liste des commandes : \n";
        res += "- /quit :Quitter l'application \n";
        res += "- /join nomSalon :Rejoindre un salon ou cliquer sur un des salons \n";
        res += "- /nbuser :Connaitre le nombre de personnes enregistrés\n";

        res += "- /uptime :Connaitre depuis combien de temps le salon est lancé \n";
        res += "- @userName :Envoyé un message privé à userName \n";

        res += "- /users : Bouton a droite pour afficher la liste des utilisateurs \n";
        res += "- /salon : Bouton a gauche pour afficher la liste des salons \n";

        return res;
    }

}
