package prog02;

import java.io.*;

/**
 * This is an implementation of PhoneDirectory that uses a sorted
 * array to store the entries.
 * @author vjm
 */
public class SortedPD extends ArrayBasedPD {
	
	/** Find an entry in the directory.
    @param name The name to be found
    @return The index of the entry with that name or, if it is not
    there, (-insert_index - 1), where insert_index is the index
    where should be added.
	 */
	protected int find (String name) {
		for (int i = 0; i < size; i++) {
			if (theDirectory[i].getName().equals(name))
				return i;
		}
		int first, last, mid;
		first = 0;
		last = (size-1);
		
			while (first <= last)
			{
				mid = first+((last-first)/2);
				
				if (name.compareTo(theDirectory[mid].getName()) < 0) {					
					last = mid - 1;
				}
				
				else if(name.compareTo(theDirectory[mid].getName()) > 0) {
					first = mid + 1;
				}
				
				else return mid;
			}

		return -first - 1;
	}
	
	/** Add an entry to the directory.
    @param index The index at which to add the entry in theDirectory.
    @param newEntry The new entry to add.
    @return The DirectoryEntry that was just added.
	 */
	
	  protected DirectoryEntry add (int index, DirectoryEntry newEntry) {
	  
	  if (size == theDirectory.length)
		  reallocate();
	  
	  for (int i = size-1; i>=index; i--) {
		  theDirectory[i+1] = theDirectory[i]; 
		  }
	  theDirectory[index] = newEntry;
	  
	  size++;
	  
	  return newEntry; 
	  
	  }
	 
	 
	
	/** Remove an entry from the directory.
    @param index The index in theDirectory of the entry to remove.
    @return The DirectoryEntry that was just removed.
	 */
	protected DirectoryEntry remove (int index) {
		DirectoryEntry entry = theDirectory[index];
		for (int i = index; i<size; i++) {
			if(i<size-1)
		theDirectory[i] = theDirectory[i+1];
		}
		size--;
		  
		  
		return entry;
	}

}
