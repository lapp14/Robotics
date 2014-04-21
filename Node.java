/**
 * @(#)Node.java
 *	Representation of a single NXT screen of map data. Stores a list
 * 	of points that are used to draw lines. Each node is linked to adjacent
 *	Nodes to the left, right, up and down. These are used to scroll between
 *	screens to view map data on the NXT screen
 *
 * @author Dan Lapp
 * @version 1.00 2014/4/13
 */

import java.util.LinkedList;

public class Node {

    public LinkedList<Point> points; //collision points
	public Node up, down, left, right; //adjacent nodes
	
	/* Position of the node in relation to other nodes. The start node
	 *	is (0, 0), the one to the right is (1, 0), the one below is
	 *	(0, 1), etc
	 */
	public Point position;
	
	public int timesVisited; //number of times the node has been visited
	
	//required by NXJ for some reason
	public static void main(String[] args){
	}
	
	/**
	 * Initialization of the first node when mapping starts
	 */
	public Node(){
		points = new LinkedList<Point>();
		position = new Point(0, 0);
	}
	
	/**
	 *	Initialization at some other point
	 *	@param posX x position of the new Node
	 *	@param posY y position of the new Node
	 */
	public Node(int posX, int posY){
		points = new LinkedList<Point>();
		position = new Point(posX, posY);
	}
    
	/**
	 *	Prints the list out for testing purposes as a list of points
	 *	@return list of points that are in points
	 */
    public String toString(){
    	String s = "";    	
    		
    	for(int i = 0; i < points.size(); i++){
    		s += points.get(i);
    	}
    	
    	return s;
    }
	
	/**
	 * Adds a node to the left of this one, and updates all pointers adjacent
	 *	to the new Node
	 */
	public void addLeft(){
		Node n = new Node(position.x - 1, position.y);
		
		this.left = n;
		n.right = this;
		
		//check for node above
		if(this.up != null){
			if(this.up.left != null){
				this.up.left.down = n;
				n.up = this.up.left;
			
				//check for node on other side from above
				if(this.up.left.left != null){
					if(this.up.left.left.down != null){
						this.up.left.left.down.right = n;
						n.left = this.up.left.left.down;
					}
				}
			}
		}
		
		//check for node below
		if(this.down != null){
			if(this.down.left != null){
				this.down.left.up = n;
				n.down = this.down.left;
				
				//check for node on other side from above
				if(this.down.left.left != null){
					if(this.down.left.left.up != null){
						this.down.left.left.up.right = n;
						n.left = this.down.left.left.up;
					}
				}
			}
		}
	}
	
	/**
	 * Adds a node to the right of this one, and updates all pointers adjacent
	 *	to the new Node
	 */
	public void addRight(){
		Node n = new Node(position.x + 1, position.y);
		
		this.right = n;
		n.left = this;
		
		//check for node above
		if(this.up != null){
			if(this.up.right != null){
				this.up.right.down = n;
				n.up = this.up.right;
			
				//check for node on other side from above
				if(this.up.right.right != null){
					if(this.up.right.right.down != null){
						this.up.right.right.down.left = n;
						n.right = this.up.right.right.down;
					}
				}
			}
		}
		
		//check for node below
		if(this.down != null){
			if(this.down.right != null){
				this.down.right.up = n;
				n.down = this.down.right;
				
				//check for node on other side from above
				if(this.down.right.right != null){
					if(this.down.right.right.up != null){
						this.down.right.right.up.left = n;
						n.right = this.down.right.right.up;
					}
				}
			}
		}
	}
	
	/**
	 * Adds a node above this one, and updates all pointers adjacent
	 *	to the new Node
	 */
	public void addUp(){
		Node n = new Node(position.x, position.y - 1);
		
		this.up = n;
		n.down = this;
		
		//check for node to the right
		if(this.right != null){
			if(this.right.up != null){
				this.right.up.left = n;
				n.right = this.right.up;
			
				//check for node on other side from right
				if(this.right.up.up != null){
					if(this.right.up.up.left != null){
						this.right.up.up.left.down = n;
						n.up = this.right.up.up.left;
					}
				}
			}
		}
		
		//check for node to the left
		if(this.left != null){
			if(this.left.up != null){
				this.left.up.right = n;
				n.left = this.left.up;
				
				//check for node on other side from left
				if(this.left.up.up != null){
					if(this.left.up.up.right != null){
						this.left.up.up.right.down = n;
						n.up = this.left.up.up.right;
					}
				}
			}
		}
	}
	
	/**
	 * Adds a node below this one, and updates all pointers adjacent
	 *	to the new Node
	 */
	public void addDown(){
		Node n = new Node(position.x, position.y + 1);
		
		this.down = n;
		n.up = this;
		
		//check for node to the right
		if(this.right != null){
			if(this.right.down != null){
				this.right.down.left = n;
				n.right = this.right.down;
			
				//check for node on other side from right
				if(this.right.down.down != null){
					if(this.right.down.down.left != null){
						this.right.down.down.left.up = n;
						n.down = this.right.down.down.left;
					}
				}
			}
		}
		
		//check for node to the left
		if(this.left != null){
			if(this.left.down != null){
				this.left.down.right = n;
				n.left = this.left.down;
				
				//check for node on other side from left
				if(this.left.down.down != null){
					if(this.left.down.down.right != null){
						this.left.down.down.right.up = n;
						n.down = this.left.down.down.right;
					}
				}
			}
		}
	}
}