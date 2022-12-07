import java.net.ServerSocket;
import java.io.DataOutputStream;
import java.net.Socket;




public class Client {
    public Client(){

    }
    public void mainSession(){
        try{
            Socket s=new Socket("localhost",6666);
            DataOutputStream dout=new DataOutputStream(s.getOutputStream());
            dout.writeUTF("Hello Server");
            dout.flush();
            dout.close();
            s.close();
        }
        catch(Exception e){System.out.println(e);}
        }
}

