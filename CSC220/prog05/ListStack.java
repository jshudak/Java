package prog05;

import java.util.EmptyStackException;
import java.util.List;
import java.util.ArrayList;

/** Implementation of the interface StackInterface<E> using a List.
*   @author vjm
*/

public class ListStack<E> implements StackInterface<E> {
  // Data Fields
  /** Storage for stack. */
  List<E> theData;

  /** Initialize theData to an empty List. */
  public ListStack() {
    theData = new ArrayList<E>();
  }

  /** Pushes an item onto the top of the stack and returns the item
      pushed.
      @param obj The object to be inserted.
      @return The object inserted.
   */
  public E push (E obj) {
    theData.add(obj);
    return obj;
  }

@Override
public E peek() {
	if (empty())
	      throw new EmptyStackException();
	int top = theData.size()-1;
	return theData.get(top);
}

@Override
public E pop() {
	if (empty())
	      throw new EmptyStackException();
	int top = theData.size()-1;
	E topData = theData.remove(top);
	return topData;
}

@Override
public boolean empty() {
	return theData.isEmpty();
}

  /**** EXERCISE ****/
}
