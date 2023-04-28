

import java.util.NoSuchElementException;

public class BacktrackingBST implements Backtrack, ADTSet<BacktrackingBST.Node> {
    private Stack stack;
    private Stack redoStack;
    private BacktrackingBST.Node root = null;
  //The modes states the stacks operations:
  	//0- The keys enter to Backtrack stack, redo stack is empty
  	//1- There was backtrack action, the keys from backtrack stack enter to redo stack
  	//2- The redo was activates, the keys from redo enter to backtrack stack
  	private int mode; 

	// Do not change the constructor's signature
	public BacktrackingBST(Stack stack, Stack redoStack) {
		this.stack = stack;
		this.redoStack = redoStack;
		this.mode=0;
	}

	public Node getRoot() {
		if (root == null) {
			throw new NoSuchElementException("empty tree has no root");
		}
		return root;
	}
	/**
	 * @return pointer to the node with key k, null if doesn't exist
	 */
	public Node search(int k) {
		Node x=root;
		while(x!=null) { //going down in the tree as long as the key hasn't been found
			if(x.key==k)
				return x;
			else if(x.key<k)
				x=x.right;
			else
				x=x.left;
		}
		return null; 
	}
	/**
	 * 
	 * @param node - the node we need to insert into the tree
	 */
	//this function inserts the node and to the 
	//tree and updates the stacks according to the mode
	public void insert(Node node) {
		if(root==null)
			root=node;
		else {//in case of backtracking a delete
			if(node.left!=null|node.right!=null) {
					Node leaf=insertInnerNode(node);
					if(leaf!=null)
						insert(leaf, node);
			}
			else insert(node, root);
		}
		if(mode==1) {
			redoStack.push(node);
			redoStack.push(0);
		}
		else {
			if(mode==0&(!redoStack.isEmpty())) {
				redoStack=null;
				redoStack=new Stack();
			}
			stack.push(node);
			stack.push(0);

		}
	}
	/**
	 * 
	 * @param node - the node we need to insert
	 * @param tree-we need to insert the node to tree's sub-tree
	 */
	//this function inserts the node in the correct place according to the key size
	private void insert(Node node, Node tree) {
		if(tree.key>node.key)
			if(tree.left==null) {
				tree.left=node;
				node.parent=tree;
			}
			else
				insert(node,tree.left);
		else
			if(tree.right==null) {
				tree.right=node;
				node.parent=tree;
			}
			else
				insert(node,tree.right);    	
	}
	/**
	 * 
	 * @param node - the node we need to insert to the tree as a middle node
	 */
	//this function is used to change places with a node that replaced him during delete
	//it will be used only when calling backtracking or retracking
	private Node insertInnerNode(Node node) {
		Node leaf;
		if(node.parent==null) {
			leaf=root;
			root=node;
		}
		else 
			if(node.parent.key<node.key) {
			leaf=node.parent.right;
			node.parent.right=node;
			}
			else {
			leaf=node.parent.left;
			node.parent.left=node;
			}
		
		//making sure the leaf will enter back to the 
		//tree without node's children and that node's children point
		//to him as their parent
		if(node.left!=null) {
			node.left.parent=node;
			//removing the node's left son from leaf
			if(node.left.key==leaf.left.key)
				leaf.left=null;
		}
		
		if(node.right!=null) 
			node.right.parent=node;
		
		//these are cases where leaf that replaced node was his child
		//and therefore doesn't need to be inserted back into the tree
		if(node.left==null|node.right==null||node.right.key==leaf.key)
			return null;
		else {//turning node back to leaf
			leaf.left=null;
			leaf.right=null;
		}
		
		
		return leaf;
	
	}
    /**
	 * @param node - the node we need to delete from the tree
	 * @param isInnerDelete - mark if this is recursive call according to delete inner node with 2 children
	 */
	private void deleteH(Node node) {
		//step 2- Delete the node from the tree
		//case 1- node is a leaf
		if(node.left==null & node.right==null) {
			if(node.equals(root))
				root=null;
			else { //the node is not the root
				if(IsLeftChild(node))
					node.parent.left=null;
				else
					node.parent.right=null; //node is the right child
			}

		}
		//case 2- node has 1 child
		else if(node.left!=null & node.right==null){//node have just left child
			if(node.equals(root)) {
				root=node.left;
				root.parent=null;
			}
			else {//update the parent of node
				if(IsLeftChild(node)) 
					node.parent.left=node.left;
				else 
					node.parent.right=node.left;
				node.left.parent=node.parent;
			}
		}
		else if(node.left==null & node.right!=null){//node have just right child
			if(root.equals(node)){
				root=node.right;
				root.parent=null;
			}
			else {//update the parent of node
				if(IsLeftChild(node))
					node.parent.left=node.right;
				else
					node.parent.right=node.right;
				node.right.parent=node.parent;
			}
		}
		else { //Node has 2 children
			Node successorNode=successor(node);
			Node rightsubtreeNode=node.right;
			Boolean isSucRightChilde=false; //Remark if successor is child of node
			if(node.right.equals(successorNode)) 
				isSucRightChilde=true;
			deleteH(successorNode); //delete temporary the successor
			//insert successor in node place
			successorNode.left=node.left;
			successorNode.right=node.right;
			if(node.parent!=null) //node isn't the root
				if(IsLeftChild(node))
					node.parent.left=successorNode;
				else
					node.parent.right=successorNode;
			else {
				root=successorNode;
				root.parent=null;
			}
			//update parent field in successor and his children
			if(node.parent!=null)
				successorNode.parent=node.parent;
			if(successorNode.left!=null)
				successorNode.left.parent=successorNode;
			if(successorNode.right!=null)
				successorNode.right.parent=successorNode;
			if(isSucRightChilde)
				node.right=rightsubtreeNode;

		}

	}

