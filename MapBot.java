import lejos.nxt.*;
import lejos.util.Delay;
import java.util.BitSet;
import javax.microedition.lcdui.Graphics;

/**
 * @(#)MapBot.java
 *	Main class that does all the navigation. Controls the Robot class
 *
 * @author Dan Lapp
 * @version 1.00 2014/4/15
 */

public class MapBot{
	
	public final int COLLISION_DIST = 23;
	
	Robot robot; //the instance of the robot
	Screen screen; //the instance of our lcd graphics
	boolean mapping; //is the robot conducting mapping
	
	public MapBot(){
		robot = new Robot();
		screen = new Screen();
		mapping = true;		
	}
	
	/**
	 * Waits for the user to press enter before starting mapping. Adds
	 * action listeners and behaviour to all 4 NXT buttons
	 */
	public void waitForUser(){
		// Button listener for ESCAPE to quit program
		Button.ESCAPE.addButtonListener(new ButtonListener(){
			public void buttonPressed(Button b){
				//press ENTER and ESCAPE to exit program
				if(Button.ENTER.isDown())
					System.exit(0);
					
				if(!mapping){
					if(robot.current.down != null){
						robot.current = robot.current.down;
						screen.drawNode(robot.getCurrentNode());
					}
				} else {
					System.exit(0);
				}
			}
			
			public void buttonReleased(Button b){}
		});
		
		Button.LEFT.addButtonListener(new ButtonListener(){
			public void buttonPressed(Button b){
				if(!mapping){
					if(robot.current.left != null){
						robot.current = robot.current.left;
						screen.drawNode(robot.getCurrentNode());
					}
				}
			}
			
			public void buttonReleased(Button b){}
		});
		
		Button.RIGHT.addButtonListener(new ButtonListener(){
			public void buttonPressed(Button b){
				if(!mapping){
					if(robot.current.right != null){
						robot.current = robot.current.right;
						screen.drawNode(robot.getCurrentNode());
					}
				}
			}
			
			public void buttonReleased(Button b){}
		});
		
		System.out.println("Press ENTER to start\nPress ESCAPE to quit");
		
		//wait for user to start program
		while(!Button.ENTER.isDown());
				
		// Button listener for ESCAPE to quit program
		Button.ENTER.addButtonListener(new ButtonListener(){
			public void buttonPressed(Button b){
				//Press ENTER and ESCAPE to quit program
				if(Button.ESCAPE.isDown())
					System.exit(0);
			
				if(mapping)
					mapping = false;
				else{
					if(robot.current.up != null){
						robot.current = robot.current.up;
						screen.drawNode(robot.getCurrentNode());
					}
				}
			}
			
			public void buttonReleased(Button b){}
		});

	}
	
	/**
	 * Conducts the actual navigation and mapping
	 */
	public void search(){
		while(mapping) {		
			//search for an object to map
			if(robot.frontDistance() <= COLLISION_DIST){
				robot.collision(); //add map point
				robot.turnLeft();
				
				/* wall following loop */
				while(mapping){
					//move forward a bit
					for(int i = 0; i < 18; i++){
						if(robot.frontDistance() <= COLLISION_DIST){
							//if collision
							robot.collision(); //add map point
							robot.turnLeft();
							i = -1; //restart loop at 0
						}
						robot.forward(1);
					}
					
					//check to see if wall is still to the right of the bot
					robot.turnRight();
					
					if(robot.frontDistance() <= COLLISION_DIST){
						robot.collision(); //add map point
						robot.turnLeft();
					} else 
						robot.collision(); //add map point
				}
				
			} else {
				robot.forward(1);
			}
		}
		
		//draw final screen
		screen.drawNode(robot.getCurrentNode());
	}
	
	/**
	 * Program entry point
	 */
	public static void main(String[] args){
		MapBot bot = new MapBot();
		bot.waitForUser();
		bot.search();
		
		//after user stops mapping, wait for ESCAPE+ENTER press to end program
		while(true);
	}
	
	
}

