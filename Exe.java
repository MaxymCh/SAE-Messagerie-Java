public class Exe {
    public static void main (String[] args){
        Client cl1 = new Client();
        System.out.println(cl1.getNomClient());
        Object o = (Object) cl1;
        Client cl2 = (Client) o;
        cl2.setNomClient("momo");
        System.out.println(cl1.getNomClient());
    }
}
