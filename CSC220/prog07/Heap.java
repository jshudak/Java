package prog07;
import java.util.*;

public class Heap <E> extends AbstractQueue<E> {
  private List<E> theElements = new ArrayList<E>();

  public Heap () {}
  
  /** An optional reference to a Comparator object. */
  Comparator < E > comparator = null;

  /** Creates a heap-based priority queue with that orders its
      elements according to the specified comparator.
      @param comp The comparator used to order this priority queue
  */
  public Heap (Comparator < E > comp) {
    comparator = comp;
  }

  public int size () { return theElements.size(); }

  /** Compare the elements with index i and index j in theElements using
      either a Comparator object's compare method or their natural
      ordering using method compareTo.
      @param i index of first element in theElements
      @param j index of second element in theElements
      @return Negative int if first less than second,
      0 if first equals second,
      positive int if first > second
      @throws ClassCastException if elements are not Comparable
  */
  private int compare (int i, int j) {
    if (comparator == null)
      return ((Comparable<E>) theElements.get(i)).compareTo(theElements.get(j));
    else
      return comparator.compare(theElements.get(i), theElements.get(j));
  }

  /** Swap the elements with index i and index j in theElements.
      @param i index of first element in theElements
      @param j index of second element in theElements
  */
  private void swap (int i, int j) {
    E temp = theElements.get(i);
    theElements.set(i, theElements.get(j));
    theElements.set(j, temp);
  }
  
  private boolean hasLeft (int i) {
    return 2 * i + 1 < size();
  }
  
  private int getLeft (int i) {
    return 2 * i + 1;
  }
  
  private boolean hasRight (int i) {
    return 2 * i + 2 < size();
  }
  
  private int getRight (int i) {
    return 2 * i + 2;
  }
  
  private boolean hasParent (int i) {
    return i > 0;
  }

  private int getParent (int i) {
    if (!hasParent(i)) return -1;
    return (i - 1) / 2;
  }

  /** Insert an element into the priority queue.
      pre:  theElements is in heap order.
      post: The element is in the priority queue and
      theElements is in heap order.
      @param element The element to be inserted
      @throws NullPointerException if the element to be inserted is null.
  */
  public boolean offer (E element) {
    if (element == null)
      throw new NullPointerException();
    
    // Add the element to the end of the list.
    theElements.add(element);

    // index is the index of the element.
    int index = size() - 1;

    // While the element is less than its parent, swap it upwards.
    while (hasParent(index) && compare(index, getParent(index)) < 0) {
      swap(index, getParent(index));
      index = getParent(index);
    }

    return true;
  }

  /**
   * Returns the element at the front of the queue without removing it.
   * @return The element at the front of the queue if successful;
   * return null if the queue is empty
   */
  @Override
  public E peek () {
    if (size() == 0)
      return null;
    return theElements.get(0);
  }

  /** Remove an element from the priority queue
      pre: The ArrayList theElements is in heap order.
      post: Removed smallest element, theElements is in heap order.
      @return The element with the smallest priority value or null if empty.
  */
  public E poll() {
    // EXERCISE

    // Return null if the queue is empty.
	  if (size()==0) {
		  return null;
	  }
	  
	// If the queue contains only one element, then remove it and return it.	  
	  if (theElements.size() == 1) {
		 return theElements.remove(0);
	  }


    // Save the top of the heap in a variable.
    E result = theElements.get(0);

    /* Remove the last element from the List (what's the index?) and
       place it into the first position (what's the index?). */  
    theElements.set(0, theElements.remove(size()-1));



    int index = 0;
    // While the element at index is greater than one of its children...
    // (Use, hasLeft, getLeft, hasRight, getRight, and compare.)
    
    while(hasLeft(index) == true && compare(index, getLeft(index)) > 0 || hasRight(index) == true && compare(index, getRight(index)) > 0) {
    	      
      // If the right child is there and is lesser than the left child...
    	if (hasRight(index) == true && compare(getRight(index), getLeft(index)) < 0) {
    		
    		// Swap with right child and set index
    		swap(index, getRight(index));
    		index = getRight(index);
    	}

      // Else swap with left child and set index.
    	else {	
    		
    		swap(index, getLeft(index));
    		index = getLeft(index);
    	}

    }

    return result;
  }

