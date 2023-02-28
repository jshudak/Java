package prog04;

public class TestDLLBasedPD extends DLLBasedPD {
  public static void main (String[] args) {
    TestDLLBasedPD test = new TestDLLBasedPD();
    test.test();
  }

  void test () {
    int labscore = 20;
    if (!testAdd()) {
      System.out.println("add failed: -10");
      labscore -= 10;
    }
    if (!testFind()) {
      System.out.println("find failed: -10");
      labscore -= 10;
    }

    int hwscore = 20;
    if (!testRemove()) {
      System.out.println("removeEntry failed: -20");
      hwscore -= 20;
    }

    System.out.println("LAB SCORE: " + labscore);
    System.out.println("HW SCORE: " + hwscore);
  }

  String[][][] addIn = { { },
                         { { "Ian", "412" } },
                         { { "Ian", "412" } },
                         { { "Bob", "555" }, { "Ian", "412" } },
                         { { "Bob", "555" }, { "Ian", "412" } },
                         { { "Bob", "555" }, { "Ian", "412" } },
                         { { "Bob", "555" }, { "Ian", "412" } } };
  String[] addLoc = { null, "Ian", "Ian", "Bob", "Bob", "Ian", "Ian" };
  String[][] addEntry = { { "Bob", "555" },
                          { "Bob", "555" },
                          { "Vic", "777" },
                          { "Ann", "123" },
                          { "Eve", "321" },
                          { "Eve", "321" },
                          { "Zoe", "606" } };
  String[][][] addOut = { { { "Bob", "555" } },
                          { { "Ian", "412" }, { "Bob", "555" } },
                          { { "Ian", "412" }, { "Vic", "777" } },
                          { { "Bob", "555" }, { "Ian", "412" }, { "Ann", "123" } },
                          { { "Bob", "555" }, { "Ian", "412" }, { "Eve", "321" } },
                          { { "Bob", "555" }, { "Ian", "412" }, { "Eve", "321" } },
                          { { "Bob", "555" }, { "Ian", "412" }, { "Zoe", "606" } } };

  boolean testAdd () {
    for (int i = 0; i < addIn.length; i++) {
      try {
        if (!testAdd(i))
          return false;
      } catch (Exception e) {
        System.out.println(e);
        return false;
      }
      System.out.println();
    }
    return true;
  }

  boolean testAdd (int i) {
    setList(addIn[i]);
    printList();
    DLLEntry location = getLoc(addLoc[i]);
    DLLEntry newEntry = new DLLEntry(addEntry[i][0], addEntry[i][1]);
    System.out.println("Calling add(" + str(location) + ", " + str(newEntry) + ")");
    add(location, newEntry);
    printList();
    return checkList(addOut[i]);
  }

  void setList (String[][] list) {
    first = last = null;
    for (String[] nn : list) {
      DLLEntry entry = new DLLEntry(nn[0], nn[1]);
      if (first == null) {
        first = last = entry;
      }
      else {
        last.setNext(entry);
        entry.setPrevious(last);
        last = entry;
      }
    }
  }

  DLLEntry getLoc (String name) {
    if (name == null)
      return null;
    for (DLLEntry entry = first; entry != null; entry = entry.getNext())
      if (entry.getName().equals(name))
        return entry;
    return null;
  }

  void printList () {
    System.out.print("list is ");
    if (first == null)
      System.out.print("empty ");
    else
      for (DLLEntry entry = first; entry != null; entry = entry.getNext())
        System.out.print(str(entry) + ", ");
    System.out.println("and last is " + str(last));
  }

  String str (DLLEntry entry) {
    if (entry == null)
      return "null";
    else
      return entry.getName() + ":" + entry.getNumber();
  }

  boolean checkList (String[][] list) {
    if (list.length == 0)
      return first == null && last == null;
    int i = 0;
    for (DLLEntry entry = first; entry != null; entry = entry.getNext())
      if (!entry.getName().equals(list[i++][0]))
        return false;
    if (last == null || !last.getName().equals(list[list.length-1][0]))
      return false;
    return true;
  }
    
