import java.util.LinkedList;
import java.util.List;

import javax.swing.plaf.basic.BasicTreeUI.TreeCancelEditingAction;
import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;


public class BacktrackingBTree<T extends Comparable<T>> extends BTree<T> {
	// For clarity only, this is the default ctor created implicitly.
	public BacktrackingBTree() {
		super();
	}

	public BacktrackingBTree(int order) {
		super(order);
	}
	/**
	 * backtracking the last insertion action
	 * goes from the node which the last inserted key got into, 
	 * in the path to the root until all the splits from the insertion got reversed
	 */
	public void Backtrack() {
		if(!stack.isEmpty()) { 
			T keytodelete= stack.pop();
			Boolean finishBacktrack=false;
			Node<T>currentNode=this.getNode((T)keytodelete); //find the node with the last inserted key
			currentNode.removeKey((T) keytodelete); //remove the last inserted key
			if(currentNode.equals(root)) { //if we got to the root- there's no more merged to do
				finishBacktrack=true;
			}
			else
				currentNode=currentNode.parent;
			while (!finishBacktrack) {
				T wassplitted=stack.pop();
				T median=stack.pop();
				if((Integer)wassplitted==1) //the relevant child in the path splitted 
					merge(currentNode, currentNode.indexOf((T)median), median);
				if(currentNode.numOfKeys==0) //we merged with root, update current
					currentNode=root;
				if(currentNode.equals(root)) {
					finishBacktrack=true;
				}
				else
					currentNode=currentNode.parent;
			}
			if(root.numOfKeys==0) // empty the tree if root doesn't have keys
				root=null;
		}
	}


	//Change the list returned to a list of integers answering the requirements
	public static List<Integer> BTreeBacktrackingCounterExample(){
		List <Integer> ans= new LinkedList<Integer>() ;
		ans.add(1);
		ans.add(2);
		ans.add(3);
		ans.add(4);
		ans.add(5);
		ans.add(10);
		ans.add(11);
		ans.add(12);
		ans.add(13);
		return ans;
	}
}
