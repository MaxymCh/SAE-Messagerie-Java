import java.net.ServerSocket;
import java.io.DataOutputStream;
import java.io.DataInputStream;

import java.net.Socket;
import java.util.Scanner;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.io.Serializable;



public class Client implements Serializable{
    private String nomClient;
    private DataInputStream dis;
    private DataOutputStream dout;
    private Socket s;
    public Client(){

    }

    public String getNomClient(){
        return this.nomClient;
    }

    public void setNomClient(String nomC){
        this.nomClient = nomC;
    }
    public void mainSession(){
        ObjectInputStream ois = null;
        
        try{
            Socket s=new Socket("localhost",6666);
            DataInputStream dis = new DataInputStream(s.getInputStream());
            
            
            System.out.println(dis.readUTF());
            Scanner sc = new Scanner(System.in);
            
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(this);
            oos.flush();

            /*ois = new ObjectInputStream(s.getInputStream());*/

            choisirNom(sc, s, dis);
            for(int i=0; i<1000000; i++){
                System.out.println("Bonjour "+this.getNomClient());

            }
            
            while(true){
                String message = sc.nextLine();
                dout.writeUTF(message);
                dout.flush();
                if (message.equals("/quit")){
                    System.out.println("Au revoir "+this.nomClient);
                    break;
                }
            }
            
            dout.close();
            s.close();
        }
        catch(Exception e){System.out.println(e);}
        }

        public void choisirNom(Scanner sc, Socket s, DataInputStream dis){
            
            try{
                Boolean nomEstChoisi = false;
                DataOutputStream dout=new DataOutputStream(s.getOutputStream());
                
                String name = null;
                while(! nomEstChoisi){
                    System.out.println("Donner moi votre nom !");
                    name = sc.nextLine();
                    dout.writeUTF(name);
                    nomEstChoisi = (Boolean) dis.readBoolean();
                    if(nomEstChoisi){
                        System.out.println(name+"!!! Ca sonne bien !"); 
                    }
                    else{
                        System.out.println("malheureusement "+name+" est déjà utilisé");
                    }
                    
                
                }
                
            }
            catch(Exception e){System.out.println(e);}
        }
    }


