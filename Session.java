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

            String str;
            
            while(true){
                
                str = (String)dis.readUTF();
                System.out.println("message "+str);
                if (str.equals("/quit")){
                    break;
                }
                
                else if (str.equals("/salon")){
                    changerSalon(dos);
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
    
}