  public boolean remove (Object o) {
    // EXERCISE
    if (false) // change this to false
      return false;

    // Get the index of the object to be removed.
    int index = theElements.indexOf(o);

    // If it is not there, how do you know?  What do you return?
    if (index == -1) {
    	return false;
    }



    // If it is the last element in the list, just remove it.  What do
    // you return?
    if (index == theElements.size()-1) {
    	theElements.remove(index);
    	return true;
    	
    }





    // Remove the last element in the list, and use it to replace the
    // element at index.  From now on, the element at index is now what used
    // to be the last element in the list.
    theElements.set(index, theElements.remove(theElements.size()-1));


    // While the element at index is less than its parent, swap it upward.
    while(hasParent(index) == true && (compare(index, getParent(index)) < 0)) {
    	swap(index, getParent(index));
    }





    // While the element at index is greater than one of its children,
    // swap it with its lesser child.
    while(hasLeft(index) == true && compare(index, getLeft(index)) > 0 || hasRight(index) == true && compare(index, getRight(index)) > 0) {
	      
        // If the right child is there and is lesser than the left child...
      	if (hasRight(index) == true && compare(getRight(index), getLeft(index)) < 0) {
      		
      		// Swap with right child and set index
      		swap(index, getRight(index));
      		index = getRight(index);
      	}

        // Else swap with left child and set index.
      	else {	
      		
      		swap(index, getLeft(index));
      		index = getLeft(index);
      	}

      }


    return true;
  }

  public Iterator<E> iterator () { return theElements.iterator(); }

  public String toString1 () {
    return toString(0, 0);
  }
  
  private String toString (int root, int indent) {
    if (root >= size())
      return "";
    String ret = toString(2 * root + 2, indent + 2);
    for (int i = 0; i < indent; i++)
      ret = ret + "  ";
    ret = ret + theElements.get(root) + "\n";
    ret = ret + toString(2 * root + 1, indent + 2);
    return ret;
  }

  public String toString () {
    if (size() == 0)
      return "\n";
    int w = width(0);
    List<String> list = toList(0, w);
    String s = "";
    for (String line : list)
      s += line + "\n";
    return s;
  }

  private int width (int root) {
    if (root >= size())
      return 0;
    String kv = "" + theElements.get(root);
    String s = spaces(kv.length());
    int wl = width(getLeft(root));
    int wr = width(getRight(root));
    int wmax = wl > wr ? wl : wr;
    return kv.length() + 2 * wmax;
  }

  private List<String> toList (int root, int width) {
    String kv = "" + theElements.get(root);
    String skv = spaces(kv.length());
    int width2 = (width - kv.length()) / 2;
    String sw = spaces(width2);
    List<String> out = new ArrayList<String>();
    out.add(sw + kv + sw);
    if (getLeft(root) >= size() && getRight(root) >= size()) {
      return out;
    }
    if (getLeft(root) >= size()) {
      List<String> right = toList(getRight(root), width2);
      for (String r : right)
        out.add(sw + skv + r);
      return out;
    }
    if (getRight(root) >= size()) {
      List<String> left = toList(getLeft(root), width2);
      for (String l : left)
        out.add(l + skv + sw);
      return out;
    }
    List<String> left = toList(getLeft(root), width2);
    List<String> right = toList(getRight(root), width2);
    for (int i = 0; i < left.size() && i < right.size(); i++)
      out.add(left.get(i) + skv + right.get(i));
    if (left.size() > right.size()) {
      for (int i = right.size(); i < left.size(); i++)
        out.add(left.get(i) + skv + sw);
    }
    if (left.size() < right.size()) {
      for (int i = left.size(); i < right.size(); i++)
        out.add(sw + skv + right.get(i));
    }
    return out;
  }    

  String spaces (int n) {
    String s = "";
    for (int i = 0; i < n; i++)
      s += " ";
    return s;
  }

  public static void main (String[] args) {
    int[] data = { 3, 1, 4, 1, 5, 9, 2, 6 };
    Heap<Integer> queue = new Heap<Integer>();
    
    for (int i = 0; i < data.length; i++) {
      System.out.println("offer " + data[i]);
      queue.offer(data[i]);
      System.out.println(queue);
      System.out.println();
      if (queue.remove(data[i])) {
        System.out.println("remove " + data[i]);
        System.out.println(queue);
        System.out.println();
        System.out.println("offer " + data[i]);
        queue.offer(data[i]);
        System.out.println(queue);
        System.out.println();
      }
    }
    for (int i = 0; i < data.length; i++) {
      System.out.println("poll returns " + queue.poll());
      System.out.println(queue);
      System.out.println();
    }

    int[] data2 = { 1, 2, 7, 3, 4, 8, 9, 5 };
    for (int i = 0; i < data2.length; i++)
      queue.offer(data2[i]);
    System.out.println("offer 1 2 7 3 4 8 9 5");
    System.out.println(queue);
    System.out.println();
    System.out.println("remove 8");
    queue.remove(8);
    System.out.println(queue);
    System.out.println();
    System.out.println("remove 1");
    queue.remove(1);
    System.out.println(queue);
    System.out.println();
  }
}

