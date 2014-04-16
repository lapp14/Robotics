import lejos.nxt.*;
import lejos.util.Delay;
import java.util.BitSet;
import javax.microedition.lcdui.Graphics;

public class MapBot{
	
	private static Graphics graphics;
	public final int COLLISION_DIST = 25;
	
	Robot robot;
	public final int SCALE = 8;
	
	Node current;
	
	public MapBot(){
		graphics = new Graphics();
		
		robot = new Robot();
				
		current = new Node();
		
	}
	
	public void search(){
		while(!Button.ESCAPE.isDown()) {		
			if(robot.getDistance() <= COLLISION_DIST){
				//avoid obstacle
				robot.turnLeft();
				
				boolean obstacle = true;
				
				while(obstacle){
					if(robot.getDistance() <= COLLISION_DIST)
						break;
				
					for(int i = 0; i < 18 && robot.getDistance() > COLLISION_DIST; i++)
						robot.forward(1);
					
					robot.turnRight();
					
					if(robot.getDistance() > COLLISION_DIST)
						obstacle = false;
					else
						robot.turnLeft();
				}
				
			}else{
			
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
		
		Node n = current;
		
		for(int y = 48; y >= 0; y -= 16){
			drawTile(n, 46, y); //center
			
			Node temp = n;
				
			for(int x = 30; x > -16; x -= 16)
				if(temp.left != null){
					temp = temp.left;
					drawTile(temp, x, y); //draw left from current
				}
				
			temp = n;
					
			for(int x = 62; x < 100; x += 16)
				if(temp.right != null){
					temp = temp.right;
					drawTile(temp, x, y); //draw right from current
				}
			
			if(n.up != null)
				n = n.up;
			else
				break;
		}
	}
		
	public void drawTile(Node tile, int x, int y){
			
		for(int i = 0; i < 16; i++){
			for(int j = 0; j < 16; j++){
				if(tile.data.get(tile.getIndex(i, j))){
				//	graphics.drawLine(i + x - position.x, j + y - position.y, i + x - position.x, j + y - position.y);
				}
			}					
		}
	}
	
	public static void main(String[] args){
		new MapBot().search();
	}
	
	
}

