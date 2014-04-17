import lejos.nxt.*;
import lejos.util.Delay;
import java.util.LinkedList;

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
	LinkedList<Direction> collisions;
	
	Node current;
    
    public Robot(){
    	
		ultrasonic = new UltrasonicSensor(SensorPort.S1);
		motorLeft = new NXTRegulatedMotor(MotorPort.C);
		motorRight = new NXTRegulatedMotor(MotorPort.A);
    	
		collisions = new LinkedList<Direction>();
    	position = new Point(50, 50);
		
    	direction = Direction.UP;
		current = new Node();
    }
    
    
    public int getDistance(){
    	return ultrasonic.getDistance();
    }
	
	public void collision(){
		collisions.add(0, direction);
		
		if(collisions.size() == 5)
			collisions.remove(4);
					
	}
	
	public Node getCurrentNode(){
		return current;
	}
    
    public void turnLeft(){	
		motorLeft.stop(true);
		motorRight.stop(true);
		Delay.msDelay(150);
	
		motorLeft.backward();
		motorRight.forward();
		
		int initialPos = motorRight.getTachoCount();
		while(motorRight.getTachoCount() - initialPos < 535);
		
		motorLeft.stop(true);
		motorRight.stop(true);
		
		if(direction == Direction.UP)
			direction = Direction.LEFT;
		else if(direction == Direction.LEFT)
			direction = Direction.DOWN;
		else if(direction == Direction.DOWN)
			direction = Direction.RIGHT;
		else
			direction = Direction.UP;
	}
	
	public void turnRight(){	
		motorLeft.stop(true);
		motorRight.stop(true);
		Delay.msDelay(150);
	
		motorLeft.forward();
		motorRight.backward();
		
		int initialPos = motorLeft.getTachoCount();
		while(motorLeft.getTachoCount() - initialPos < 535);
		
		motorLeft.stop(true);
		motorRight.stop(true);
		
		if(direction == Direction.UP)
			direction = Direction.RIGHT;
		else if(direction == Direction.LEFT)
			direction = Direction.UP;
		else if(direction == Direction.DOWN)
			direction = Direction.LEFT;
		else
			direction = Direction.DOWN;
	}
	
	public void forward(int ticks){
		motorLeft.forward();
		motorRight.forward();
			
		int initialDist = motorLeft.getTachoCount();
		while(motorLeft.getTachoCount() - initialDist < (36 * ticks)); //2000 moves 55 pixels
		
		if(direction == Direction.UP)
			position.y -= ticks;
		else if(direction == Direction.LEFT)
			position.x -= ticks;
		else if(direction == Direction.DOWN)
			position.y += ticks;
		else
			position.x += ticks;
				
		motorLeft.stop(true);
		motorRight.stop(true);
	}
	
	public static void main(String[] args){
	}
    
}
