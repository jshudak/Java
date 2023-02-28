package prog04;

public class SortedDLLPD extends DLLBasedPD{
	
	
	/** Add a new entry at a location.
    @param previousORnext The entry right before or right after
    where the new entry should go.  null for empty list.
    @param newEntry The new entry to be added.
    @return the new entry
	*/
	 protected DLLEntry add (DLLEntry previousORnext, DLLEntry newEntry) {
		    // EXERCISE
		    // Add entry to the end of the list. (Ignore previousORnext.)
			 
		    // What do you do if the list is empty?  (Easy!)
			if (first == null) {
				first = newEntry;
				last = newEntry;
				return newEntry;
			}
		    // What do you do if the list is not empty?  (Tuesday lecture.)    

		    	DLLEntry prev, next;
		    	
		    	if(previousORnext.getName().compareTo(newEntry.getName()) < 0) {
		    		prev = previousORnext;
		    		next = prev.getNext();
		    	}
		    	
		    	else {
		    		next = previousORnext;
		    		prev = next.getPrevious();
		    	}
		    	
		    newEntry.setPrevious(prev);
		    newEntry.setNext(next);
		    
		    if (next == null) {
		    	last = newEntry;
		    }
		    else {
		    	next.setPrevious(newEntry);
		    }
		    
		    
		    if (prev == null) {
		    	first = newEntry;
		    }
		    else {
		    	prev.setNext(newEntry);
		    }
			
		    return newEntry;
		  }
	 
	 
	 /** Find an entry in the directory.
     @param name The name to be found
     @return The entry with the same name or the entry right before
     or right after where the entry should be.  null for an empty
     list.
	 */
	 protected DLLEntry find (String name) {
		    // EXERCISE
		    // For each entry in the directory.
			  for (DLLEntry entry = first;entry != null;entry = entry.getNext()) {
			  if (entry.getName().compareTo(name) >= 0) {
				 return entry; 
			  }
			  }

		    return last; // Name not found.  It should go at the end.
		  }

}
