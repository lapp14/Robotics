import lejos.nxt.*;
import lejos.util.Delay;
import java.util.BitSet;
import java.util.Random;

public class Text{
	public static final int BLACK = 1;

	private static UltrasonicSensor ultrasonicFront;
	private static UltrasonicSensor ultrasonicSide;
	
	Random r = new Random();
	
	public Text(){
		// Button listener for ESCAPE to quit program
		Button.ESCAPE.addButtonListener(new ButtonListener(){
			public void buttonPressed(Button b){
				System.exit(0);
			}
			
			public void buttonReleased(Button b){}
		});
	}
	
	public void go(){
		LCD.clear();
		
		Node current = new Node();
		randomize(current);
		
		current.addRight();
		randomize(current.right);
		
		current.right.addRight();
		randomize(current.right.right);
		
		current.addDown();
		randomize(current.down);
		
		current.right.addDown();
		randomize(current.right.down);
		
		current.right.right.addDown();
		randomize(current.right.right.down);
		
		
		drawNode(current);
		drawNode(current.down);
		drawNode(current.right);
		drawNode(current.right.right);
		drawNode(current.right.down);
		drawNode(current.right.right.down);
		
		while(true);
	}
	
	public void drawNode(Node n){
		for(int i = 0; i < 32; i++){
			for(int j = 0; j < 32; j++){
				if(n.data.get(n.getIndex(i, j)))
					LCD.setPixel(i + (n.position.x * 32), j + (n.position.y * 32), BLACK);
					
			}
		}
	}
	
	public void randomize(Node n){
		int col = r.nextInt(32);
		int row = r.nextInt(32);
		
		for(int i = 0; i < 32; i++)
			n.data.set(n.getIndex(i, col));
			
		for(int i = 0; i < 32; i++)
			n.data.set(n.getIndex(row, i));
			
	}

	public static void main(String[] args){
		new Text().go();
	
	}	
}