package prog06;

import java.util.Queue;
import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implements the Queue interface using a singly-linked list.
 **/
public class LinkedQueue<E> extends AbstractQueue<E>
  implements Queue<E> {

  // Data Fields
  /** Reference to the first element in the queue. */
  protected Entry<E> first;
  /** Reference to the last element in the queue. */
  protected Entry<E> last;
  /** Size of queue. */
  protected int size;

  /** A Entry is the building block for a singly-linked list. */
  public static class Entry<E> {
    // Data Fields

    /** The reference to the element. */
    protected E element;
    /** The reference to the next entry. */
    protected Entry<E> next;

    // Constructors
    /**
     * Creates a new entry with a null next field.
     * @param element The element stored
     */
    protected Entry (E element) {
      this.element = element;
      next = null;
    }

    /**
     * Creates a new entry that references another entry.
     * @param element The element stored
     * @param next The entry referenced by new entry
     */
    protected Entry (E element, Entry<E> next) {
      this.element = element;
      this.next = next;
    }
  } //end class Entry

  // Methods
  /**
   * Insert an element as the last in the queue.
   * @post element is added as last and size is incremented.
   * @param element The element to add.
   * @return true (always successful)
   */
  @Override
  public boolean offer (E element) {
	Entry toAdd = new Entry(element);
	
	if (size > 0) {
	last.next = toAdd;
	}
	
	else if (size == 0) {
		first = toAdd;
	}
	
	last = toAdd;
	size++;
    return true;
  }

  /**
   * Return the element the first element in the queue without removing it.
   * @return The first element if successful;
   * return null if the queue is empty.
   */
  @Override
  public E peek () {
	  if (isEmpty())
          return null;

       else
          return first.element;
  }

  /**
   * Remove the first element of the queue and return it
   * if the queue is not empty.
   * @post first references element that was second in the queue and
   * size is decremented.
   * @return The element removed if successful, or null if not.
   */
  @Override
  public E poll () {
    if (isEmpty()) {
    	return null;
    }
    
    else {
    	E temp = first.element;
    	first = first.next;
    	size--;
    	
    	if (size == 0) {
    		last = null;
    		first = null;
    	}
    	
    	return temp;
    }
  }

  /**
   * Returns the size of the queue
   * @return the size of the queue
   */
  @Override
  public int size () {
    return size;
  }

  /**
   * Returns an iterator to the contents of this queue
   * @return an iterator to the contents of this queue
   */
  public Iterator<E> iterator () {
    return new Iter();
  }

  /**
   * Inner class to provide an iterator to the contents of
   * the queue.
   */
  protected class Iter implements Iterator<E> {
    // Data Fields
	  protected Entry entry;
    // EXERCISE

    // Constructor
    public Iter () {
    	if (isEmpty() == true) {
    		entry = null;
    	} 	
	    	
	    else {
	    	  entry = first;
	    }
	 }
    // Methods
    /**
     * Returns true if there are more elements in the iteration
     * @return true if there are more elements in the iteration
     */
    @Override
    public boolean hasNext () {
        if (entry != null) {
      	  return true;
        }
        
        
        else {
      	  return false;
        }
      }
    /**
     * Return the next element in the iteration and advance the iterator
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public E next () {
        E data = (E)entry.element;
        
        if (entry != last) {
      	  entry = entry.next;
        }
        
        
        else {
        entry = null;
        }
        
        return data;
      }
    /**
     * Removes the last element returned by this iteration
     * @throws UnsupportedOperationException this operation is not
     * supported
     */
    @Override
    public void remove () {
      throw new UnsupportedOperationException();
    }
  }
}
/*</listing>*/
