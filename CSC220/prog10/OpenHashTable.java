package prog10;

import java.util.*;
import java.io.PrintWriter;
import java.io.FileWriter;

public class OpenHashTable<K, V> extends AbstractMap<K, V> {
  private class Entry implements Map.Entry<K, V> {
    K key;
    V value;

    public K getKey () { return key; }
    public V getValue () { return value; }
    public V setValue (V value) { return this.value = value; }

    Entry (K key, V value) {
      this.key = key;
      this.value = value;
    }
  }

  private final static int DEFAULT_CAPACITY = 5;
  private Entry[] table = new OpenHashTable.Entry[DEFAULT_CAPACITY];
  private Entry DELETED = new Entry(null, null);
  private int size;
  private int numNull = DEFAULT_CAPACITY;

  private int hashIndex (Object key) {
    int index = key.hashCode() % table.length;
    if (index < 0) {
      index += table.length;
    }
    return index;
  }

  // Linear probe sequence: start at hashIndex(key) and increment,
  // but go back to zero at the end of the table.

  // If the key is there, return the index of its Entry.

  // If it is not there, return the index where the Entry with the
  // key should be inserted.  If there is a deleted Entry in the
  // probe sequence, return the index of the *first* deleted Entry
  // in the sequence.

  // Otherwise return the index of the first null in the probe
  // sequence.
  private int find (Object key) {
    // IMPLEMENT
	  int index = hashIndex(key);
	  int firstCone = -1;
	  
	  while (table[index] != null) {
		  
		  if (table[index] != DELETED && table[index].key.equals(key))
			  return index;
		  
		  else  if (firstCone == -1 && table[index] == DELETED)
			  firstCone = index;
		  
		  index = (index + 1)%table.length;
		  
		  if(index % table.length == 0)
			  index = 0;
		  
	  }
	  
		  if (firstCone > -1)
			  return firstCone;
	  
		  else
		  return index;
	  
  }

  public boolean containsKey (Object key) {
    Entry entry = table[find(key)];
    return entry != null && entry != DELETED;
  }

  public V get (Object key) {
    Entry entry = table[find(key)];
    if (entry == null || entry == DELETED)
      return null;
    return entry.value;
  }

  public V put (K key, V value) {
    int index = find(key);
    Entry entry = table[index];
    if (entry != null && entry != DELETED)
        return entry.setValue(value);
    
    if(table[index] == null)
    	numNull--;
    
      table[index] = new Entry(key, value);
      size++;
      
    if (numNull < table.length / 2)
      rehash(4 * size);
    return null;
  }

  public V remove (Object key) {
    int index = find(key);
    Entry entry = table[index];
    if (entry == null || entry == DELETED)
      return null;
    table[index] = DELETED;
    size--;
    return entry.value;
  }

  private void rehash (int newCapacity) {
    Entry[] oldTable = table;
    table = new OpenHashTable.Entry[newCapacity];
    size = 0;
    numNull = newCapacity;
    for (Entry data : oldTable) {
    	if (data != null && data != DELETED)
    		put(data.key, data.value);
    }


  }

  private Iterator<Map.Entry<K, V>> entryIterator () {
    return new EntryIterator();
  }

  private class EntryIterator implements Iterator<Map.Entry<K, V>> {
    // EXERCISE

    private EntryIterator () {
      // EXERCISE
    }

    public boolean hasNext () {
      // EXERCISE
      return false;
    }

    public Map.Entry<K, V> next () {
      if (!hasNext())
        throw new NoSuchElementException();
      // EXERCISE
      return null;
    }

    public void remove () {}
  }

  public Set<Map.Entry<K,V>> entrySet() { return new EntrySet(); }

  private class EntrySet extends AbstractSet<Map.Entry<K, V>> {
    public int size() { return size; }

    public Iterator<Map.Entry<K, V>> iterator () {
      return entryIterator();
    }

    public void remove () {}
  }

  public String toString () {
    String ret = "--------------------------------\n";
    for (int i = 0; i < table.length; i++) {
      ret = ret + i + ": ";
      Entry entry = table[i];
      if (entry == null)
        ret = ret + "null\n";
      else if (entry == DELETED)
        ret = ret + "DELETED\n";
      else
        ret = ret + entry.key + " " + entry.value + "\n";
    }
    return ret;
  }

