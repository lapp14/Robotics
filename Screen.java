import javax.microedition.lcdui.Graphics;

public class Screen{
	Graphics g;
	
	public Screen(){
		g = new Graphics();
		g.setColor(Graphics.BLACK);
	}
	
	public void drawNode(Node n){
		g.clear();
	
		if(n.points.size() > 1)
			for(int i = 0; i < n.points.size() - 1; i++){
				if(n.points.get(i).x >= 0 && n.points.get(i + 1).x >= 0)
					g.drawLine(n.points.get(i).x, n.points.get(i).y, n.points.get(i + 1).x, n.points.get(i + 1).y);					
			}
	}
	
	public static void main(String[] args){
	}	
}