

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Okno extends JFrame{
	
	JPanel panelStart = new PanelStart(this, null);
	
	String nazwaMetody;
	
	Okno(){
		add(panelStart);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}