import lejos.nxt.*;
import lejos.util.Delay;
import java.util.LinkedList;

/**
 * @(#)Robot.java
 *
 * 	Represents the instance of the Robot. This class interfaces with the
 *	servos and sensor, and is responsible for moving the bot. It makes changes
 * 	to map data as well
 *
 * @author Dan Lapp
 * @version 1.00 2014/4/15
 */
public class Robot {
	
	private static NXTRegulatedMotor motorLeft, motorRight; //servo motors for tracks
	private static UltrasonicSensor ultrasonicFront; //the range sensor
	
	Point position; 				//absolute position in the world
	Point screenPosition;			//position in current screen Node
  	Direction direction;			//direction the robot is facing
	LinkedList<Point> positions;	//list of past positions to detect cycles
	
	public Node current;	//current screen Node the robot is in
    
	/**
	 *	Default constructor. Initializes robot to default parameters and initializes
	 *	lists, sensors and servos.
	 */
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
    
	/**
	 *	Polls the sensor for the distance the robot is from object. 255 means
	 *	no object in range
	 *	@return distance robot is from object in front of it
	 */
    public int frontDistance(){
    	return ultrasonicFront.getDistance();
    }
	
	/**
	 *	Called by MapBot when the robot encounters an object. Adds a point to
	 *	the current Node's map data to draw the objects
	 */
	public void collision(){
		current.points.add(new Point(screenPosition.x, screenPosition.y));
	}
	
	/**
	 *	Checks to see if the robot has visited this area recently.
	 *	@return true if it has visited area recently, false otherwise
	 */
	public boolean stuckInCycle(){
		for(int i = 0; i < positions.size(); i++)
			if(positions.get(i).x == position.x && positions.get(i).y == position.y){
				System.out.println("Cycle" + positions.get(i));
				return true;
			}
		
		//add position to list if its not in a cycle
		positions.add(0, new Point(position.x, position.y));
		
		//trim list if its too long
		if(positions.size() > 16)
			positions.remove(positions.size() - 1);
		
		return false;
	}
	
	/**
	 *	@return the current screen Node that the robot is in
	 */
	public Node getCurrentNode(){
		return current;
	}
    
	/**
	 * Turns the robot 90 degrees left
	 */
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
		
		//adjust direction
		if(direction == Direction.UP)
			direction = Direction.LEFT;
		else if(direction == Direction.LEFT)
			direction = Direction.DOWN;
		else if(direction == Direction.DOWN)
			direction = Direction.RIGHT;
		else
			direction = Direction.UP;
	}
	
	/**
	 * Turns the robot 90 degrees right
	 */
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
		
		//adjust direction
		if(direction == Direction.UP)
			direction = Direction.RIGHT;
		else if(direction == Direction.LEFT)
			direction = Direction.UP;
		else if(direction == Direction.DOWN)
			direction = Direction.LEFT;
		else
			direction = Direction.DOWN;
	}
	
	/**
	 *	Moves the robot forward one unit in it's position, and one
	 *	pixel on the screen
	 */
	public void forward(int ticks){
		motorLeft.forward();
		motorRight.forward();
			
		int initialDist = motorLeft.getTachoCount();
		while(motorLeft.getTachoCount() - initialDist < (36 * ticks)); //move 1 pixel
		
		//adjust absolute and current screen positions
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
		
		//check to see if the robot has entered another screen Node
		checkCurrentNode();
	}
	
	/**
	 * 	Checks to see if the robot has left the current screen Node and 
	 *	entered another. This is called after every move forward, and 
	 *	will move the robot to the appropriate screen node, and adjust 
	 *	the screenPosition accordingly
	 */
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
	
	//required for some reason by NXJ
	public static void main(String[] args){
	}
}
