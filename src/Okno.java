import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Okno extends JFrame{
	
	List<Zolnierz> listaWojska = new ArrayList<>();
	List<General> listaGeneralow = new ArrayList<>();
	
	JPanel panel = new MyPanel(listaWojska, listaGeneralow);
	MetodaEuklidesowa mE = new MetodaEuklidesowa(listaWojska, listaGeneralow);
	
	Okno(){
		add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		pack();
		
		mE.liczArmie();
	}
}