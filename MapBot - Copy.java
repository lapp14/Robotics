import lejos.nxt.*;
import lejos.nxt.addon.*;
import lejos.util.Delay;
import java.util.BitSet;
import java.awt.Point;
import javax.microedition.lcdui.Graphics;

public class MapBot{
	
	private static UltrasonicSensor ultrasonic;
	private static CompassHTSensor compass;
	private static NXTRegulatedMotor motorLeft, motorRight;
	private static Graphics graphics;
	public final int SCALE = 8;
	
	Node start, current;
	Point position;
	
	public MapBot(){
		ultrasonic = new UltrasonicSensor(SensorPort.S1);
		compass = new CompassHTSensor(SensorPort.S4);
		motorLeft = new NXTRegulatedMotor(MotorPort.C);
		motorRight = new NXTRegulatedMotor(MotorPort.A);
		graphics = new Graphics();
				
		start = new Node();
		current = start;
		
		position = new Point(0, 0);
		
		
	}
	
	public void search(){
		/*while(!Button.ESCAPE.isDown()) {
			look();
			drawScreen();
		}*/
		
		Delay.msDelay(2000);
		look();
		drawScreen();
		
		forward(5);
		
		look();
		drawScreen();
		
		Delay.msDelay(5000);
	}
	
	public void look(){
		int range = ultrasonic.getDistance();
		Node n = current;
		
		for(int i = 0; i < (int)(range / 16); i++){
			if(n.up == null)
				n.up = new Node();
				
			n = n.up;
		}
		
		for(int i = 0; i < 16; i++)
			n.data.set(n.getIndex(i, range % 16));
	}

	
	/**
	 *	Screen origin is top left and is 100x64
	 *	Tiles are drawn onto the screen and their position is the top left corner
	 */
	public void drawScreen(){
		graphics.clear();
		
		Node n = current;
		
		for(int y = 48; y >= 0; y -= 16){
			drawTile(n, 46, y); //center
			
			Node temp = n;
				
			for(int x = 30; x > -16; x -= 16)
				if(temp.left != null){
					temp = temp.left;
					drawTile(temp, x, y); //draw left from current
				}
				
			temp = n;
					
			for(int x = 62; x < 100; x += 16)
				if(temp.right != null){
					temp = temp.right;
					drawTile(temp, x, y); //draw right from current
				}
			
			if(n.up != null)
				n = n.up;
			else
				break;
		}
	}
		
	public void drawTile(Node tile, int x, int y){
			
		for(int i = 0; i < 16; i++){
			for(int j = 0; j < 16; j++){
				if(tile.data.get(tile.getIndex(i, j)))
					graphics.drawLine(i + x - position.x, j + y - position.y, i + x - position.x, j + y - position.y);	
			}					
		}
	}

	public void turnLeft(){
		motorLeft.backward();
		motorRight.forward();
		
		int initialPos = motorLeft.getTachoCount();
		while(motorLeft.getTachoCount() - initialPos < 533);
		
		motorLeft.stop(true);
		motorRight.stop(true);
	}
	
	public void turnRight(){
		motorLeft.forward();
		motorRight.backward();
		
		int initialPos = motorLeft.getTachoCount();
		while(motorLeft.getTachoCount() - initialPos < 533);
		
		motorLeft.stop(true);
		motorRight.stop(true);
	}
	
	public void forward(int ticks){
		motorLeft.forward();
		motorRight.forward();
			
		int initialDist = motorLeft.getTachoCount();
		while(motorLeft.getTachoCount() - initialDist < (36 * ticks)); //2000 moves 55 pixels
		
		//based on direction
		position.x += ticks;
		
		if(position.x >= 16){
			position.x %= 16;
			
			if(current.up == null)
				current.up = new Node();
				
			current = current.up;
		}
		
		motorLeft.stop(true);
		motorRight.stop(true);
	}
	
	public static void main(String[] args){
		new MapBot().search();
	}
	
	class Node{
		
		public BitSet data;
		public Node up, down, left, right;
		
		/**
		 *	The Bitset represents a 16x16 array of bits, in a single dimension
		 **/
		
		public Node(){
			data = new BitSet(256);
		}
		
		/**
		 *	Simple method to get the single dimensional index from x, y values
		 */
		public int getIndex(int x, int y){
			return y * 16 + x;
		}
	    
	    /**
	     * @Override
	     *	Breaks up the single dimension bit array and prints it as a 16x16
	     *  array of 1's and 0's
	     */
	    public String toString(){
	    	String s = "";    	
	    		
	    	for(int i = 0; i < 256; i++){
	    		if(data.get(i))
	    			s += "1";
	    		else
	    			s += "0";
	    		
	    		if(i % 16 == 15)
	    			s += "\n";
	    	}
	    	
	    	return s;
	    }
	}
}

