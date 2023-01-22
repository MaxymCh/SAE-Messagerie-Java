import java.io.DataOutputStream;

import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.CyclicBarrier;




public class Client{
    private String nomClient;
    private Socket s;
    private DataOutputStream dout;
    private Scanner sc;
    private boolean continuer = true;

    public Socket getSocket(){
        return this.s;
    }

    public String getNomClient(){
        return this.nomClient;
    }

    public void setNomClient(String nomC){
        this.nomClient = nomC;
    }
    public void mainSession(){
        
        try{
            this.sc = new Scanner(System.in);
            System.out.println("Saisissez votre adresse ip ou localhost");
            String adresseIP = this.sc.nextLine();
            try{
                this.s=new Socket(adresseIP,6666);
            }
            catch(Exception e){
                System.out.println("Il y a eu une erreur dans votre adresse ip vous etes connecter en localhost");
                this.s=new Socket("localhost",6666);
            }
            this.dout=new DataOutputStream(s.getOutputStream());
            CyclicBarrier barrier = new CyclicBarrier(2);
            ClientReader cr = new ClientReader(this, s, barrier);
            cr.start();
            while(this.continuer){
                String message = this.sc.nextLine();
                dout.writeUTF(message);
                dout.flush();
                if (message.equals("/quit")){
                    barrier.await();
                }


                
            }
            cr.stop_thread();
            Thread.sleep(1000);
            dout.close();
            s.close();
        }

        catch(Exception e){System.out.println(e);}
        }


        public void quitter(){
            this.continuer = false;
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


