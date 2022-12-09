import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Session extends Thread {
    private Socket sock;
    private Client client;
    private Serveur serv;
    private DataInputStream dis;
    DataOutputStream dos;
    public Session( Socket sock, Serveur serv){
        this.sock = sock;
        this.serv = serv;
    }
    @Override
    public void run(){
        try{
            this.dos = new DataOutputStream(this.sock.getOutputStream());
            this.dos.writeUTF("Bonjour, bienvenue");
            /*
            
            System.out.println("1");
            
            System.out.println("1");
            ObjectOutputStream oos = new ObjectOutputStream(this.sock.getOutputStream());
            System.out.println("1");
            */
           
            ObjectInputStream ois = new ObjectInputStream(this.sock.getInputStream());
            this.client = (Client)ois.readObject();
            /*this.client = (Client)ob;*/
            choisirNom();
            System.out.println("Test ");

            String str;
            
            while(true){
                
                str = (String)dis.readUTF();
                System.out.println("message "+str);
                if (str.equals("/quit")){
                    break;
                }
            }
        }
        catch(Exception e){
            System.out.println("Erreur "+e);
        }
        
    }
    public Client getClient(){
        return this.client;
    }
    public void choisirNom(){
        try{
        this.dis = new DataInputStream(this.sock.getInputStream());
        Boolean nomValide = false;
        while(! nomValide){
            
            /*this.dos.writeUTF("Donner moi votre nom !");*/
            String nomChoisi = this.dis.readUTF();
            if(this.serv.nomEstLibre(nomChoisi)){
                nomValide = true;
                this.client.setNomClient(nomChoisi);
                this.dos.writeBoolean(true);
            }
            
            else{
                this.dos.writeBoolean(false);
            }
        }
    }
    catch(Exception e){System.out.println(e);}
}

    
}
