package prog08;
import java.util.Random;

import prog08.DLLMap.Entry;

public class SkipMap<K extends Comparable<K>, V> extends DLLMap<K, V> {
  protected Entry top;

  /** 
   * Find (recursively) the Entry at the bottom level with the key.
   * @param startAt The Entry to start from, usually at a higher level, but null if Map is empty.
   * @param key The key to be found.
   * @return Bottom level Entry with the key or null if not there.
   */
  protected Entry rFind (Entry startAt, K key) {
    if (startAt == null)
      return null;
    // EXERCISE 7
    ///
    // Use find (not rFind) to get the nearest Entry to the key in the same list as startAt.
    Entry entry = find(startAt, key);
    // If the Entry is not at the bottom (isSkipEntry), the use rFind on the Entry just below it (getEntry()).
    if (entry.isSkipEntry() == true ) {
    	entry = rFind(entry.getEntry(), key);
    }

    if (entry == null)
    	return null;
    
    // If we are the bottom and the Entry has the right key, return it.
    else if(entry.isSkipEntry() == false && entry.key.equals(key) == true) {
    	return entry;
    }
    ///
    return null;
  }

  public boolean containsKey (Object keyAsObject) {
    K key = (K) keyAsObject;
    Entry entry = rFind(top, key);
    return entry != null;
  }
  
  public V get (Object keyAsObject) {
    // EXERCISE 7
    ///
	  K key = (K) keyAsObject;
	  Entry entry = find(first, key);
	  if (entry != null && entry.key.equals(key) == true)
	    return entry.getValue();
	  else
	    return null;

  }

  /** 
   * Add (recursively) a new Entry at the bottom level plus additional levels to be determined by coin flips.
   * @param startAt The entry to start from, usually at a higher level, but null if Map is empty.
   * @param key The key to be added.
   * @param value The value to be added.
   * @return Entry that was added at the same level as startAt or null if one was not added at that level.
   */
  protected Entry rAdd (Entry startAt, K key, V value) {
    // EXERCISE 9
    ///
    // Use find (not rFind) to get the nearest Entry to the key in the
    // same list as startAt.
	Entry nearest = find(startAt, key);
	
    // If the Entry is on the bottom, just use add (not rAdd) and
    // return the new Entry.
	if(nearest.isSkipEntry()==false) {
		Entry addBottom = new Entry(key, value);
		  add(nearest, addBottom);
		  return addBottom;
	}
    // Otherwise, use rAdd on the Entry below the Entry.
	Entry newAdded = rAdd(nearest.getEntry(), key, value);
    // If rAdd returns an Entry and if you flip heads (heads() is true),
    // use add to add a new Entry on this level with that Entry
    // as its value.  Return the new Entry (on this level).
	if (newAdded != null && heads() == true) {
		Entry addLevel = new Entry (key, newAdded);
		add(nearest, addLevel);
		return addLevel;
	  	
	}
    ///
    return null;
  }

  public V put (K key, V value) {
    if (false) // Change to false in Step 9.
      return super.put(key, value);
    if (top == null) {
      top = add(null, new Entry(key, value));
      return null;
    }
    // EXERCISE 9
    ///
    // Use rFind to check if the key is there already, and if it
    // is, use setValue.
    Entry entry = rFind(top, key);
    if (entry != null && entry.key.equals(key) == true) {
    	V valueToReturn = entry.getValue();
		entry.setValue(value);
		return valueToReturn;
    }

    // Otherwise, use rAdd to add it.
    	Entry retrieved = rAdd(top, key, value);
    ///

    // EXERCISE 10
    ///
    // If rAdd returned an Entry, set top to it.
    // While you flip heads, set top to an Entry on top of top.
    if (retrieved != null) {
    	top = retrieved;

    while (heads()) {
    	top = new Entry(key, top);
    }
    }

    ///
    return null;
  }      
  
  Random random = new Random(1);
  boolean heads () {
    return random.nextInt() % 2 == 0;
  }

  public String toString () {
    if (top == null)
      return "empty\n";
    String s = "";
    Entry head = top;
    while (true) {
      Entry entry = head;
      while (entry.previous != null)
        entry = entry.previous;
      Entry bot = first;
      for (; entry != null; entry = entry.next) {
        while (bot.key != entry.key) {
          int n = bot.key.toString().length();
          for (int i = 0; i <= n; i++)
            s = s + " ";
          bot = bot.next;
        }
        bot = bot.next;
        s = s + entry.key + " ";
      }
      s = s + "\n";
      if (head.isSkipEntry())
        head = head.getEntry();
      else
        break;
    }
    for (Entry entry = first; entry != null; entry = entry.next) {
      String k = "" + entry.key;
      String v = "" + entry.value;
      int d = k.length() - v.length();
      for (int i = 0; i < d; i++)
        s += " ";
      s += v + " ";
    }
    s += "\n";
    return s;
  }

  void skipify () {
    if (top != null || first == null)
      return;
    Entry newFirst = first;
    while (newFirst != null) {
      top = newFirst;
      newFirst = null;
      if (top.next != null)
        top = top.next;
      else
        break;
      Entry newTop = null;
      while (true) {
        if (newTop == null)
          newFirst = newTop = new Entry(top.key, top);
        else {
          newTop.next = new Entry(top.key, top);
          newTop.next.previous = newTop;
          newTop = newTop.next;
        }
        if (top.next != null && top.next.next != null)
          top = top.next.next;
        else
          break;
      }
    }
  }

  public static void main (String[] args) {
    String[] keys = { "Zoe", "Bob", "Eve", "Hal", "Abe", "Boo", "Eve", "Sam", "Kit", "Kat", "Pam", "Joe", "Ari", "Doc", "Hen", "Guy" };
    SkipMap map = new SkipMap();
    for (int i = 0; i < keys.length; i++)
      map.put(keys[i], i);
    map.skipify();
    System.out.println(map);

    String[] keys2 = { "Aaa", "Abe", "Guy", "Hal", "Joe", "Mxy", "Zoe", "Zzz" };
    for (String key : keys2) {
      System.out.print("containsKey(" + key + ") = ");
      System.out.println(map.containsKey(key));
      System.out.print("get(" + key + ") = ");
      System.out.println(map.get(key));
    }

    String[] keys3 = { "Vic", "Ira", "Sue", "Ann", "Moe", "Cat", "Dog", "Pig", "Cow", "Dot" };
    for (int i = 0; i < keys3.length; i++)
      map.put(keys3[i], keys.length + i);
    System.out.println(map);
  }
}
