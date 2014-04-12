import lejos.nxt.*;
import lejos.nxt.addon.*;
import lejos.util.Delay;

public class MapBot{
	
	private static UltrasonicSensor ultrasonic;
	private static CompassHTSensor compass;
	private static NXTRegulatedMotor motorLeft, motorRight;
	
	Node start;
	
	public MapBot(){
		ultrasonic = new UltrasonicSensor(SensorPort.S1);
		compass = new CompassHTSensor(SensorPort.S4);
		motorLeft = new NXTRegulatedMotor(MotorPort.C);
		motorRight = new NXTRegulatedMotor(MotorPort.A);
				
		start = new Node();
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
		public byte[] data;
		public Node north, south, east, west;
		
		public Node(){
			data = new byte[16];
		}
	}
}

