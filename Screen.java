import lejos.nxt.*;
import java.util.Random;

public class Screen{
	public static final int BLACK = 1;
	
	Random r = new Random();
	
	public Screen(){
		// Button listener for ESCAPE to quit program
		Button.ESCAPE.addButtonListener(new ButtonListener(){
			public void buttonPressed(Button b){
				System.exit(0);
			}
			
			public void buttonReleased(Button b){}
		});
	}
	
	//just for testing
	public void go(){
		LCD.clear();
		
		Node current = new Node();
		randomize(current);
		
		drawNode(current);
		
		while(true);
	}
	
	public void drawNode(Node n){
		for(int i = 0; i < 100; i++){
			for(int j = 0; j < 64; j++){
				if(n.data.get(n.getIndex(i, j)))
					LCD.setPixel(i, j, BLACK);
					
			}
		}
	}
	
	//just for testing
	public void randomize(Node n){
		int col = r.nextInt(64);
		int row = r.nextInt(100);
		
		for(int i = 0; i < 100; i++)
			n.data.set(n.getIndex(i, col));
			
		for(int i = 0; i < 64; i++)
			n.data.set(n.getIndex(row, i));
			
	}

	public static void main(String[] args){
	}	
}