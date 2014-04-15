/**
 * @(#)Robot.java
 *
 *
 * @author 
 * @version 1.00 2014/4/15
 */


public class Robot {
	
	private static NXTRegulatedMotor motorLeft, motorRight;
	private static UltrasonicSensor ultrasonic;
	
	Point position;
	Point screenPosition;
  	Direction direction;
    
    public Robot(){
    	
		ultrasonic = new UltrasonicSensor(SensorPort.S1);
		motorLeft = new NXTRegulatedMotor(MotorPort.C);
		motorRight = new NXTRegulatedMotor(MotorPort.A);
    	
    	position = new Point(-16, -30);
    	direction = Direction.UP;
    	
    	
    }
    
    
    public int getDistance(){
    	return ultrasonic.getDistance();
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
    
}
