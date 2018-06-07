import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MetodaEuklidesowa {
	
	private List<Zolnierz> listaZolnierzy;
	private List<General> listaGeneralow;
	private Map<General, Integer> mapaWynikow = new HashMap<>();
	
	MetodaEuklidesowa(List<Zolnierz> listaZ, List<General> listaG){
		this.listaZolnierzy = listaZ;
		this.listaGeneralow = listaG;
		
		for(General g : listaGeneralow){
			mapaWynikow.put(g, 0);
		}
	}
	
	void liczArmie(){
		int iloscZmianWArmii = -1;
		while(iloscZmianWArmii != 0){
			
			Map<General, List<Integer>> mapaNowychXYGeneralow = new HashMap<>();
			
			for(General g : listaGeneralow){
				mapaNowychXYGeneralow.put(g, new ArrayList<>());
				mapaNowychXYGeneralow.get(g).add(0);
				mapaNowychXYGeneralow.get(g).add(0);
				mapaNowychXYGeneralow.get(g).add(0);
			}
			
			iloscZmianWArmii = 0;
			
			for(Zolnierz z : listaZolnierzy){
				for(General g : listaGeneralow){
					mapaWynikow.put(g, 0);
					mapaWynikow.put(g, (z.getX() - g.getX()) * (z.getX() - g.getX()) + (z.getY() - g.getY()) * (z.getY() - g.getY()));
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
	}
}
