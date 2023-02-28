package prog04;

import prog02.PhoneDirectory;
import java.io.*;
import java.util.Scanner;

/** This is an implementation of the prog02.PhoneDirectory interface
 *   that uses a doubly linked list to store the data.
 *   @author vjm
 */
public class DLLBasedPD implements PhoneDirectory {
  /** The first entry and last entry of the linked list that stores
   * the directory data */
  protected DLLEntry first, last;
  
  /** The data file that contains the directory data */
  protected String sourceName = null;
    
  /** Method to load the data file.
      pre:  The directory storage has been created and it is empty.
      If the file exists, it consists of name-number pairs
      on adjacent lines.
      post: The data from the file is loaded into the directory.
      @param sourceName The name of the data file
  */
  public void loadData (String sourceName) {
    // Remember the source name.
    this.sourceName = sourceName;
    try {
      // Create a Scanner for the file.
      Scanner in = new Scanner(new File(sourceName));

      // Read each name and number and add the entry to the array.
      while (in.hasNextLine()) {
        String name = in.nextLine();
        String number = in.nextLine();
        // Add an entry for this name and number.
        addOrChangeEntry(name, number);
      }
      // Close the file.
      in.close();
    } catch (FileNotFoundException ex) {
      // Do nothing: no data to load.
      return;
    } catch (Exception ex) {
      System.err.println("Load of directory failed.");
      ex.printStackTrace();
      System.exit(1);
    }
  }
    
  /** Method to save the directory.
      pre:  The directory has been loaded with data.
      post: Contents of directory written back to the file in the
      form of name-number pairs on adjacent lines.
  */
  public void save () {
    try {
      // Create PrintStream for the file.
      PrintStream out = new PrintStream(new File(sourceName));
      
      // EXERCISE
      // Write each directory entry to the file.
      for (DLLEntry entry = first;entry != null;entry = entry.getNext()) {
        // Write the name.
        out.println(entry.getName());
        // EXERCISE
        // Write the number.
        out.println(entry.getNumber());

      }
      
      // Close the file.
      out.close();
    } catch (Exception ex) {
      System.err.println("Save of directory failed");
      ex.printStackTrace();
      System.exit(1);
    }
  }
    
  /** Add an entry or change an existing entry.
      @param name The name of the person being added or changed
      @param number The new number to be assigned
      @return The old number or, if a new entry, null
  */
  public String addOrChangeEntry (String name, String number) {
    DLLEntry entry = find(name);
    if (entry != null && entry.getName().equals(name)) {
      String oldNumber = entry.getNumber();
      entry.setNumber(number);
      return oldNumber;
      
    }
    else {
      add(entry, new DLLEntry(name, number));
      return null;
    }
  }
    
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
	}
    // What do you do if the list is not empty?  (Tuesday lecture.)    
    else if (first != null) {
		  last.setNext(newEntry);
		  newEntry.setPrevious(last);
		  last = newEntry;
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
	  if (entry.getName().equals(name)) {
		 return entry; 
	  }
	  }

    return last; // Name not found.  It should go at the end.
  }
  
  /** Look up an entry.
      @param name The name of the person
      @return The number. If not in the directory, null is returned
  */
  public String lookupEntry (String name) {
    DLLEntry entry = find(name);
    if (entry != null && entry.getName().equals(name))
      return entry.getNumber();
    return null;
  }

  /** Remove an entry from the directory.
      @param name The name of the person to be removed
      @return The current number. If not in directory, null is
      returned
  */
  public String removeEntry (String name) {
    // Call find to find the entry to remove.
    DLLEntry entry = find(name);

    // EXERCISE
    // If it is not there, return null.
    if (entry == null || entry.getName().equals(name) == false) 
    	return null;

    // If there is only one entry (how can you tell?) what do you do?
    else if (entry.getNext() == null && entry.getPrevious() == null) {
    	first = null;
    	last = null;
    }

    // If entry is the first entry?
    else if (entry.getPrevious() == null) {
    	first = entry.getNext();
    	entry.getNext().setPrevious(null);
    	entry.setNext(null);
    }

    // If entry is the last entry?
    else if (entry.getNext() == null) {
    	last = entry.getPrevious();
    	entry.getPrevious().setNext(null);
    	entry.setPrevious(null);
    }

    // Get the previous and next entry.
    // Two more lines!
    else {
    	entry.getPrevious().setNext(entry.getNext());
    	entry.getNext().setPrevious(entry.getPrevious());
    }
    	
    return entry.getNumber();
  }

}
