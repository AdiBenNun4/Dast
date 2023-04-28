

public class BacktrackingSortedArray implements Array<Integer>, Backtrack {
    private Stack stack;
    public int[] arr; // This field is public for grading purposes. By coding conventions and best practice it should be private.
    // TODO: implement your code here
    int size;
    
    // Do not change the constructor's signature
    public BacktrackingSortedArray(Stack stack, int size) {
        this.stack = stack;
        arr = new int[size];
        size=0;
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
		int left=0, right=size-1,middle;
		while(left<=right) { // have more values in the range to check
			middle=(left+right)/2;
			if(arr[middle]==k)//we found the key
				return middle;
			else if(arr[middle]<k) 
				left=middle+1;	
			else 
				right=middle-1;
		}
		return -1; 
	}

    @Override
    /**
	 * @param k - the key we want to insert
	 */
    //The function inserts the key in the correct place in the array in order to maintain a sorted array
    public void insert(Integer x) {
    	//if array is full-throw exception
        if(size==arr.length)
    	   throw new ArrayIndexOutOfBoundsException();
        //case 1:array is empty
    	if(size==0)	{
    		arr[0]=x;
    		stack.push(x);
    		stack.push(0);
    	}
    	else { //case 2
    		int start=1, end=size-1, index=-1;
    		if(x<arr[0])
    			index=0;
    		else if(arr[size-1]<x)
    			index=size;
    		//using binary search in order to find the correct place for the new key
    		while(start<=end&index==-1) {
    			int middle=(start+end)/2;
    			if(arr[middle]<x)
    				if(arr[middle+1]>x)
    					index=middle+1;
    				else
    					start=middle+1;
    			else
    				if(arr[middle-1]<x)
    					index=middle;
    				else
    					end=middle-1;
    		}
    		
    		shiftright(index);
    		arr[index]=x;
    		stack.push(x);
    		stack.push(index);
    	}
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
		shiftleft(index);
		size--;
	}

    @Override
    /**
	 * @throws IllegalArgumentException - if the array is empty and it doesn't have minimum
	 * @return the index of the smallest key in the array
	 */
    public Integer minimum() {
        if(size==0)
        	throw new IllegalArgumentException();
    	
    	return 0;
    }

    @Override
    /**
	 * @throws IllegalArgumentException - if the array is empty and don't have maximum
	 * @return the index of the biggest key in the array
	 */
	public Integer maximum() {
		if(size<1)
			throw new IllegalArgumentException();
		return size-1; 
	}

    @Override
    /**
	 * @throws IllegalArgumentException - if the key doesn't have a successor
	 * @return the value of the successor to the key in the index 'index'
	 */
    public Integer successor(Integer index) {
        if(index>=size-1)
        	throw new IllegalArgumentException();
        
        return index+1;
    }

    @Override
    /**
   	 * @throws IllegalArgumentException - if the key doesn't have a successor
   	 * @return the value of the predecessor to the key in the index 'index'
   	 */
    public Integer predecessor(Integer index) {
    	if(index<=0)
    		throw new IllegalArgumentException();
        
        return index-1;
    }

    @Override
    //reversing the state of the array to the condition it was before the last change made
    public void backtrack() {
    	// if(stack.isEmpty())
    	//exception
	
    	int index=(Integer)stack.pop();
    	int key=(Integer)stack.pop();
    	if(arr[index]==key&index<size) {
    		shiftleft(index);
    		size--;
    	}
    	else {
    		shiftright(index);
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
		output=output.substring(0,output.length()-1);
		System.out.println(output);
    }
    /**
	 * @param index - The index we need to shift the keys from it 
	 */
	//assisting function that shift the keys in the array to index (included), one spot left 
	private void shiftleft(int index) {
		for (int i = index+1; i < size ; i++) 
			arr[i-1]=arr[i];
	}
	/**
	 *  @param index- The index we need to shift the keys from it 
	 */
	//assisting function that shift the keys in the array from index, one spot right 
	private void shiftright(int index) {
		if(size==arr.length)
			throw new IllegalArgumentException("can't shift, dont have space in the array");
		for (int i = size-1; i >=index ; i--) 
			arr[i+1]=arr[i];
	}
	

    
}
