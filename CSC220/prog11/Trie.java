package prog11;

import java.util.*;
import prog02.*;

public class Trie <V>
  extends AbstractMap<String, V> {

  private class Entry
    implements Map.Entry<String, V> {
    String key;
    V value;
    
    Entry (String key, V value) {
      this.key = key;
      this.value = value;
      this.sub = key;
    }

    Entry (String sub, String key, V value) {
      this.key = key;
      this.value = value;
      this.sub = sub;
    }

    public String getKey () { return key; }
    public V getValue () { return value; }
    public V setValue (V newValue) {
      V oldValue = value;
      value = newValue;
      return oldValue;
    }

    String sub;
    Entry[] array = new Trie.Entry[90];
  }
  
  private Entry[] array = new Trie.Entry[90];
  private int size;

  public int size () { return size; }

  /**
   * Find the entry with the given key.
   * @param key The key to be found.
   * @param iKey The current starting character index in the key.
   * @param array The array of entries to search for the key.
   * @return The entry with that key or null if not there.
   */
  private Entry find (String key, int iKey, Entry[] array) {
    int iEntry = -1;
    Entry entry = null;
    // EXERCISE:
    // Set iEntry to the character iKey in key minus 'a'.
    // Set entry to the element of array at that index.
    ///
    iEntry = key.charAt(iKey) - '!';
    entry = array[iEntry];
    ///
    if (entry == null)
      return null;

    int iSub = 0;
    // While the character iKey of key and iSub of entry.sub match,
    // increment both iKey and iSub.
    ///
    while(iKey < key.length() && iSub < entry.sub.length() && key.charAt(iKey) == entry.sub.charAt(iSub)) {
    	iKey++;
    	iSub++;
    }

    ///

    // If we have not matched all the characters of entry.sub, the key
    // is not in the Trie.
    ///
    if (entry.sub.length() != iSub) {
    	return null;
    }

    ///

    // If we have matched all the characters of key, then entry might
    // be the one we want (to return).  But only if its key is not
    // null.  If it is null, the key is not in the Trie.
    ///
    if (iKey == key.length()) {
    	if (entry.key != null)
    		return entry;
    	else
    		return null;
    }

    ///
    
    // If we have not returned yet, call find recursively with entry.array.
    ///
    return find(key, iKey, entry.array);
    
    ///
  }    

  public boolean containsKey (Object key) {
    Entry entry = find((String) key, 0, array);
    return entry != null && entry.key != null;
  }
  
  public V get (Object key) {
    Entry entry = find((String) key, 0, array);
    if (entry != null && entry.key != null)
      return entry.value;
    return null;
  }
  
  public boolean isEmpty () { return size == 0; }
  
  private V put (String key, int iKey, V value, Entry[] array) {
    int iEntry = -1;
    Entry entry = null;
    // EXERCISE:
    ///
    iEntry = key.charAt(iKey) - '!';
    entry = array[iEntry];

    ///

    if (entry == null) {
      // EXERCISE:
      // Create a entry whose sub is the unmatched part of key, key is
      // key, and value is value.  Save it at the iEntry in array.
      // Increment size.
      ///
    	Entry newEntry = new Entry(key.substring(iKey), key, value);
    	array[iEntry] = newEntry;
    	size++;
      ///
      return null;
    }
    ///

    int iSub = 0;
    // While the character iKey of key and iSub of entry.sub match,
    // increment both iKey and iSub.
    ///
    while(iKey < key.length() && iSub < entry.sub.length() && key.charAt(iKey) == entry.sub.charAt(iSub)) {
    	iKey++;
    	iSub++;
    }


    ///

    // If we have not matched all the characters of entry.sub, we need
    // to split entry.
    // Set sub0 to the first iSub characters of entry.sub and sub1 to
    // the rest.
    // Set array[iEntry] to a new entry with sub0 and with null key and value.
    // Set the sub of entry to sub1.
    // Set iEntry2 using the same formula as you used for iEntry, only
    // it will be different because you changed iKey.
    // Store entry at array[iEntry].array[iEntry2].
    // Set entry back to array[iEntry].
    ///
    if (iSub != entry.sub.length()) {
    	String sub0 = entry.sub.substring(0, iSub);
    	String sub1 = entry.sub.substring(iSub);
    	array[iEntry] = new Entry(sub0, null, null);
    	entry.sub = sub1;
    	int iEntry2 = sub1.charAt(0) - '!';
    	array[iEntry].array[iEntry2] = entry;
    	entry = array[iEntry];
    }

    ///

    // If we have matched all the characters of key, then we will use
    // the current entry.  If its key is null, set its key and value,
    // increment size and return null.  Otherwise, just return
    // entry.setValue(value).
    ///
    if (iKey == key.length()) {
    	if (entry.key != null)
    		return entry.setValue(value);
    	else {
    		entry.key = key;
    		entry.setValue(value);
    		size++;
    		return null;
    	}
    }

    ///
    
    // If we have not returned yet, we need to recurse on entry.array.
    ///
    return put(key, iKey, value, entry.array);
    ///
  }
  
  public V put (String key, V value) {
    return put(key, 0, value, array);
  }      
  
  public V remove (Object keyAsObject) {
    return null;
  }

  private Iterator<Map.Entry<String, V>> entryIterator () {
    return entryList(array).iterator();
  }

  private List<Map.Entry<String,V>> entryList (Entry[] array) {
    List<Map.Entry<String,V>> list = new ArrayList<Map.Entry<String,V>>();

    // EXERCISE
    // Add each entry in array to list and recursively add its array.
    // To add a list to a list, use addAll.
    ///
   for (Entry entry : array){
	   if (entry == null) {
		   continue;
	   }
	   else {
		   if (entry.key != null) {
		   list.add(entry);
		   }
		   if (entry.array != null) {
			   list.addAll(entryList(entry.array));
		   }
	   }
   }
    ///

    return list;
  }

  public Set<Map.Entry<String, V>> entrySet() { return new EntrySet(); }

  private class EntrySet extends AbstractSet<Map.Entry<String, V>> {
    public int size() { return size; }

    public Iterator<Map.Entry<String, V>> iterator () {
      return entryIterator();
    }

    public void remove () {}
  }

  public String toString () {
    return toString(array, 0);
  }
  
  private String toString (Entry[] array, int indent) {
    String ind = "";
    // Add index number of spaces to ind:
    ///
    for(int i = 0; i < indent; i++)
    	ind += " ";

    ///
    String s = "";
    for (Entry entry : array) {
      if (entry == null)
        continue;
      // Append indented entry (sub, key, and value) and newline ("\n") to s.
      ///
      s = s + ind + entry.sub + " " + entry.key + " "
    		  + entry.value + "\n";
      ///
      // Append the toString of entry.array to s.
      // What value of indent should you used?
      // bob null null
      //    by bobby 7
      //    cat bobcat 8
      // What additional indent would make by and cat line up just past bob?
      ///
      	s = s + toString(entry.array, entry.sub.length()+indent);
      ///
    }
    return s;
  }

  void test () {
    Entry bob = new Entry("bob", null, null);
    array['b' - '!'] = ((Entry) bob);
    Entry by = new Trie.Entry("by", "bobby", 0);
    bob.array['b' - '!'] = (by);
    Entry ca = new Trie.Entry("ca", null, null);
    bob.array['c' - '!'] = (ca);
    Entry lf = new Trie.Entry("lf", "bobcalf", 1);
    ca.array['l' - '!'] = (lf);
    Entry t = new Trie.Entry("t", "bobcat", 2);
    ca.array['t' - '!'] = (t);
    Entry catdog = new Trie.Entry("catdog", "catdog", 3);
    array['c' - '!'] = ((Entry) catdog);
    size = 4;
  }

  public static void main (String[] args) {
    Trie<Integer> trie = new Trie<Integer>();
    trie.test();
    System.out.println(trie);

    String[] keys = { "bobby", "bobcalf", "bobcat", "catdog", "bo", "bob", "bobb", "bobcal", "cat", "fred" };
    for (String key : keys)
      System.out.println("get(" + key + ") = " + trie.get(key));
    System.out.println();

    UserInterface ui = new ConsoleUI();

    String[] commands = { "toString", "containsKey", "get", "put", "size", "entrySet", "remove", "quit" };
    String key = null;
    int value = -1;

    while (true) {
      int command = ui.getCommand(commands);
      switch (command) {
      case 0:
        ui.sendMessage(trie.toString());
        break;
      case 1:
        key = ui.getInfo("key: ");
        if (key == null) {
          ui.sendMessage("null key");
          break;
        }
        ui.sendMessage("containsKey(" + key + ") = " + trie.containsKey(key));
        break;
      case 2:
        key = ui.getInfo("key: ");
        if (key == null) {
          ui.sendMessage("null key");
          break;
        }
        ui.sendMessage("get(" + key + ") = " + trie.get(key));
        break;
      case 3:
        key = ui.getInfo("key: ");
        if (key == null) {
          ui.sendMessage("null key");
          break;
        }
        try {
          value = Integer.parseInt(ui.getInfo("value: "));
        } catch (Exception e) {
          ui.sendMessage(value + "not an integer");
          break;
        }
        ui.sendMessage("put(" + key + "," + value + ") = " + trie.put(key, value));
        break;
      case 4:
        ui.sendMessage("size() = " + trie.size());
        break;
      case 5: {
        String s = "";
        for (Map.Entry<String, Integer> entry : trie.entrySet())
          s += entry.getKey() + " " + entry.getValue() + "\n";
        ui.sendMessage(s);
        break;
      }
      case 6:
        break;
      case 7:
        return;
      }
    }
  }
}


