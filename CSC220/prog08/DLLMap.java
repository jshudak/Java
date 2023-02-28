package prog08;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.AbstractSet;
import java.util.Set;
import java.util.Iterator;

public class DLLMap <K extends Comparable<K>, V>
  extends AbstractMap<K, V> {

  protected class Entry
    implements Map.Entry<K, V> {

    K key;
    Object value;
    Entry previous, next;
    
    Entry (K key, Object value) {
      this.key = key;
      this.value = value;
    }
    
    public K getKey () { return key; }
    public V getValue () { return (V) value; }
    public V setValue (V newValue) {
      V oldValue = (V) value;
      value = newValue;
      return oldValue;
    }

    protected boolean isSkipEntry () { 
      return value != null && value instanceof DLLMap.Entry;
    }

    protected Entry getEntry () { return (Entry) value; }
  }
  
  protected Entry first, last;
  
  /**
   * Starting from entry, find an Entry with key nearest to key.
   * @param startAt The entry to start from.
   * @param key The Key to be found.
   * @return Entry with key or, if not there, neighboring key in list.
   */
  protected Entry find (Entry startAt, K key) {
    if (startAt == null)
      return null;
    Entry nearest = startAt;

    // EXERCISE
    // Move nearest to the nearest key to key (same or a neighbor).
    ///
    while(key.compareTo(nearest.getKey()) > 0) {
    	if (nearest.next != null)
    		nearest = nearest.next;
    	else
    		return nearest;
    }

    while (key.compareTo(nearest.getKey()) < 0) {
    	if (nearest.previous != null)
    		nearest = nearest.previous;
    	else
    		return nearest;
    }

    ///

    return nearest;
  }    
  
  public boolean containsKey (Object keyAsObject) {
    K key = (K) keyAsObject;
    Entry entry = find(first, key);
    return entry != null && entry.key.equals(key);
  }
  
  public V get (Object keyAsObject) {
    // EXERCISE
    // Look at containsKey and fix this.
    ///
	  K key = (K) keyAsObject;
	  Entry entry = find(first, key);
	  if (entry != null && entry.key.equals(key) == true)
	    return entry.getValue();
	  else
	    return null;

  }
  
  /**
   * Add newEntry before or after neighbor depending on the order of their Keys.
   * @param neighbor The Entry to insert before or after.  null if the list is empty.
   * @param newEntry The new Entry to be inserted.
   * @return newEntry
   */
  protected Entry add (Entry neighbor, Entry newEntry) {
    Entry previous = null;
    Entry next = null;

    if (neighbor != null) {
      // EXERCISE
      // Set previous and next.  One of them is equal to neighbor.
      // Set newEntry.previous and newEntry.next (easy).
      // Set previous.next and next.previous, avoiding null pointer exceptions.
      // DO NOT use the variables first and last!!!!
      ///
    if (neighbor.getKey().compareTo(newEntry.getKey()) < 0) {
    	previous = neighbor;
    	next = neighbor.next;
    	
    	newEntry.previous = previous;
    	newEntry.next = next;
    	
    	if (next != null)
    		next.previous = newEntry;
    	previous.next = newEntry;
    	
    }
    else {
    	next = neighbor;
    	previous = neighbor.previous;
    	
    	newEntry.previous = previous;
    	newEntry.next = next;
    	
    	if (previous != null)
    		previous.next = newEntry;
    	next.previous = newEntry;
    }






      






      ///
    }

    // Setting first and last here.
    if (first == next)
      first = newEntry;
    if (last == previous)
      last = newEntry;

    return newEntry;
  }

  public V put (K key, V value) {
    Entry entry = find(first, key);
    // EXERCISE
    // Look at containsKey and then fix this.
    ///
    if ((entry != null && entry.key.equals(key) == true)) {
    	V valueToReturn = entry.getValue();
    	entry.setValue(value);
    	return valueToReturn;
    }
    else	

    ///
    add(entry, new Entry(key, value));
    return null;
  }      
  
  /**
   * Remove Entry entry from list.
   * @param entry The entry to remove.
   */
  protected void remove (Entry entry) {
    // EXERCISE
    // ONLY modify first IF entry==first.  Ditto last.
    ///
	  if (entry.previous != null)
	  entry.previous.next = entry.next;
	  
	  if (entry.next != null)
	  entry.next.previous = entry.previous;

	  if (entry == first)
		  first = entry.next;
	  
	  else if (entry == last)
		  last = entry.previous;
    ///
  }

  public V remove (Object keyAsObject) {
    // EXERCISE
    // If it is not there:
    ///
	  K key = (K) keyAsObject;
	  Entry entry = find(first, key);
	  if (entry == null || entry.key.equals(key) == false) {
		  return null;	  
	  }
    // Otherwise remove it and return the right thing.
    // EXERCISE
    ///
	  else {
		  remove(entry);
		  return entry.getValue();
	  }

    ///
  }      

  protected class Iter implements Iterator<Map.Entry<K, V>> {
    Entry next = first;
    
    public boolean hasNext () { return next != null; }
    
    public Map.Entry<K, V> next () {
      Map.Entry<K, V> ret = next;
      next = next.next;
      return ret;
    }
    
    public void remove () {
      throw new UnsupportedOperationException();
    }
  }
  
  protected class Setter extends AbstractSet<Map.Entry<K, V>> {
    public Iterator<Map.Entry<K, V>> iterator () {
      return new Iter();
    }
    
    public int size () { return DLLMap.this.size(); }
  }
  
  public Set<Map.Entry<K, V>> entrySet () { return new Setter(); }
  
  public static void main (String[] args) {
    Map<String, Integer> map = new DLLMap<String, Integer>();
    
    if (false) {
      map.put("Victor", 50);
      map.put("Irina", 45);
      map.put("Lisa", 47);
    
      for (Map.Entry<String, Integer> pair : map.entrySet())
        System.out.println(pair.getKey() + " " + pair.getValue());
    
      System.out.println(map.put("Irina", 55));

      for (Map.Entry<String, Integer> pair : map.entrySet())
        System.out.println(pair.getKey() + " " + pair.getValue());

      System.out.println(map.remove("Irina"));
      System.out.println(map.remove("Irina"));
      System.out.println(map.get("Irina"));
    
      for (Map.Entry<String, Integer> pair : map.entrySet())
        System.out.println(pair.getKey() + " " + pair.getValue());
    }
    else {
      String[] keys = { "Vic", "Ira", "Sue", "Zoe", "Bob", "Ann", "Moe" };
      for (int i = 0; i < keys.length; i++) {
        System.out.print("put(" + keys[i] + ", " + i + ") = ");
        System.out.println(map.put(keys[i], i));
        System.out.println(map);
        System.out.print("put(" + keys[i] + ", " + -i + ") = ");
        System.out.println(map.put(keys[i], -i));
        System.out.println(map);
        System.out.print("get(" + keys[i] + ") = ");
        System.out.println(map.get(keys[i]));
        System.out.print("remove(" + keys[i] + ") = ");
        System.out.println(map.remove(keys[i]));
        System.out.println(map);
        System.out.print("get(" + keys[i] + ") = ");
        System.out.println(map.get(keys[i]));
        System.out.print("remove(" + keys[i] + ") = ");
        System.out.println(map.remove(keys[i]));
        System.out.println(map);
        System.out.print("put(" + keys[i] + ", " + i + ") = ");
        System.out.println(map.put(keys[i], i));
        System.out.println(map);
      }
    }
  }
}
