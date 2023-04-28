

public class BacktrackingArray implements Array<Integer>, Backtrack {
    private Stack stack;
    private int[] arr;
    // TODO: implement your code here
    int size; //points to the next available index in the array 

    // Do not change the constructor's signature
    public BacktrackingArray(Stack stack, int size) {
        this.stack = stack;
        arr = new int[size];
        this.size=0;
    }

    @Override
    /**
	 * @param index - the index of the value we want to return
	 * @return value of index in array, -1 if doesn't exist
	 */
    public Integer get(int index){
		if(index>=size|index<0)
			throw new IllegalArgumentException();
		return arr[index];
	}

    @Override
    /**
	 * @param k - the key we are looking for in the array
	 * @return the index of the key, -1 if doesn't exist
	 */
    public Integer search(int k) {
    	//iterating through the array to find the key
        for(int i=0; i<size; i++)
        	if(arr[i]==k)
        		return i;
        return -1;
    }

    @Override
    /**
	 * @param k - the key we want to insert
	 */
    public void insert(Integer x) {
       if(size==arr.length)
    	   throw new ArrayIndexOutOfBoundsException();
       
    	arr[size]=x;//adding the key
    	stack.push(x);
    	stack.push(size);
    	size++;
    }
	
    @Override
    /**
	 * @param index - the index of the key we need to delete
	 */
	//The function deletes the key in the given index
    public void delete(Integer index) {
    	if(index>=size|index<0)
			throw new IllegalArgumentException();
		stack.push(arr[index]);
		stack.push(index);
		if(size!=1 | size-1==index)
			arr[index]=arr[size-1];
		size--;
    }

    @Override
    /**
	 * @throws IllegalArgumentException - if the array is empty and don't have maximum
	 * @return the index of the biggest key in the array
	 */
    public Integer minimum() {
       if(size==0)
    	   throw new IllegalArgumentException();
    	
    	int min=arr[0];
    	for(int i=1; i<size; i++)
    		if(arr[i]<min)
    			min=arr[i];
    	return min;
    }
    
    @Override
    /**
	 * @throws IllegalArgumentException - if the array is empty and does't have maximum
	 * @return the index of the biggest key in the array
	 */
    public Integer maximum() {
    	if(size<1)
			throw new IllegalArgumentException();
		int maxkey=arr[0],maxindex=0;
		for (int i = 0; i < arr.length; i++) {
			if(arr[i]>maxkey) {
				maxkey=arr[i];
				maxindex=i;
			}
		}
		return maxindex; 
    }

    @Override
    /**
   	 * @throws IllegalArgumentException - if the key doesn't have a successor
   	 * @return the value of the successor to the key in the index 'index'
   	 */
    public Integer successor(Integer index) {
        if(index>size)
        	throw new IllegalArgumentException();
    	
    	int successor=-1;
    	//iterating through the array
    	for(int i=0; i<size; i++) {
    		if(arr[i]>arr[index]) 
    			if(successor==-1)
    				successor=i;
    			else if(arr[successor]>arr[i])
    				successor=i;
    		if(successor!=-1&&successor==arr[index]+1)
    			return successor;
    	}
    		
    	
    	if(successor==-1)
    		throw new IllegalArgumentException();
    	
    	return successor;
    }

    @Override
    /**
   	 * @throws IllegalArgumentException - if the key doesn't have a successor
   	 * @return the value of the predecessor to the key in the index 'index'
   	 */
    public Integer predecessor(Integer index) {
        if(index>size)
        	throw new IllegalArgumentException();
        
    	int predecessor=-1;
    	for(int i=0; i<size; i++) {
    		if(arr[i]<arr[index])
    			if(predecessor==-1)
    				predecessor=i;
    			else if(arr[predecessor]<arr[i])
    				predecessor=i;
    		if(predecessor!=-1&&arr[predecessor]==arr[index]-1)
    			return predecessor;
    	}
    	
    	if(predecessor==-1)
    		throw new IllegalArgumentException();
    	
    	return predecessor;

    }


    @Override
  //reversing the state of the array to the condition it was before the last change made
    public void backtrack() {
       // if(stack.isEmpty())
        	//exception
    	
    	int index=(Integer)stack.pop();
    	int key=(Integer)stack.pop();
    	if(index==size){ //was delete' we need to insert
    		arr[size]=key;
    		size++;
    	}
    	else if(arr[index]==key&index<size)  //we need to delete (backtrack the insert)
    		size--;
    	else { //we need to insert (backtrack the delete) 
    		arr[size]=arr[index];
    		arr[index]=key;
    		size++;
    	}
    }

    @Override
    public void retrack() {
		/////////////////////////////////////
		// Do not implement anything here! //
		/////////////////////////////////////
    }

    @Override
   public void print() {
    	String output="";
		for (int i = 0; i < size; i++) {
			output+=arr[i]+" ";
		}
		if(output.length()>0)
			output=output.substring(0,output.length()-1);
		System.out.println(output);
    }

}
