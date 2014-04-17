import lejos.nxt.*;

public class Screen{
	public static final int BLACK = 1;
	
	public Screen(){
	}
	
	public void drawNode(Node n){
		LCD.clear();
	
		for(int i = 0; i < 100; i++){
			for(int j = 0; j < 64; j++){
				if(n.data.get(n.getIndex(i, j)))
					LCD.setPixel(i, j, BLACK);
					
			}
		}
	}
	
	public static void main(String[] args){
	}	
}