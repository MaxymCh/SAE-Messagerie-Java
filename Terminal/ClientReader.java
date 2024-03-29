import java.io.DataInputStream;
import java.net.Socket;
import java.util.concurrent.CyclicBarrier;

public class ClientReader extends Thread{
    private Client client;
    private Socket socket;
    private boolean clientLance;
    private CyclicBarrier barrier;

    public ClientReader( Client client, Socket socket, CyclicBarrier barrier){
        this.client = client;
        this.socket = socket;
        this.clientLance = true;
        this.barrier = barrier;

    }

    public void stop_thread(){
        this.clientLance = false;
    }
    public void run(){
        try{
            DataInputStream dis = new DataInputStream(this.socket.getInputStream());
            String mes;
            while(this.clientLance){
                mes = dis.readUTF();
                if(mes.equals("true")){
                    mes = dis.readUTF();
                    this.client.setNomClient(mes);
                    break;
                }
                else{
                    System.out.println(mes);
                }           
            }

            while(this.clientLance){
                mes = dis.readUTF();
                if(mes.equals("servquit")){
                    mes = dis.readUTF();
                    System.out.println(mes);
                    this.client.quitter();
                    this.barrier.await();
                    break;
                }
                else if(mes.equals("servquitsalon")){
                    mes = dis.readUTF();
                    this.barrier.await();
                }
                
                System.out.println(mes);
            }
        }
        catch(Exception e){
            System.out.println("Erreur : "+e);
        }

    }
}
