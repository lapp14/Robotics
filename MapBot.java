import lejos.nxt.*;
import lejos.util.Delay;
import java.util.BitSet;
import javax.microedition.lcdui.Graphics;

public class MapBot{
	
	public final int COLLISION_DIST = 23;
	public final int SCALE = 8;
	
	Robot robot;
	boolean mapping;
	
	public MapBot(){
		robot = new Robot();
		mapping = true;		
	}
	
	public void waitForUser(){
		// Button listener for ESCAPE to quit program
		Button.ESCAPE.addButtonListener(new ButtonListener(){
			public void buttonPressed(Button b){
				System.exit(0);
			}
			
			public void buttonReleased(Button b){}
		});
		
		System.out.println("Press ENTER to start\nPress ESCAPE to quit");
		
		//wait for user to start program
		while(!Button.ENTER.isDown());
				
		// Button listener for ESCAPE to quit program
		Button.ENTER.addButtonListener(new ButtonListener(){
			public void buttonPressed(Button b){
				mapping = false;
			}
			
			public void buttonReleased(Button b){}
		});

	}
	
	public void search(){
		while(mapping) {		
			if(robot.frontDistance() <= COLLISION_DIST){
			
				//avoid obstacle
				robot.collision();
				robot.turnLeft();
				
				/* wall following loop */
				while(mapping){
				
					for(int i = 0; i < 18; i++){
						if(robot.frontDistance() <= COLLISION_DIST){
							//if collision
							robot.collision();
							robot.turnLeft();
							i = -1; //restart loop at 0
						}
						robot.forward(1);
					}
					
					if(robot.stuckInCycle())
						break;
						
					System.out.println(robot.getCurrentNode().position);
						
					robot.turnRight();
					
					if(robot.frontDistance() <= COLLISION_DIST){
						robot.collision();
						robot.turnLeft();
					}
				}
				
			} else {
				robot.forward(1);
			}
		}
	}
		
	public static void main(String[] args){
		MapBot bot = new MapBot();
		bot.waitForUser();
		bot.search();
		
		//after user stops mapping, wait for ESCAPE press to end program
		while(true);
	}
	
	
}

