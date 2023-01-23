import java.io.DataOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;






public class ClientIHM{
    private String nomClient;
    private Socket s;
    private DataOutputStream dout;
    private Scanner sc;

    private Thread cr;

    private AppClient appClient;
    

    public ClientIHM(AppClient appClient) throws ConnectException{
        try{
            this.s=new Socket("localhost",6666);
            this.dout=new DataOutputStream(s.getOutputStream());
            this.appClient = appClient;
        }

        catch(Exception e){
            System.out.println(e);
            throw new ConnectException();
        }
        }


    public Socket getSocket(){
        return this.s;
    }

    public String getNomClient(){
        return this.nomClient;
    }

    public void setNomClient(String nomC){
        this.nomClient = nomC;
    }
    public Thread creeClientReader(){
        ClientReaderIHM crIHM = new ClientReaderIHM(this, this.s, appClient);
        this.cr =new Thread(crIHM);
        cr.start();
        return this.cr;
    }

    public void envoyerMessage(String message){
        try{
            dout.writeUTF(message);
            dout.flush();
            if (message.equals("/quit")){
                
            }
        }
        catch(Exception e){System.out.println(e);}
    }
    





        public void choisirNom(){
            try{
               
                String name = this.sc.nextLine();
                this.dout.writeUTF(name);
                this.dout.flush();
           }
            catch(Exception e){System.out.println(e);}
        }
}


