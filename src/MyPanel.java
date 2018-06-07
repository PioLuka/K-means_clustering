import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

public class MyPanel extends JPanel {
	
	int wysokosc = 900,
		szerokosc = 600,
		iloscGeneralow = 21;
	
	List<Zolnierz> listaWojska;
	List<General> listaGeneralow;
	Color[] tabKolor = {
		Color.BLUE,
		Color.GREEN,
		Color.RED,
		Color.CYAN,
		Color.MAGENTA,
		Color.YELLOW
	};

	MyPanel(List<Zolnierz> listaZ, List<General> listaG){
		
		this.listaWojska = listaZ;
		this.listaGeneralow = listaG;
		
		for(int i = 1; i <= 100000; i++){
			listaWojska.add(new Zolnierz(((int)(Math.random()*szerokosc)),((int)(Math.random()*wysokosc))));
		}
		
		setPreferredSize(new Dimension(szerokosc, wysokosc));
		
		for(int i = 0; i < iloscGeneralow; i++){
			if(i % 2 == 0){
				listaGeneralow.add(new General(szerokosc/iloscGeneralow, wysokosc/iloscGeneralow + (wysokosc/iloscGeneralow)*i, tabKolor[i%6]));
			}else{
				listaGeneralow.add(new General(szerokosc - szerokosc/iloscGeneralow, wysokosc/iloscGeneralow + (wysokosc/iloscGeneralow)*(i-1), tabKolor[i%6]));
			}
			listaGeneralow.get(i).setNumer(i);
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		for(int i = 0; i < listaGeneralow.size(); i++){
			g.setColor(listaGeneralow.get(i).getKolor());
			g.fillRect(listaGeneralow.get(i).getX(), listaGeneralow.get(i).getY(), 6, 6);
		}
        
        //wojsko
        for(Zolnierz z : listaWojska){
        	for(General gen : listaGeneralow){
        		if(z.getArmia() == gen.getNumer()){
        			//g.setColor(Color.BLACK);
            		//g.fillRect(z.getX(), z.getY(), 3, 3);
        			g.setColor(gen.getKolor());
            		g.drawLine(z.getX(), z.getY(), gen.getX(), gen.getY());
        		}else{
        			g.setColor(Color.BLACK);
            		g.fillRect(z.getX(), z.getY(), 3, 3);
        		}
        	}
        }
	}
}
