import lejos.nxt.*;
import lejos.util.Delay;
import java.util.BitSet;
import javax.microedition.lcdui.Graphics;

public class MapBot{
	
	public final int COLLISION_DIST = 25;
	public final int SCALE = 8;
	
	private static Graphics graphics;
	
	Robot robot;
	boolean mapping;
	
	public MapBot(){
		graphics = new Graphics();
		
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
		
		graphics.clear();
		
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
			if(robot.getDistance() <= COLLISION_DIST){
			
				//avoid obstacle
				robot.collision();
				robot.turnLeft();
				
				/* wall following loop */
				while(mapping){
					for(int i = 0; i < 18; i++){
						if(robot.getDistance() <= COLLISION_DIST){
							//if collision
							robot.collision();
							robot.turnLeft();
							i = -1; //restart loop at 0
						}
						robot.forward(1);
					}
					
					robot.turnRight();
					
					if(robot.getDistance() <= COLLISION_DIST){
						robot.collision();
						robot.turnLeft();
					}
					System.out.println(robot.position.x + ", " + robot.position.y);
					drawScreen();
				}
				
			} else {
				robot.forward(1);
			}
		}
	}
	
	public void look(){
		int range = robot.getDistance();
	}

	
	/**
	 *	Screen origin is top left and is 100x64
	 *	Tiles are drawn onto the screen and their position is the top left corner
	 */
	public void drawScreen(){
		graphics.clear();
				
		drawTile(robot.getCurrentNode(), 0, 0);
	}
		
	public void drawTile(Node tile, int x, int y){
			
		for(int i = 0; i < 32; i++){
			for(int j = 0; j < 32; j++){
				if(tile.data.get(tile.getIndex(i, j))){
					graphics.drawLine(i + x, j + y, i + x, j + y);
				}
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

