import java.net.Socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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
    private BufferedReader reader;
    public Session( Socket sock, Serveur serv){
        this.sock = sock;
        this.serv = serv;
        try{
            InputStream input = sock.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));

            }
            catch(Exception e){
                System.out.println("Erreur : "+e);
            }
    }

    @Override
    public void run(){
        try{
            /*
            this.dis = new DataInputStream(this.sock.getInputStream());
            
            this.dos = new DataOutputStream(this.sock.getOutputStream());
            */
            ObjectOutputStream oos = new ObjectOutputStream(this.sock.getOutputStream());
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
                
                str = this.reader.readLine();
                System.out.println("message "+str);
                if (str.equals("/quit")){
                    break;
                }
                else if (str.equals("/salon")){
                    changerSalon(oos);
                }
                else{

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
                this.dos.writeUTF(nomChoisi);
                this.nomClient = nomChoisi;
            }
            
            else{
                this.dos.writeBoolean(false);
                this.dos.writeUTF(nomChoisi);
            }
        }
    }
    catch(Exception e){System.out.println(e);}
}

    public void changerSalon(ObjectOutputStream oos){
        
        try {
            oos.writeObject(this.serv.getListeSalon());
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
                OutputStream output = this.sock.getOutputStream();
                writer = new PrintWriter(output, true);
                writer.println(mes);
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
