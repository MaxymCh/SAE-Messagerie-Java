import java.io.BufferedReader;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class ClientWriter extends Thread{

    private Client client;
    private Socket socket;
    private ObjectInputStream ois;
    private DataOutputStream dout;
    private PrintWriter writer;
    

    public ClientWriter(Client client, Socket socket){
        this.client = client;
        this.socket = socket;
        try{
        /*this.dout=new DataOutputStream(this.socket.getOutputStream());*/
        OutputStream output = socket.getOutputStream();
        this.writer = new PrintWriter(output, true);

        }
        catch(Exception e){
            System.out.println("Erreur : "+e);
        }
    
    }

    public void run(){
        Scanner sc = new Scanner(System.in);
        try{
            Boolean nomEstChoisi = false;
            
            
            String name = null;
            while(this.client.getNomClient() == null){
                System.out.println("Donner moi votre nom !");
                name = sc.nextLine();
                this.writer.println(name);
                Thread.sleep(5000000);
            
            }
                
            
        while(true){
            System.out.println("Bienvenue dans la messagerie vous pouvez ecrire vos messages /salon changer salon");
            String message = sc.nextLine();
            this.writer.println(message);
            if (message.equals("/quit")){
                System.out.println("Au revoir "+this.client.getNomClient());
                break;
            }
            else if (message.equals("/salon")){
                /*changerSalon(ois, sc, dout);*/
        }
    }
}
    catch(Exception e){System.out.println(e);}
}

    
}

