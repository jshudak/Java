package prog06;

import java.util.*;

/**
 * Implements the Queue interface using a circular array (ring buffer).
 **/
public class ArrayQueue<E> extends AbstractQueue<E>
  implements Queue<E> {

  // Data Fields
  /** Index of the first element of the queue. */
  protected int first;
  /** Index of the last element of the queue. */
  protected int last;
  /** Current size of the queue. */
  protected int size;
  /** Default capacity of the queue. */
  protected static final int DEFAULT_CAPACITY = 5;
  /** Array to hold the elements. */
  protected E[] theElements;

  // Constructors
  /**
   * Construct a queue with the default initial capacity.
   */
  public ArrayQueue () {
    this(DEFAULT_CAPACITY);
  }

  /**
   * Construct a queue with the specified initial capacity.
   * @param initCapacity The initial capacity
   */
  @SuppressWarnings("unchecked")
  public ArrayQueue (int initCapacity) {
    theElements = (E[]) new Object[initCapacity];
    first = 0;
    last = theElements.length - 1;
    size = 0;
  }

  // Public Methods
  /**
   * Inserts a new element as last.
   * @post element is added as last.
   * @param element The element to add.
   * @return true (always successful)
   */
  @Override
  public boolean offer (E element) {
    // EXERCISE
	if (size == theElements.length) {
		reallocate();
		}
	
    // Increment last, but if it goes past the end, set it to zero.
    last = (last + 1) % theElements.length;
    // Store the new element at index last.
    theElements[last] = element;
    // Increment size.
    size++;
    return true;
  }

  /**
   * Returns the first element queue without removing it.
   * @return The first element of the queue (if successful).
   * return null if the queue is empty
   */
  @Override
  public E peek () {
    if (isEmpty())
      return null;
    return theElements[first];
  }

  /**
   * Removes the entry at the first of the queue and returns it
   * if the queue is not empty.
   * @post first references element that was second in the queue.
   * @return The element removed if successful or null if not
   */
  @Override
  public E poll () {
	  if (isEmpty()) {
		  return null;		// Only if it is empty.
	  }
	 
	  else {
		  E takenEntry = theElements[first];
		  size--;
		  first  = (first + 1)%theElements.length;
		  return takenEntry;
	  }
  }

  /**
   * Return the size of the queue
   * @return The number of elements in the queue
   */
  @Override
  public int size () {
    return size;
  }

  /**
   * Returns an iterator to the elements in the queue
   * @return an iterator to the elements in the queue
   */
  @Override
  public Iterator<E> iterator () {
    return new Iter();
  }
    
  // Protected Methods
  /**
   * Double the capacity and reallocate the elements.
   * @pre The array is filled to capacity.
   * @post The capacity is doubled and the first half of the
   *       expanded array is filled with elements.
   */
  @SuppressWarnings("unchecked")
  protected void reallocate () {
    int newCapacity = 2 * theElements.length;
    E[] newElements = (E[]) new Object[newCapacity];
    
    for (int i = 0; i < size; i++) {
    		newElements[i]  = theElements[first];
    		first = (first+1)%theElements.length;
    }
    
    first = 0;
    last = size-1;
    
    theElements = newElements;
  }

  /** Inner class to implement the Iterator<E> interface. */
  protected class Iter implements Iterator<E> {
    // Data Fields
	protected int entry;
    // Index of next element */
    // EXERCISE

    // Methods
    // Constructor	
    /**
     * Initializes the Iter object to reference the
     * first queue element.
     */
    public Iter () {
      // EXERCISE
    	if (isEmpty() == true) {
    		entry = -1;
    	}
    	
    	
    	else {
    		entry = first;
    	}
      
    }

    /**
     * Returns true if there are more elements in the queue to access.
     * @return true if there are more elements in the queue to access.
     */
    @Override
    public boolean hasNext () {
      if (entry != -1) {
    	  return true;
      }
      
      
      else {
    	  return false;
      }
    }

    /**
     * Returns the next element in the queue.
     * @pre entry references the next element to access.
     * @post entry moved forward in the queue.
     * @return The next element.
     */
    @Override
    public E next () {
      E data = theElements[entry];
      
      if (entry != last && entry+1 < size) {
    	  entry++;
      }
    		  
    		  
      else if (entry != last && entry+1 >= size) {
    	  entry = 0;
      }
      
      
      else {
      entry = -1;
      }
      
      return data;
    }

    /**
     * Remove the element accessed by the Iter object -- not implemented.
     * @throws UnsupportedOperationException when called
     */
    @Override
    public void remove () {
      throw new UnsupportedOperationException();
    }
  }
}
