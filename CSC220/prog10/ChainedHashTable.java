package prog10;

import java.util.Map;
import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Set;
import java.util.AbstractSet;
import java.util.NoSuchElementException;
import java.io.PrintWriter;
import java.io.FileWriter;

public class ChainedHashTable<K, V> extends AbstractMap<K, V> {
  private class Entry implements Map.Entry<K, V> {
    K key;
    V value;
    Entry next;

    public K getKey () { return key; }
    public V getValue () { return value; }
    public V setValue (V value) { return this.value = value; }

    Entry (K key, V value, Entry next) {
      this.key = key;
      this.value = value;
      this.next = next;
    }
  }

  private final static int DEFAULT_CAPACITY = 5;
  private Entry[] table = new ChainedHashTable.Entry[DEFAULT_CAPACITY];
  private int size;

  private int hashIndex (Object key) {
    int index = key.hashCode() % table.length;
    if (index < 0)
      index += table.length;
    return index;
  }

  private Entry find (Object key) {
    int index = hashIndex(key);
    for (Entry node = table[index]; node != null; node = node.next)
      if (key.equals(node.key))
        return node;
    return null;
  }

  public boolean containsKey (Object key) {
    return find(key) != null;
  }

  public V get (Object key) {
    Entry node = find(key);
    if (node == null)
      return null;
    return node.value;
  }

  public V put (K key, V value) {
    Entry node = find(key);
    if (node != null) {
      V old = node.value;
      node.value = value;
      return old;
    }
    if (size == table.length)
      rehash(2 * table.length);
    int index = hashIndex(key);
    table[index] = new Entry(key, value, table[index]);
    size++;
    return null;
  }
	
  public V remove (Object key) {
    // IMPLEMENT
    // Get the index for the key.
	  int index = hashIndex(key);
 
    // What do you do if the linked list at that index is empty?
	  if (table[index] == null)
		  return null;


    // What do you do if the first element of the list has the key?
	  else if (table[index].getKey() == key) {
		  V keyValue = table[index].getValue();
		  table[index] = table[index].next;
		  return keyValue;
	  }

    // If the key is farther down the list, make sure you keep track
    // of the pointer to the previous entry, because you will need to
    // change its next variable.
	  else {
	  for (Entry previous = table[index]; previous.next != null; previous = previous.next) {
		  if(previous.next.getKey() == key) {
		  	V keyValue = previous.next.getValue();
		  	previous.next = previous.next.next;
		  	return keyValue;
		  }
	  }
	  }
    // Return null otherwise.
    return null;
  }

  private void rehash (int newCapacity) {
	  Entry[] oldTable = table;
	  table = new ChainedHashTable.Entry[newCapacity];
    // IMPLEMENT
	  size = 0;
	  Entry n;
	  for(int i=0; i < oldTable.length; i++) {
		  n = oldTable[i];
		  while(n != null) {
			  put(n.key, n.value);
			  n = n.next;
		  }
	  }




  }

  private Iterator<Map.Entry<K, V>> entryIterator () {
    return new EntryIterator();
  }

  private class EntryIterator implements Iterator<Map.Entry<K, V>> {
    // EXERCISE




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

  public Set<Map.Entry<K, V>> entrySet() { return new EntrySet(); }

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
      ret = ret + i + ":";
      for (Entry node = table[i]; node != null; node = node.next)
        ret = ret + " " + node.key + " " + node.value;
      ret = ret + "\n";
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
      out = new PrintWriter(new FileWriter("chained.txt"));
    } catch (Exception e) {
      System.out.println(e);
      return;
    }

    ChainedHashTable<String, Integer> table =
      new ChainedHashTable<String, Integer>();

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

    print(out, "remove Fred returns " + table.remove("Fred"));
    print(out, table);
    print(out, "remove Hal returns " + table.remove("Hal"));
    print(out, table);
    print(out, "remove Brad returns " + table.remove("Brad"));
    print(out, table);
    print(out, "remove Lynne returns " + table.remove("Lynne"));
    print(out, table);
    print(out, "remove Lisa returns " + table.remove("Lisa"));
    print(out, table);

    out.close();
  }
}
			     
