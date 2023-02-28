package prog05;

import java.util.EmptyStackException;

/** Class to implement interface StackInterface<E> as a linked list.
*   @author vjm
* */

public class LinkedStack<E> implements StackInterface<E> {

  /** This Entry is the building block for a single-linked list.

      It has a next but no previous.

      Since we declare it inside LinkedStack, we can just call it
      Entry instead of LLEntry.
   */
  private static class Entry<E> {
    // Data Fields
    /** The reference to the data. */
    private E data;

    /** The reference to the next node. */
    private Entry next;

    // Constructors
    /** Creates a new node with a null next field.
        @param data The data stored
     */
    private Entry (E data) {
      this.data = data;
      next = null; // Necessary in C++ but not in Java.
    }

    /** Creates a new node that references another node.
        @param data The data stored
        @param next The next node referenced by new node.
     */
    private Entry (E data, Entry<E> next) {
      this.data = data;
      this.next = next;
    }
  } //end class Entry

  // Data Fields
  /** The reference to the top stack node. */
  private Entry<E> first = null;

  /** Pushes an item onto the top of the stack and returns the item
      pushed.
      @param obj The object to be inserted.
      @return The object inserted.
   */
  public E push (E obj) {
    first = new Entry<E>(obj, first);
    return obj;
  }

@Override
public E peek() {
	if (empty())
	      throw new EmptyStackException();
	return first.data;
}

@Override
public E pop() {
	    if (empty())
	      throw new EmptyStackException();
	    
	    E popped = first.data;
	    first = first.next;
	    return popped;

	  }

@Override
public boolean empty() {
	return first == null;
}

  /**** EXERCISE ****/
}
