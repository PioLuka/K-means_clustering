import java.awt.Color;

public class General extends Zolnierz {
	
	private Color kolor;
	private int numerArmii = 0;

	General(int x, int y, Color kolor) {
		super(x, y);
		this.kolor = kolor;
	}
	
	Color getKolor(){
		return kolor;
	}
	
	void setXY(int x, int y){
		setX(x);
		setY(y);
	}
	
	int getNumer(){
		return numerArmii;
	}
	
	void setNumer(int x){
		this.numerArmii = x;
	}

}
