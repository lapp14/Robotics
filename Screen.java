import javax.microedition.lcdui.Graphics;

/**
 * @(#)Screen.java
 *	Represents an instance of NXT graphics. Responsible for drawing the
 *	current screen Node's map data to the NXT screen
 *
 * @author Dan Lapp
 * @version 1.00 2014/4/15
 */

public class Screen{
	Graphics g;
	
	public Screen(){
		g = new Graphics();
		g.setColor(Graphics.BLACK);
	}
	
	/**
	 *	Draws the current screen Node's map data to the nxt screen
	 *	@param n the current screen node to be drawn
	 */
	public void drawNode(Node n){
		g.clear();
	
		if(n.points.size() > 1)
			/*
			 * For each point in the map data, draw lines between them, unless
			 * there is a null point. Null points represent a line break
			 */
			for(int i = 0; i < n.points.size() - 1; i++){
				if(n.points.get(i).x >= 0 && n.points.get(i + 1).x >= 0)
					g.drawLine(n.points.get(i).x, n.points.get(i).y, n.points.get(i + 1).x, n.points.get(i + 1).y);					
			}
	}
	
	//required for some reason by NXJ
	public static void main(String[] args){
	}	
}