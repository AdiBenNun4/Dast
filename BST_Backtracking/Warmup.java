import java.util.Iterator;

public class Warmup {
	/** 
	 * This method return the index of x in arr
	 * @param arr - unsorted array
	 * @param x - the value we are looking for
	 * @param forward - amount of the next steps
	 * @param back - amount of steps to return backward
	 * @param myStack - assisting stack for the backtrack
	 * @param counter- the cells we have already checked
	 * @return index of x or -1 if not exist
	 * assumes forward>back
	 */
	public static int backtrackingSearch(int[] arr, int x, int forward, int back, Stack myStack) {
		int pointer=0, counter=0;
		if(arr.length==0) //if the array is empty- x does not exist
			return -1;

		while(pointer < arr.length) {
			for (int i = 0; i < forward & pointer<arr.length; i++) { //forward steps
				if(pointer<=counter) { //if arr[pointer] havn't been checked
					if(arr[pointer]==x)
						return pointer;	
					else 
						counter++;
				}
				myStack.push(arr[pointer]);
				pointer++; //step forward
			}
			for (int j = 0; j < back & pointer<arr.length ; j++) { //steps backward
				myStack.pop();
				pointer--;
			}
		}
		return -1; 
	}
	
	/**
	 * 
	 * @param arr - sorted array
	 * @param x - the value we are looking for
	 * @param myStack - assisting stack for the backtrack
	 * @return index of x or -1 if not exist
	 */
	public static int consistentBinSearch(int[] arr, int x, Stack myStack) {
		int left=0, right=arr.length-1,middle, inconsistencies=0;

		while(left<=right) { // have more values in the range to check
			if(inconsistencies==0) {//don't need to check incosistencies
				middle=(left+right)/2;
				if(arr[middle]==x)//we found the key
					return middle;
				else if(arr[middle]<x) { 
					myStack.push(left); //remember we moved the left
					left=middle+1;	
				}
				else {
					myStack.push(right);//remember we moved the right
					right=middle-1;
				}
			}
			else {//need to undo steps
				for(int i=0 ; i<inconsistencies & !myStack.isEmpty() ; i++) {
					int prev=(Integer)myStack.pop();
					if(prev<left)
						left=prev;
					else
						right=prev;
				}
				inconsistencies = Consistency.isConsistent(arr);
			}
		}

		return -1; 
	}


}

