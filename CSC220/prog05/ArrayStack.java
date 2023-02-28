package prog05;

import java.util.EmptyStackException;

import prog02.DirectoryEntry;

/** Implementation of the interface StackInterface<E> using an array.
*   @author vjm
*/

public class ArrayStack<E> implements StackInterface<E> {
  // Data Fields
  /** Storage for stack. */
  E[] theData;

  /** Number of elements in stack. */
  int size = 0;
  
  private static final int INITIAL_CAPACITY = 4;

  /** Construct an empty stack with the default initial capacity. */
  public ArrayStack () {
    theData = (E[])new Object[INITIAL_CAPACITY];
  }

  /** Pushes an item onto the top of the stack and returns the item
      pushed.
      @param obj The object to be inserted.
      @return The object inserted.
   */
  public E push (E obj) {
    // EXERCISE:  Check if array is full and do something about it.
	  if (theData[size] == null)
		  reallocate();

    // Putting the ++ after size means use its current value and then
    // increment it afterwards.
    theData[size++] = obj;
    return obj;
  }
  
  public void reallocate() {
	  E[] moreData = (E[])new Object[2 * theData.length];
		System.arraycopy(theData, 0, moreData, 0, theData.length);
		theData = moreData;
	  
  }

  /** Returns the object at the top of the stack and removes it.
      post: The stack is one item smaller.
      @return The object at the top of the stack.
      @throws EmptyStackException if stack is empty.
   */
  public E pop () {
    if (empty())
      throw new EmptyStackException();
    
    E popped = theData[size-1];
    size--;
    return popped;

  }

@Override
public E peek() {
	if (empty())
	      throw new EmptyStackException();
	return theData[size-1];
}

@Override
public boolean empty() {
	return size == 0;

  // EXERCISE
}
}
