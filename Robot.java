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
	private static UltrasonicSensor ultrasonicFront;
	private static UltrasonicSensor ultrasonicRight;
	
	Point position;
	Point screenPosition;
  	Direction direction;
	LinkedList<Point> positions;
	
	public Node current;
    
    public Robot(){
    	
		ultrasonicFront = new UltrasonicSensor(SensorPort.S1);
		ultrasonicRight = new UltrasonicSensor(SensorPort.S4);
		motorLeft = new NXTRegulatedMotor(MotorPort.C);
		motorRight = new NXTRegulatedMotor(MotorPort.A);
    	
		positions = new LinkedList<Point>();
    	position = new Point(50, 50);
		screenPosition = new Point(50, 50);
		
    	direction = Direction.UP;
		current = new Node();
    }
    
    public int frontDistance(){
    	return ultrasonicFront.getDistance();
    }
	
    public int rightDistance(){
    	return ultrasonicRight.getDistance();
    }
	
	public void collision(){
		current.points.add(new Point(screenPosition.x, screenPosition.y));
	}
		
	public boolean stuckInCycle(){
		for(int i = 0; i < positions.size(); i++)
			if(positions.get(i).x == position.x && positions.get(i).y == position.y){
				System.out.println("Cycle" + positions.get(i));
				return true;
			}
			
		positions.add(0, new Point(position.x, position.y));
		
		if(positions.size() > 16)
			positions.remove(positions.size() - 1);
		
		return false;
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
		Delay.msDelay(150);
		
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
		Delay.msDelay(150);
		
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
		
		if(direction == Direction.UP){
			position.y -= ticks;
			screenPosition.y -= ticks;
		} else if(direction == Direction.LEFT){
			position.x -= ticks;
			screenPosition.x -= ticks;
		} else if(direction == Direction.DOWN) {
			position.y += ticks;
			screenPosition.y += ticks;
		} else {
			position.x += ticks;
			screenPosition.x += ticks;
		}
		
		motorLeft.stop(true);
		motorRight.stop(true);
		
		checkCurrentNode();
	}
	
	public void checkCurrentNode(){
		if(screenPosition.x > 99) {
			current.points.add(new Point(99, screenPosition.y));
			screenPosition.x -= 100;
			
			if(current.right == null){
				current.addRight();
				current.right.points.add(new Point(0, screenPosition.y));
			} else {
				current.right.points.add(new Point(-1, -1));
				current.right.points.add(new Point(0, screenPosition.y));
			}
			current = current.right;
			
		} else if(screenPosition.x < 0) {
			current.points.add(new Point(0, screenPosition.y));
			screenPosition.x += 100;
			
			if(current.left == null){
				current.addLeft();
				current.left.points.add(new Point(99, screenPosition.y));
			} else {
				current.left.points.add(new Point(-1, -1));
				current.left.points.add(new Point(99, screenPosition.y));
			}	
			current = current.left;
			
		} else if(screenPosition.y > 63) {
			current.points.add(new Point(screenPosition.x, 63));
			screenPosition.y -= 64;
			
			if(current.down == null){
				current.addDown();
				current.down.points.add(new Point(screenPosition.x, 0));
			} else {
				current.down.points.add(new Point(-1, -1));
				current.down.points.add(new Point(screenPosition.x, 0));
			}
				
			current = current.down;
		} else if(screenPosition.y < 0) {
			current.points.add(new Point(screenPosition.x, 0));
			screenPosition.y += 64;
			
			if(current.up == null){
				current.addUp();
				current.up.points.add(new Point(screenPosition.x, 63));
			} else {
				current.up.points.add(new Point(-1, -1));
				current.up.points.add(new Point(screenPosition.x, 63));
			}
				
			current = current.up;
		} else {
		
		}
	}
	
	public static void main(String[] args){
	}
    
}
