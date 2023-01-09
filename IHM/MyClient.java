import javax.swing.JFrame;


public class MyClient {
	  public static void main(String[] argv) {   
	      JFrame fenetre = new JFrame();
	      fenetre.setContentPane(new IHMClient());
	      fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      fenetre.setLocation(100, 100);
	      fenetre.pack();
	      fenetre.setVisible(true);
	    }
	}

