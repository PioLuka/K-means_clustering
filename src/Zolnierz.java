
public class Zolnierz {
	
	private int x,
				y,
				armia = -1;
	
	Zolnierz(int x, int y){
		this.x = x;
		this.y =y;
	}
	
	int getX(){
		return x;
	}
	
	void setX(int x){
		this.x = x;
	}

	int getY(){
		return y;
	}
	
	void setY(int y){
		this.y = y;
	}
	
	int getArmia(){
		return armia;
	}
	
	void setArmia(int general){
		armia = general;
	}
}
