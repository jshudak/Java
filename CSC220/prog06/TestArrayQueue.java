package prog06;

import java.util.Iterator;

public class TestArrayQueue extends ArrayQueue {
  class State {
    String[] data;
    int f, l, s;
    State (String[] data, int f, int l, int s) {
      this.data = data;
      this.f = f;
      this.l = l;
      this.s = s;
    }

    void set () {
      theElements = new Object[data.length];
      for (int i = 0; i < data.length; i++)
        theElements[i] = data[i];
      first = f;
      last = l;
      size = s;
    }

    boolean equals (Object a, Object b) {
      if ((a == null) != (b == null))
        return false;
      if (a == null)
        return true;
      return a.equals(b);
    }

    boolean same () {
      if (theElements.length != data.length)
        return false;
      for (int i = 0; i < data.length; i++)
        if (!equals(theElements[i], data[i]))
          return false;
      return first == f && last == l && size == s;
    }
  }

  class Test {
    State before;
    String call;
    String arg;
    Object ret;
    State after;
    Test (State before, String call, String arg, Object ret, State after) {
      this.before = before;
      this.call = call;
      this.arg = arg;
      this.ret = ret;
      this.after = after;
    }

    void printIterator () {
      System.out.print("Iterator:");
      for (Iterator iter = iterator(); iter.hasNext();)
        System.out.print(" " + iter.next());
      System.out.println();
    }
        
    boolean checkIterator () {
      int count = 0;
      for (Iterator iter = iterator(); iter.hasNext(); iter.next())
        count++;
      if (count != size)
        return false;

      int j = first;
      Iterator it = iterator();
      for (int i = 0; i < size; i++) {
        if (!it.next().equals(theElements[j]))
          return false;
        j = (j + 1) % theElements.length;
      }

      return true;
    }

    void print () {
      System.out.print("theElements:");
      int n = theElements.length;
      for (int i = 0; i < theElements.length; i++)
        System.out.print(" " + theElements[i]);
      System.out.println();
      System.out.println("first=" + first + " last=" + last + " size=" + size);
      printIterator();
    }

    boolean test () {
      before.set();
      print();
      if (!checkIterator())
        return false;
      System.out.println(call + "(" + arg + ")");
      Object r = null;
      try {
        if (call.equals("offer"))
          r = offer(arg);
        else if (call.equals("peek"))
          r = peek();
        else
          r = poll();
      } catch (Exception e) {
        System.out.println("ERROR: " + e);
        return false;
      }
      System.out.println("returns " + r);
      if (ret == null && r != null || ret != null && !ret.equals(r))
        return false;
      print();
      if (!checkIterator())
        return false;
      if (!after.same())
        return false;
      System.out.println();
      return true;
    }
  }

  boolean test () {
    Test test;

    test = new Test(new State(new String[]{"null", null, null}, 0, 2, 0),
                    "poll", "", null,
                    new State(new String[]{"null", null, null}, 0, 2, 0));
    if (!test.test())
      return false;

    test = new Test(new State(new String[]{"bob", null, null}, 0, 2, 0),
                    "poll", "", null,
                    new State(new String[]{"bob", null, null}, 0, 2, 0));
    if (!test.test())
      return false;

    test = new Test(new State(new String[]{"bob", null, null}, 0, 0, 1),
                    "poll", "", "bob",
                    new State(new String[]{"bob", null, null}, 1, 0, 0));
    if (!test.test())
      return false;

    test = new Test(new State(new String[]{"bob", "ann", "eve"}, 2, 1, 3),
                    "poll", "", "eve",
                    new State(new String[]{"bob", "ann", "eve"}, 0, 1, 2));
    if (!test.test())
      return false;

    test = new Test(new State(new String[]{"bob", "ann", "eve"}, 0, 1, 2),
                    "offer", "vic", true,
                    new State(new String[]{"bob", "ann", "vic"}, 0, 2, 3));
    if (!test.test())
      return false;

    test = new Test(new State(new String[]{"bob", "ann", "eve"}, 2, 0, 2),
                    "offer", "vic", true,
                    new State(new String[]{"bob", "vic", "eve"}, 2, 1, 3));
    if (!test.test())
      return false;

    test = new Test(new State(new String[]{"bob", "ann", "eve"}, 0, 2, 3),
                    "offer", "vic", true,
                    new State(new String[]{"bob", "ann", "eve", "vic", null, null}, 0, 3, 4));
    if (!test.test())
      return false;

    test = new Test(new State(new String[]{"bob", "ann", "eve"}, 1, 0, 3),
                    "offer", "vic", true,
                    new State(new String[]{"ann", "eve", "bob", "vic", null, null}, 0, 3, 4));
    if (!test.test())
      return false;

    test = new Test(new State(new String[]{"bob", "ann", "eve"}, 2, 1, 3),
                    "offer", "vic", true,
                    new State(new String[]{"eve", "bob", "ann", "vic", null, null}, 0, 3, 4));
    if (!test.test())
      return false;

    return true;
  }

  public static void main (String[] args) {
    TestArrayQueue test = new TestArrayQueue();

    if (test.test())
      System.out.println("SCORE:  25");
    else
      System.out.println("SCORE:  0");
  }
}
    
