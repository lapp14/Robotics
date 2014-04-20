import lejos.nxt.*;
import lejos.util.Delay;
import java.util.BitSet;
import javax.microedition.lcdui.Graphics;

public class MapBot{
	
	public final int COLLISION_DIST = 23;
	
	Robot robot;
	Screen screen;
	boolean mapping;
	
	public MapBot(){
		robot = new Robot();
		screen = new Screen();
		mapping = true;		
	}
	
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
					
					//if(robot.stuckInCycle())
					//	break;
												
					robot.turnRight();
					
					if(robot.frontDistance() <= COLLISION_DIST){
						robot.collision();
						robot.turnLeft();
					} else 
						robot.collision();
				}
				
			} else {
				robot.forward(1);
			}
		}
		
		//draw final screen
		screen.drawNode(robot.getCurrentNode());
	}
		
	public static void main(String[] args){
		MapBot bot = new MapBot();
		bot.waitForUser();
		bot.search();
		
		//after user stops mapping, wait for ESCAPE press to end program
		while(true);
	}
	
	
}