  String[][][] findIn = { { },
                          { { "Bob", "555" } },
                          { { "Bob", "555" } },
                          { { "Bob", "555" } },
                          { { "Ian", "412" }, { "Bob", "555" } },
                          { { "Ian", "412" }, { "Bob", "555" } },
                          { { "Ian", "412" }, { "Bob", "555" } },
                          { { "Ian", "412" }, { "Bob", "555" } },
                          { { "Ian", "412" }, { "Bob", "555" } }, 
                          { { "Ian", "412" }, { "Ann", "123" }, { "Bob", "555" } }, 
                          { { "Ian", "412" }, { "Ann", "123" }, { "Bob", "555" } }, 
                          { { "Ian", "412" }, { "Ann", "123" }, { "Bob", "555" } }, 
                          { { "Ian", "412" }, { "Ann", "123" }, { "Bob", "555" } }, 
                          { { "Ian", "412" }, { "Ann", "123" }, { "Bob", "555" } }, 
                          { { "Ian", "412" }, { "Ann", "123" }, { "Bob", "555" } }, 
                          { { "Ian", "412" }, { "Ann", "123" }, { "Bob", "555" } } };
  String[] findName = { "Bob", "Ann", "Bob", "Eve", "Ann", "Ian", "Moe", "Bob", "Zoe", "Abe", "Ian", "Joe", "Ann", "Bab", "Bob", "Boo" };
  String[] findOutName = { null, "Bob", "Bob", "Bob", "Bob", "Ian",  "Bob", "Bob", "Bob", "Bob", "Ian", "Bob", "Ann", "Bob", "Bob", "Bob" };

  boolean testFind () {
    for (int i = 0; i < findIn.length; i++) {
      try {
        if (!testFind(i))
          return false;
      } catch (Exception e) {
        System.out.println(e);
        return false;
      }
      System.out.println();
    }
    return true;
  }

  boolean testFind (int i) {
    setList(findIn[i]);
    printList();
    String name = findName[i];
    System.out.println("find(" + name + ") returns ");
    DLLEntry entry = find(name);
    System.out.println(str(entry));
    if (findOutName[i] == null)
      return entry == null;
    else
      return entry.getName().equals(findOutName[i]);
  }

  String[][][] removeIn = { { },
                            { { "Bob", "555" } },
                            { { "Bob", "555" } },
                            { { "Bob", "555" }, { "Ian", "412" } },
                            { { "Bob", "555" }, { "Ian", "412" } },
                            { { "Bob", "555" }, { "Ian", "412" } },
                            { { "Ian", "412" }, { "Ann", "123" }, { "Bob", "555" } }, 
                            { { "Ian", "412" }, { "Ann", "123" }, { "Bob", "555" } }, 
                            { { "Ian", "412" }, { "Ann", "123" }, { "Bob", "555" } }, 
                            { { "Ian", "412" }, { "Ann", "123" }, { "Bob", "555" } } };
  String[] removeName = { "Bob", "Bob", "Ann", "Bob", "Ian", "Ann", "Ian", "Ann", "Bob", "Joe" };
  String[][][] removeOut = { { },
                             { },
                             { { "Bob", "555" } },
                             { { "Ian", "412" } },
                             { { "Bob", "555" } },
                             { { "Bob", "555" }, { "Ian", "412" } },
                             { { "Ann", "123" }, { "Bob", "555" } }, 
                             { { "Ian", "412" }, { "Bob", "555" } }, 
                             { { "Ian", "412" }, { "Ann", "123" } }, 
                             { { "Ian", "412" }, { "Ann", "123" }, { "Bob", "555" } } };
  String[] removeVal = { null, "555", null, "555", "412", null, "412", "123", "555", null };
  
  boolean testRemove () {
    for (int i = 0; i < removeIn.length; i++) {
      try {
        if (!testRemove(i))
          return false;
      } catch (Exception e) {
        System.out.println(e);
        return false;
      }
      System.out.println();
    }
    return true;
  }

  boolean testRemove (int i) {
    setList(removeIn[i]);
    printList();
    String name = removeName[i];
    System.out.println("Calling removeEntry(" + name + ")");
    String val = removeEntry(name);
    System.out.println("removeEntry returns " + val);
    printList();
    if (!checkList(removeOut[i]))
      return false;
    if (removeVal[i] == null)
      return val == null;
    return val.equals(removeVal[i]);
  }

}
