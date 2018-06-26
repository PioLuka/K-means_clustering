import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JPanel;

public class PomocniczyPanel extends JPanel {
	
	List<General> listaGeneralow;
	
	public PomocniczyPanel(List<General> listaG){
		this.listaGeneralow = listaG;
	}

	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics g2d = (Graphics2D) g;
		
		for(int i = 0; i < listaGeneralow.size(); i++){
			g2d.setColor(listaGeneralow.get(i).getKolor());
			g2d.fillRect(listaGeneralow.get(i).getX(), listaGeneralow.get(i).getY(), 6, 6);
		}
	}
}
