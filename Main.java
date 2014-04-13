/**
 * @(#)Main.java
 *
 *
 * @author 
 * @version 1.00 2014/4/13
 */


public class Main {

    public Main() {
    }
    
	public static void main(String[] args){
    	
    	Node n = new Node();
    	
    	for(int i = 0; i < 16; i++){
    		n.data.set(Node.getIndex(0, i));
    		
    		n.data.set(Node.getIndex(i, 0));
    		n.data.set(Node.getIndex(i, 1));
    	}
    	
    	System.out.println(n);
    }
    
    
}