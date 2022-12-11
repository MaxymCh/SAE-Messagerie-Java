import java.io.BufferedReader;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientReader extends Thread{
    private Client client;
    private Socket socket;
    private boolean estLancer;
    private BufferedReader reader;
    private ObjectInputStream ois;
    

    public ClientReader( Client client, Socket socket){
        this.client = client;
        this.socket = socket;
        this.estLancer = true;
        try{
        
        }
        catch(Exception e){
            System.out.println("Erreur : "+e);
        }
    
    }

    public void run(){
        try{
            DataInputStream dis = new DataInputStream(this.socket.getInputStream());
            Boolean nomEstChoisi = (Boolean) dis.readBoolean();
            String name = dis.readUTF();
            if(nomEstChoisi){
                System.out.println(name+"!!! Ca sonne bien !"); 
                this.client.setNomFinal();
            }
            else{
                System.out.println("malheureusement "+name+" est déjà utilisé");
            }
            /*DataInputStream dis = new DataInputStream(this.socket.getInputStream());*/
            while(estLancer){
                /*String mes = dis.readUTF();
                System.out.println(mes);*/
                System.out.println("1");
                this.ois = new ObjectInputStream(this.socket.getInputStream());
                System.out.println("1");
                Object response = (Object)this.ois.readObject();
                System.out.println("1");
                System.out.println(response);
                System.out.println("kk");
                /* try {
            HashSet<String> listeSal = (HashSet<String>)ois.readObject();
            System.out.println(listeSal);
            String nomSallon = sc.nextLine();
            dout.writeUTF(nomSallon);
            System.out.println("Bienvenue dans le sallon "+nomSallon);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } */
            }
        }
        catch(Exception e){
            System.out.println("Erreur : "+e);
        }

    }
}
