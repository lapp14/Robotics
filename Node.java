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
	public Node up, down, left, right;
	
	/**
	 *	The Bitset represents a 32X32 array of bits
	 **/
	
	public Node(){
		data = new BitSet(512);
	}
	   
	public static int getIndex(int x, int y){
		return x * 32 + y;
	}
    
    public String toString(){
    	String s = "";    	
    		
    	for(int i = 0; i < 512; i++){
    		if(data.get(i))
    			s += "1";
    		else
    			s += "0";
    		
    		if(i % 32 == 31)
    			s += "\n";
    	}
    	
    	return s;
    }
	
	public void addLeft(){
		Node n = new Node();
		
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
	
	public void addRight(){
		Node n = new Node();
		
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
}