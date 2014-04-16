/**
 * @(#)Point.java
 *
 *
 * @author 
 * @version 1.00 2014/4/15
 */


public class Point {
	
	public int x, y;

    public Point() {
    	
    }
    
    public Point(int x, int y) {
    	this.x = x;
    	this.y = y;	
    }
    
    public String toString(){
    	return "(" + x + ", " + y + ")";
    }
	
	public static void main(String[] args){
	}
}