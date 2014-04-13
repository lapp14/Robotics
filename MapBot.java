import lejos.nxt.*;
import lejos.nxt.addon.*;
import lejos.util.Delay;

public class MapBot{
	
	private static UltrasonicSensor ultrasonic;
	private static CompassHTSensor compass;
	private static NXTRegulatedMotor motorLeft, motorRight;
	
	Node start, current;
	
	public MapBot(){
		ultrasonic = new UltrasonicSensor(SensorPort.S1);
		compass = new CompassHTSensor(SensorPort.S4);
		motorLeft = new NXTRegulatedMotor(MotorPort.C);
		motorRight = new NXTRegulatedMotor(MotorPort.A);
				
		start = new Node();
		current = start;
	}
	
	public void search(){
		while(!Button.ESCAPE.isDown()) {
			forward();
			turnLeft();
		}
	}

	public void turnLeft(){
		motorLeft.backward();
		motorRight.forward();
		
		Delay.msDelay(1440);
		
		motorLeft.stop();
		motorRight.stop();
	}
	
	public void turnRight(){
		motorLeft.forward();
		motorRight.backward();
		
		Delay.msDelay(1440);
		
		motorLeft.stop();
		motorRight.stop();
	}
	
	public void forward(){
		motorLeft.forward();
		motorRight.forward();
		
		Delay.msDelay(2000);
		
		motorLeft.stop();
		motorRight.stop();
	}
	
	public static void main(String[] args){
		new MapBot().search();
	}
	
	class Node{
		
		public BitSet data;
		public Node north, south, east, west;
		
		/**
		 *	The Bitset represents a 16x16 array of bits, in a single dimension
		 **/
		
		public Node(){
			data = new BitSet(256);
		}
		
		/**
		 *	Simple method to get the single dimensional index from x, y values
		 */
		public static int getIndex(int x, int y){
			return x * 16 + y;
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

