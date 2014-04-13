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
		
		/* Represents a 16x16 bit array
		 * The bytes are assumed to be in the following orientation
		 *  [0] [1]
		 *  [2] [3]
		 *   .   .
		 *   .   .
		 *  [14][15]
		 */
		
		public Node(){
			data = new byte[32];
		}
		
		public byte getBit(int x, int y){		
			int byteIndex = x * 2;
			
			if(y > 7){
				x++;
				y -= 8;
			}
			
			switch(y){
				// 1000 0000
				case 0:
					if(data[byteIndex] & 0x989680 == 0)
						return 0;
					break;
					
				// 0100 0000
				case 1:
					if(data[byteIndex] & 0x40 == 0)
						return 0;
					break;
				
				// 0010 0000
				case 2:
					if(data[byteIndex] & 0x20 == 0)
						return 0;
					break;
					
				// 0001 0000
				case 3:
					if(data[byteIndex] & 0x10 == 0)
						return 0;
					break;
					
				// 0000 1000
				case 4:
					if(data[byteIndex] & 0x8 == 0)
						return 0;
					break;
					
				// 0000 0100
				case 5:
					if(data[byteIndex] & 0x4 == 0)
						return 0;
					break;
					
				// 0000 0010
				case 6:
					if(data[byteIndex] & 0x2 == 0)
						return 0;
					break;
					
				// 0000 0001
				case 7:
					if(data[byteIndex] & 0x1 == 0)
						return 0;
					break;
			}
			
			return 1;
		}
		
		public void setBit(int x, int y, boolean value){		
			int byteIndex = x * 2;
			
			if(y > 7){
				x++;
				y -= 8;
			}
			
			switch(y){
				case 0:
					if(value)// 1000 0000
						data[byteIndex] | 0x989680;
					else // 0111 1111
						data[byteIndex] & 0x7f;
					break;
					
				// 0100 0000
				case 1:
					
				
					if(data[byteIndex] & 0x40 == 0)
						return 0;
					break;
				
				// 0010 0000
				case 2:
					if(data[byteIndex] & 0x20 == 0)
						return 0;
					break;
					
				// 0001 0000
				case 3:
					if(data[byteIndex] & 0x10 == 0)
						return 0;
					break;
					
				// 0000 1000
				case 4:
					if(data[byteIndex] & 0x8 == 0)
						return 0;
					break;
					
				// 0000 0100
				case 5:
					if(data[byteIndex] & 0x4 == 0)
						return 0;
					break;
					
				// 0000 0010
				case 6:
					if(data[byteIndex] & 0x2 == 0)
						return 0;
					break;
					
				// 0000 0001
				case 7:
					if(data[byteIndex] & 0x1 == 0)
						return 0;
					break;
			}
			
			return 1;
		}
	}
}

