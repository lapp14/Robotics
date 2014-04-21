/**
 * @(#)Point.java
 *	Represents an (x, y) point in space. Uses integers only
 *	unlike the java Point
 *
 * @author Dan Lapp
 * @version 1.00 2014/4/15
 */
 
public class Point {
	
	public int x, y; //x, y positions in space

	/**
	 *	Initializes a null Point
	 */
    public Point() {
    }
    
	/**
	 *	Initializes a point to a location
	 */
    public Point(int x, int y) {
    	this.x = x;
    	this.y = y;	
    }
    
	/**
	 * @Override
	 * Prints a Point as a string as (x, y)
	 */
    public String toString(){
    	return "(" + x + ", " + y + ")";
    }
	
	//required for some reason by NXJ
	public static void main(String[] args){
	}
}