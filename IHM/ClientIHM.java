import java.io.DataOutputStream;

import java.net.Socket;
import java.util.Scanner;




public class ClientIHM{
    private String nomClient;
    private Socket s;
    private DataOutputStream dout;
    private Scanner sc;

    private ClientReader cr;

    private AppClient appClient;

    public ClientIHM(AppClient appClient){
        try{
            this.s=new Socket("localhost",6666);
            this.appClient = appClient;
        }

        catch(Exception e){System.out.println(e);}
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
    public ClientReader creeClientReader(){
        this.cr = new ClientReader(this, this.s, this.appClient);
        return this.cr;
    }

    public void mainSession(){
        
        try{
            
            this.dout=new DataOutputStream(s.getOutputStream());
            this.sc = new Scanner(System.in);
            
            cr.start();
            while(true){
                String message = this.sc.nextLine();
                dout.writeUTF(message);
                dout.flush();
                if (message.equals("/quit")){
                    cr.stop_thread();
                    break;
                }


                
            }
            Thread.sleep(1000);
            dout.close();
            s.close();
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


