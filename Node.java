/**
 * @(#)Node.java
 *
 *
 * @author 
 * @version 1.00 2014/4/13
 */

import java.util.BitSet;

public class Node {

    public BitSet data;
	public Node north, south, east, west;
	
	/**
	 *	The Bitset represents a 16x16 array of bits
	 **/
	
	public Node(){
		data = new BitSet(256);
	}
	   
	public static int getIndex(int x, int y){
		return x * 16 + y;
	}
    
    public String toString(){
    	String s = "";    	
    		
    	for(int i = 0; i < 256; i++){
    		if(data.get(i))
    			s += "1";
    		else
    			s += "0";
    		
    		if(i % 16 == 15)
    			s += "\n";
    	}
    	
    	return s;
    }

    
}