	/**
	 * @param node- the node we need to delete from the tree
	 *the function calling to the assisting function to do the mission
	 */
	public void delete(Node node) {
		//Step 1- push the node and 1 to the right stack (according to the mode) for the backtracking
		//Do it if it's a regular delete action and not part of recalling the function recursively 

		if(mode==0 | mode==2) {
			stack.push(node);
			stack.push(1); 
		}
		else { //mode 1
			redoStack.push(node);
			redoStack.push(1); //mark the node was deleted (not inserted)
		}
		if(mode==0)
			if(!redoStack.isEmpty()) //Empty the redo stack if we are in mode 0
				redoStack=new Stack();

		deleteH(node);
	}





	private boolean IsLeftChild(Node x) {
		if(x.equals(x.parent.left)) //node is the left child
			return true;
		return false; //node is the right child
	}

	/**
	 * @throws IllegalArgumentException if the tree is empty
	 * @return pointer to the node with the minimum key value 
	 */
	public Node minimum() {
		if(root==null) 
			throw new IllegalArgumentException();
		Node x=root;
		return minimum(x);
	}

	//Assisting function to find the minimum in sub Tree
	private Node minimum(Node x) {
		if(x.left==null)
			return x;
		else
			return minimum(x.left);
	}

	/**
	 * @throws IllegalArgumentException if the tree is empty
	 * @return pointer to the node with the maximum key value 
	 */
	public Node maximum() {
		if(root==null)
			throw new IllegalArgumentException();
		return maximum(root);
	}
	public Node maximum(Node node) {
		if(node.right!=null)
			return maximum(node.right);
		else
			return node;
	}

	/**
	 * @param node - we need to return its successor
	 * @throws IllegalArgumentException - if node doesn't have successor
	 * @return the successor of node
	 */
	public Node successor(Node node) {
		if(node.right!=null) //if it has right sub tree, the successor its the minimum of it
			return minimum(node.right);
		else { //the successor is placed in the first right ancestor
			Node parentNode=node.parent;
			while(parentNode!=null && parentNode.right==node) {
				node=parentNode;
				parentNode=parentNode.parent;
			}
			if(parentNode==null)
				throw new IllegalArgumentException();
			return parentNode;
		}
	}

	/**
	 * @param node - we need to return its predecessor
	 * @throws IllegalArgumentException - if node doesn't have predecessor
	 * @return the predecessor of the node
	 */
	public Node predecessor(Node node) {
		if(node.left!=null)
			return maximum(node.left);
		
		while(node.parent!=null&&node.parent.right!=node)
			node=node.parent;

		if(node.parent==null)
			throw new IllegalArgumentException();

		return node.parent;
	}

	@Override
	//The stack contains pointer to nodes and above each on integer. 
	//The integers represents the action that was done on the nodes- 1 for delete, 0 for insert.
public void backtrack() {
		if(!stack.isEmpty()) {
			mode=1; //backtrack was activated
			int action=(Integer) stack.pop();
			Node x=(Node) stack.pop();
			if(action==1)  //The node was deleted, Backtrack=insert 
				this.insert(x);
			else  //The node was deleted, Backtrack=insert 
				this.delete(x);
			mode=0;
		}
	}

	@Override
	public void retrack() {
		int action=(Integer)redoStack.pop();
		Node node=(Node)redoStack.pop();
		mode=2;
		if(action==0)
			delete(node);
		else
			insert(node);
		mode=0;
	}

	//prints the tree in pre-order
	public void printPreOrder(){
		String print="";
		if(root!=null) {
			print=printPreOrder(root);
			print=print.substring(0,print.length()-1);
		}
		System.out.println(print);
	}
	//creating the String
	public String printPreOrder(Node node) {
		String print=node.key+" ";
		if(node.left!=null)
			print=print+printPreOrder(node.left);
		if(node.right!=null)
			print=print+printPreOrder(node.right);

		return print;
	}
	@Override
	public void print() {
		printPreOrder();
	}

	public static class Node {
		// These fields are public for grading purposes. By coding conventions and best practice they should be private.
		public BacktrackingBST.Node left;
		public BacktrackingBST.Node right;

		private BacktrackingBST.Node parent;
		private int key;
		private Object value;

		public Node(int key, Object value) {
			this.key = key;
			this.value = value;
		}

		public int getKey() {
			return key;
		}

		public Object getValue() {
			return value;
		}

	}

}
