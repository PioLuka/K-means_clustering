import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JPanel;

public class MyPanel extends JPanel implements Runnable{
	
	int wysokosc = 860,
		szerokosc = 1180,
		iloscGeneralow = 21;
	
	List<Zolnierz> listaWojska = new ArrayList<>();
	List<General> listaGeneralow = new ArrayList<>();
	Color[] tabKolor = {
		Color.BLUE,
		Color.GREEN,
		Color.RED,
		Color.CYAN,
		Color.MAGENTA,
		Color.YELLOW
	};
	
	private Map<General, Integer> mapaWynikow = new HashMap<>();
	private Thread animator;
	int iloscZmianWArmii = -1;
	String nazwaMetody;
	int liczbaZolnierzy;

	MyPanel(String metoda, int liczbaZolnierzy, int iloscGen, List<General> listaGeneralow){
		
		setPreferredSize(new Dimension(szerokosc, wysokosc));
		
		this.nazwaMetody = metoda;
		this.liczbaZolnierzy = liczbaZolnierzy;
		this.iloscGeneralow = iloscGen;
		this.listaGeneralow = listaGeneralow;
		
		for(int i = 1; i <= liczbaZolnierzy; i++){
			listaWojska.add(new Zolnierz(((int)(Math.random()*szerokosc)),((int)(Math.random()*wysokosc))));
		}
		
		for(int i = 0; i < iloscGeneralow; i++){
			listaGeneralow.get(i).setNumer(i);
		}
		
		for(General g : listaGeneralow){
			mapaWynikow.put(g, 0);
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics g2d = (Graphics2D) g.create();
		
		for(int i = 0; i < listaGeneralow.size(); i++){
			g2d.setColor(listaGeneralow.get(i).getKolor());
			g2d.fillRect(listaGeneralow.get(i).getX(), listaGeneralow.get(i).getY(), 6, 6);
		}
        
        //wojsko
        for(Zolnierz z : listaWojska){
        	for(General gen : listaGeneralow){
        		if(z.getArmia() == gen.getNumer()){
        			g2d.setColor(gen.getKolor());
            		g2d.drawLine(z.getX(), z.getY(), gen.getX(), gen.getY());
        		}else{
        			g2d.setColor(Color.BLACK);
            		g2d.fillRect(z.getX(), z.getY(), 3, 3);
        		}
        	}
        }
        Toolkit.getDefaultToolkit().sync();
	}

	@Override
	public void run() {
		while(iloscZmianWArmii != 0){
			cycle();
			repaint();
			
			try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
		}
	}
	
	@Override
    public void addNotify() {
        super.addNotify();

        animator = new Thread(this);
        animator.start();
    }
	
	private void cycle() {

		Map<General, List<Integer>> mapaNowychXYGeneralow = new HashMap<>();
		
		for(General g : listaGeneralow){
			mapaNowychXYGeneralow.put(g, new ArrayList<>());
			mapaNowychXYGeneralow.get(g).add(0);
			mapaNowychXYGeneralow.get(g).add(0);
			mapaNowychXYGeneralow.get(g).add(0);
		}
		
		iloscZmianWArmii = 0;
		
		for(Zolnierz z : listaWojska){
			for(General g : listaGeneralow){
				mapaWynikow.put(g, 0);
				mapaWynikow.put(g, metodaOdleglosci(nazwaMetody, z.getX(), z.getY(), g.getX(), g.getY()));
			}
			int minVal = Collections.min(mapaWynikow.values());
			for(Entry<General, Integer> entry : mapaWynikow.entrySet()){
				if(entry.getValue() == minVal){
					if(z.getArmia() != entry.getKey().getNumer()){
						z.setArmia(entry.getKey().getNumer());
						iloscZmianWArmii++;
					}
					int nowyX = mapaNowychXYGeneralow.get(entry.getKey()).get(0) + z.getX();
					mapaNowychXYGeneralow.get(entry.getKey()).set(0, nowyX);
					
					int nowyY = mapaNowychXYGeneralow.get(entry.getKey()).get(1) + z.getY();
					mapaNowychXYGeneralow.get(entry.getKey()).set(1, nowyY);
					
					int iloscWojska = mapaNowychXYGeneralow.get(entry.getKey()).get(2) + 1;
					mapaNowychXYGeneralow.get(entry.getKey()).set(2, iloscWojska);
					break;
				}
			}
		}
		
		for(General g : listaGeneralow){
			if(mapaNowychXYGeneralow.get(g).get(2) != 0){
				g.setXY(mapaNowychXYGeneralow.get(g).get(0)/mapaNowychXYGeneralow.get(g).get(2), mapaNowychXYGeneralow.get(g).get(1)/mapaNowychXYGeneralow.get(g).get(2));
			}
		}
    }
	
	public int metodaOdleglosci(String nazwa, int xJednostka, int yJednostka, int xGeneral, int yGeneral){
		switch (nazwa){
			case "Euklidesowa" : return (xJednostka - xGeneral) * (xJednostka - xGeneral) + (yJednostka - yGeneral) * (yJednostka - yGeneral);
			case "Miejska": return (Math.abs(xJednostka - xGeneral)) + (Math.abs(yJednostka - yGeneral));
			case "Maksimum": return Math.max(Math.abs(xJednostka - xGeneral), Math.abs(yJednostka - yGeneral));
			case "Rzeczna": int c1 = xJednostka;
							int c2 = xGeneral;
							return (c1 - c2) * (c1 - c2) + (wysokosc/2 - yGeneral) * (wysokosc/2 - yGeneral);
			default: return 0;
		}
	}
}
