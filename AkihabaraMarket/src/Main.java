import javax.swing.SwingUtilities;

import com.akihabara.market.view.*;





public class Main {

	public static void main(String[] args) {

		//InterfazConsola.menu();
		
	
		//Atraves del SwingUtilities llamamos al constructor de interfazGrafica y ponemos que sea visible
		SwingUtilities.invokeLater(() -> {
            new InterfazGrafica().setVisible(true);
        });

	}

}
