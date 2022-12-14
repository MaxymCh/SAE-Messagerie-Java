import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Session extends Thread {
    private Socket sock;
    private String nomClient;
    private String salonActuelle;
    private Serveur serv;
    private DataInputStream dis;
    private DataOutputStream dos;
    public Session( Socket sock, Serveur serv){
        this.sock = sock;
        this.serv = serv;
    }
    @Override
    public void run(){
        try{
            this.dis = new DataInputStream(this.sock.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(this.sock.getOutputStream());
            this.dos = new DataOutputStream(this.sock.getOutputStream());
            this.dos.writeUTF("Bonjour, bienvenue");
            /*
            
            System.out.println("1");
            
            System.out.println("1");
            ObjectOutputStream oos = new ObjectOutputStream(this.sock.getOutputStream());
            System.out.println("1");
            */
           /*
            ObjectInputStream ois = new ObjectInputStream(this.sock.getInputStream());
            this.client = (Client)ois.readObject();
            this.client.setNomClient("dd");
            */
            /*this.client = (Client)ob;*/
            choisirNom(this.dis);
            this.serv.ajouterSession(this);
            String str;
            changerSalon(dos);
            
            while(true){
                
                str = (String)dis.readUTF();
                System.out.println("message "+str);
                if (str.equals("/quit")){
                    this.dos.writeUTF("Merci et à bientot");
                    this.serv.removeSession(this);
                    if(this.salonActuelle!=null){
                        this.serv.retirerClientSalon(this);
                    }
                    break;
                }
                
                else if (str.equals("/salon")){
                    changerSalon(dos);
                }
                else if(str.equals("/nbuser")){
                    int nombreUser = this.serv.getNombreUser();
                    this.dos.writeUTF("Il y a actuellement "+String.valueOf(nombreUser)+" client enregistré");
                    
                }
                else if(str.equals("/uptime")){
                    long tempsCreation = this.serv.getTempsDepuisCreation()/1000;
                    this.dos.writeUTF("Le serveur est lancé depuis  "+String.valueOf(tempsCreation)+" secondes");
                    
                }

                else if(str.equals("/users")){
                    String listeUser = this.serv.getListeClient();
                    this.dos.writeUTF("La liste des clients actuellement connecté est "+listeUser);
                    
                }
                
                
                
                else{
                    this.serv.envoyerMessageSallon(this, str);
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
        while(! nomValide){
            /*this.dos.writeUTF("Donner moi votre nom !");*/
            String nomChoisi = this.dis.readUTF();
            if(this.serv.nomEstLibre(nomChoisi)){
                nomValide = true;
                /*this.client.setNomClient(nomChoisi);*/
                this.dos.writeBoolean(true);
                this.nomClient = nomChoisi;
            }
            
            else{
                this.dos.writeBoolean(false);
            }
        }
    }
    catch(Exception e){System.out.println(e);}
}

    public void changerSalon(DataOutputStream dos){
        try {
            dos.writeUTF("Veuillez choisir un salon parmis la liste suivante : ");
            dos.writeUTF(this.serv.getListeSalon().toString());
            String nomSallon = this.dis.readUTF();
            this.serv.changerSalon(this, nomSallon);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getSallonActuelle(){
        return this.salonActuelle;
    }

    public void setSallonActuelle(String nouveauSallon){
        this.salonActuelle = nouveauSallon;
    }

    public void envoyerMessageClient(String nomEnvoyer, String mes){
        try{
            PrintWriter writer;
            try {
                DataOutputStream output = new DataOutputStream(this.sock.getOutputStream());
                output.writeUTF(mes);
                /*
                writer = new PrintWriter(output, true);
                writer.println(mes);*/
            } catch (IOException ex) {
                System.out.println("Error getting output stream: " + ex.getMessage());
                ex.printStackTrace();
            }
            /*
            this.dos.writeUTF(nomEnvoyer+" "+mes);*/
        }
        catch(Exception e){
            System.out.println("Erreur : "+e);
        }
        
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o instanceof Session){
            Session s2 = (Session)o;
            if(this.getNomClient().equals(s2.getNomClient())){
                return true;
            }
        }
        return false;
    }

}