  static void print (PrintWriter out, Object s) {
    out.println(s);
    System.out.println(s);
  }

  public static void main (String[] args) {
    PrintWriter out = null;
    try {
      out = new PrintWriter(new FileWriter("open.txt"));
    } catch (Exception e) {
      System.out.println(e);
      return;
    }

    OpenHashTable<String, Integer> table =
      new OpenHashTable<String, Integer>();

    print(out, "put Brad 46 hash index " + table.hashIndex("Brad") + " returns " + table.put("Brad", 46));
    print(out, table);
    print(out, "put Hal 10 hash index " + table.hashIndex("Hal") + " returns " + table.put("Hal", 10));
    print(out, table);
    print(out, "put Brad 60 hash index " + table.hashIndex("Brad") + " returns " + table.put("Brad", 60));
    print(out, table);
    print(out, "put Hal 24 hash index " + table.hashIndex("Hal") + " returns " + table.put("Hal", 24));
    print(out, table);
    print(out, "put Kyle 6 hash index " + table.hashIndex("Kyle") + " returns " + table.put("Kyle", 6));
    print(out, table);
    print(out, "put Lisa 43 hash index " + table.hashIndex("Lisa") + " returns " + table.put("Lisa", 43));
    print(out, table);
    print(out, "put Lynne 43 hash index " + table.hashIndex("Lynne") + " returns " + table.put("Lynne", 43));
    print(out, table);
    print(out, "put Victor 46 hash index " + table.hashIndex("Victor") + " returns " + table.put("Victor", 46));
    print(out, table);
    print(out, "put Zoe 6 hash index " + table.hashIndex("Zoe") + " returns " + table.put("Zoe", 6));
    print(out, table);
    print(out, "put Zoran 76 hash index " + table.hashIndex("Zoran") + " returns " + table.put("Zoran", 76));
    print(out, table);

    for (String key : table.keySet())
      System.out.print(key + " ");
    System.out.println();

    print(out, "remove Zoe returns " + table.remove("Zoe"));
    print(out, table);
    print(out, "remove Kyle returns " + table.remove("Kyle"));
    print(out, table);
    print(out, "remove Brad returns " + table.remove("Brad"));
    print(out, table);
    print(out, "remove Zoran returns " + table.remove("Zoran"));
    print(out, table);
    print(out, "remove Lisa returns " + table.remove("Lisa"));
    print(out, table);
    print(out, "remove Hal returns " + table.remove("Hal"));
    print(out, table);
    print(out, "remove Lynne returns " + table.remove("Lynne"));
    print(out, table);

    print(out, "put Ant 3 hash index " + table.hashIndex("Ant") + " returns " + table.put("Ant", 3));
    print(out, table);
    print(out, "remove Ant returns " + table.remove("Ant"));
    print(out, table);
    print(out, "put Bug 1 hash index " + table.hashIndex("Bug") + " returns " + table.put("Bug", 1));
    print(out, table);
    print(out, "remove Bug returns " + table.remove("Bug"));
    print(out, table);
    print(out, "put Cat 4 hash index " + table.hashIndex("Cat") + " returns " + table.put("Cat", 4));
    print(out, table);
    print(out, "remove Cat returns " + table.remove("Cat"));
    print(out, table);
    print(out, "put Dog 1 hash index " + table.hashIndex("Dog") + " returns " + table.put("Dog", 1));
    print(out, table);
    print(out, "remove Dog returns " + table.remove("Dog"));
    print(out, table);
    print(out, "put Eel 5 hash index " + table.hashIndex("Eel") + " returns " + table.put("Eel", 5));
    print(out, table);
    print(out, "remove Eel returns " + table.remove("Eel"));
    print(out, table);
    print(out, "put Fox 9 hash index " + table.hashIndex("Fox") + " returns " + table.put("Fox", 9));
    print(out, table);
    print(out, "remove Fox returns " + table.remove("Fox"));
    print(out, table);
    print(out, "put Gnu 2 hash index " + table.hashIndex("Gnu") + " returns " + table.put("Gnu", 2));
    print(out, table);
    print(out, "remove Gnu returns " + table.remove("Gnu"));
    print(out, table);
    print(out, "put Hen 2 hash index " + table.hashIndex("Hen") + " returns " + table.put("Hen", 2));
    print(out, table);

    out.close();
  }
}
