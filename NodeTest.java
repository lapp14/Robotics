/**
 * @(#)NodeTest.java
 *
 *
 * @author 
 * @version 1.00 2014/4/15
 */


public class NodeTest {

    public NodeTest() {
    }
    
    public static void main(String[] args){
    	
    	Node start= new Node();
    	
    	start.addUp();
    	start.addDown();
    	
    	start.down.addLeft();
    	start.up.addLeft();
    	
    	start.down.left.addLeft();
    	start.up.left.addLeft();
    	
    	start.up.left.left.addDown();
    	
    	start.addLeft();
    	
    	System.out.println(start.left.left.position);
    	
    }
    
}