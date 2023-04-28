import java.util.LinkedList;
import java.util.List;

public class BacktrackingAVL extends AVLTree {
    // For clarity only, this is the default ctor created implicitly.
    public BacktrackingAVL() {
        super();
    }
    
	//You are to implement the function Backtrack.
    public void Backtrack() {
        
        if(!backtrack.isEmpty()) {
        	backtrack.pop();//removing the partition (type Boolean)
        	
        	//removing the nodes that were inserted to the deque for the 
        	//insert that is now being backtracked
            Node firstOut=(Node)backtrack.pop();
            
            if((!backtrack.isEmpty())&&!((backtrack.peek() instanceof Boolean))) {
            	Node secondOut=(Node)backtrack.pop();
            	if(backtrack.peek() instanceof Boolean)
            		backtrack(secondOut,firstOut,null);
            	else
            		backtrack((Node)backtrack.pop(),secondOut,firstOut);
            }
            else
            	backtrack(firstOut,null,null);
        }
        
    }
    /**
	 * help function backtrack
	 * @param node - the node that was the root of a sub-tree that was rotated
	 * this function uses rotation functions in AVL
	 */
    //This function is used to reverse the rotation 
    //in order to return the tree to it's original form
    private void reverserotation(Node node) {
    	Node p=node.parent.parent;
    	if(node.parent.value>node.value)
    		if(node.parent==root)
    			root=rotateRight(node.parent);
    		else {
    			if(p.value>node.parent.value)
    				p.left=rotateRight(node.parent);
    			else
    				p.right=rotateRight(node.parent);
    		}
    	else
    		if(node.parent==root)
    			root=rotateLeft(node.parent);
    		else {
    			if(p.value>node.parent.value)
    				p.left=rotateLeft(node.parent);
    			else
    				p.right=rotateLeft(node.parent);
    		}
    }
    /**
	 * help function Backtrack
	 * @param node - the node that was inserted
	 * @param rot1- the root of the sub-tree that was rotated (first rotation) (if there wasn't a rotation value is null)
	 * @param rot2- the root of the sub-tree that was rotated (second rotation)(if there wasn't a rotation value is null)
	 */
    //This function returns the tree to it's original form 
    //and deletes the last inserted node
    private void backtrack(Node node, Node rot1, Node rot2) {
    	
    	if(rot1!=null) {
    		reverserotation(rot1);
    	}
    	if(rot2!=null) {
    		reverserotation(rot2);
    	}
    	
    	if(node.value==root.value)
    		root=null;
    	else {
    		if(root.value==node.value)
    			root=null;
    		else {
    			Node p=node.parent;
        		if(node.parent.value>node.value)
        			node.parent.left=null;
        		else
        			node.parent.right=null;
        		
        		p.updateHeight();
    		}
    	}
    }
    //Change the list returned to a list of integers answering the requirements
    public static List<Integer> AVLTreeBacktrackingCounterExample() {

    	List <Integer> ans= new LinkedList<Integer>() ;
		ans.add(1);
		ans.add(2);
		ans.add(3);
		return ans;
    }
    /**
	 * @param index- the function needs to return the key of the node that has 
	 * index numbers smaller than it
	 */
    public int Select(int index) {
            return Select(index,root);
    }
    /**
	 * help function Select
	 * @param node - where we start going down in the tree (begins with the root)
	 * @param index - the number we are searching for
	 */
    //recursive function used to search for the the i'th number
    public int Select(int index, Node node) {
    	if(node.left==null) {
    		if(index==1)
    			return node.value;
    		else
    			return node.right.value;
    	}
    	else
    		if(node.left.size+1==index)
				return node.value;
    					else
    						if(node.left.size<index)
    			    			return Select(index-(node.left.size+1),node.right);
    						else
    							return Select(index,node.left);
    }
	/**
	 * 
	 * @param value - we want to find how many nodes with lower value than this there are
	 * @return - the amount of nodes in the tree that have lower value than the given value
	 */
	public int Rank(int value) {
		if(root==null)
			return 0;
		else
			return Rank(root,value);
	}

	/**
	 * help function to Rank
	 * @param node - where we start the going down in the tree (begins with the root)
	 * @param value - as Rank above
	 */
	private int Rank(Node node, int value) {
		if(node==null)
			return 0;
		else if (node.value>=value)
			return Rank(node.left, value);
		else { //node.value<value
			if(node.left!=null)
				return Rank(node.right, value)+node.left.size+1;
			else
				return Rank(node.right, value)+1;
		}
	}
}

