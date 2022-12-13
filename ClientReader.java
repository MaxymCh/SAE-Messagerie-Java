import java.io.BufferedReader;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientReader extends Thread{
    private Client client;
    private Socket socket;

    public ClientReader( Client client, Socket socket){
        this.client = client;
        this.socket = socket;

    }

    public void run(){
        try{
            DataInputStream dis = new DataInputStream(this.socket.getInputStream());
            while(true){
                String mes = dis.readUTF();
                System.out.println(mes);
                /*
                String response = reader.readLine();
                System.out.println(response	);*/
            }
        }
        catch(Exception e){
            System.out.println("Erreur : "+e);
        }

    }
}
