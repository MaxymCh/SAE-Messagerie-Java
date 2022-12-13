import java.net.ServerSocket;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.DataInputStream;

import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
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
            DataOutputStream dout=new DataOutputStream(s.getOutputStream());
            ois = new ObjectInputStream(s.getInputStream());
            
            System.out.println(dis.readUTF());
            Scanner sc = new Scanner(System.in);
            
            /*
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(this);
            oos.flush();
            this.setNomClient("mzamzm");
            */

            /*ois = new ObjectInputStream(s.getInputStream());*/

            choisirNom(sc, s, dis, dout);
            ClientReader cr = new ClientReader(this, s);
            cr.start();
            while(true){
                System.out.println("Bienvenue dans la messagerie vous pouvez ecrire vos messages /salon changer salon");
                String message = sc.nextLine();
                dout.writeUTF(message);
                dout.flush();
                if (message.equals("/quit")){
                    System.out.println("Au revoir "+this.nomClient);
                    break;
                }
                else if (message.equals("/salon")){
                    changerSalon(dis, sc, dout);
                    
                }
                
            }
            
            dout.close();
            s.close();
        }
        catch(Exception e){System.out.println(e);}
        }

        public void choisirNom(Scanner sc, Socket s, DataInputStream dis, DataOutputStream dout){
            
            try{
                Boolean nomEstChoisi = false;
               
                
                String name = null;
                while(! nomEstChoisi){
                    System.out.println("Donner moi votre nom !");
                    name = sc.nextLine();
                    dout.writeUTF(name);
                    nomEstChoisi = (Boolean) dis.readBoolean();
                    if(nomEstChoisi){
                        System.out.println(name+"!!! Ca sonne bien !"); 
                        this.setNomClient(name);
                    }
                    else{
                        System.out.println("malheureusement "+name+" est déjà utilisé");
                    }
                    
                
                }
                
            }
            catch(Exception e){System.out.println(e);}
        }
    public void changerSalon(DataInputStream ois, Scanner sc, DataOutputStream dout){
        try {
            String nomSallon = sc.nextLine();
            dout.writeUTF(nomSallon);
            System.out.println("Bienvenue dans le sallon "+nomSallon);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        


        


    }

}


