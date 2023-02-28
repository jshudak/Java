package prog04;

public class TestSortedDLLPD extends SortedDLLPD {
  public static void main (String[] args) {
    TestSortedDLLPD test = new TestSortedDLLPD();
    test.test();
  }

  void test () {
    int score = 40;
     if (!testAdd()) {
      System.out.println("add failed: -20");
      score -= 10;
    }
    if (!testFind()) {
      System.out.println("find failed: -20");
      score -= 10;
    }
    System.out.println("SCORE: " + score);
  }

  String[][][] addIn = { { },
                         { { "Ian", "412" } },
                         { { "Ian", "412" } },
                         { { "Sue", "555" }, { "Ian", "412" } },
                         { { "Sue", "555" }, { "Ian", "412" } },
                         { { "Sue", "555" }, { "Ian", "412" } },
                         { { "Sue", "555" }, { "Ian", "412" } },
                         { { "Sue", "555" }, { "Moe", "848" }, { "Ian", "412" } },
                         { { "Sue", "555" }, { "Moe", "848" }, { "Ian", "412" } },
                         { { "Sue", "555" }, { "Moe", "848" }, { "Ian", "412" } },
                         { { "Sue", "555" }, { "Moe", "848" }, { "Ian", "412" } },
                         { { "Sue", "555" }, { "Moe", "848" }, { "Ian", "412" } },
                         { { "Sue", "555" }, { "Moe", "848" }, { "Ian", "412" } } };
  String[] addLoc = { null, "Ian", "Ian", "Sue", "Sue", "Ian", "Ian", "Sue", "Sue", "Moe", "Moe", "Ian", "Ian" };
  String[][] addEntry = { { "Ann", "123" },
                          { "Ann", "123" },
                          { "Zoe", "321" },
                          { "Ann", "123" },
                          { "Zoe", "321" },
                          { "Ann", "321" },
                          { "Zoe", "606" },
                          { "Ann", "321" },
                          { "Zoe", "606" },
                          { "Ann", "321" },
                          { "Zoe", "606" },
                          { "Ann", "321" },
                          { "Zoe", "606" },
                          { "Ann", "321" },
                          { "Zoe", "606" },
                          { "Ann", "321" },
                          { "Zoe", "606" },
                          { "Ann", "321" },
                          { "Zoe", "606" } };
  String[][][] addOut = { { { "Ann", "321" } },
                          { { "Ann", "321" }, { "Ian", "412" } },
                          { { "Ian", "412" }, { "Zoe", "606" } },
                          { { "Ann", "321" }, { "Sue", "555" }, { "Ian", "412" } },
                          { { "Sue", "555" }, { "Zoe", "606" }, { "Ian", "412" } },
                          { { "Sue", "555" }, { "Ann", "321" }, { "Ian", "412" } },
                          { { "Sue", "555" }, { "Ian", "412" }, { "Zoe", "606" } },
                          { { "Ann", "321" }, { "Sue", "555" }, { "Moe", "848" }, { "Ian", "412" } },
                          { { "Sue", "555" }, { "Zoe", "606" }, { "Moe", "848" }, { "Ian", "412" } },
                          { { "Sue", "555" }, { "Ann", "321" }, { "Moe", "848" }, { "Ian", "412" } },
                          { { "Sue", "555" }, { "Moe", "848" }, { "Zoe", "606" }, { "Ian", "412" } },
                          { { "Sue", "555" }, { "Moe", "848" }, { "Ann", "321" }, { "Ian", "412" } },
                          { { "Sue", "555" }, { "Moe", "848" }, { "Ian", "412" }, { "Zoe", "606" } } };

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
                          { { "Bob", "555" }, { "Ian", "412" } },
                          { { "Bob", "555" }, { "Ian", "412" } },
                          { { "Bob", "555" }, { "Ian", "412" } },
                          { { "Bob", "555" }, { "Ian", "412" } },
                          { { "Bob", "555" }, { "Ian", "412" } },
                          { { "Bob", "555" }, { "Ian", "412" }, { "Sue", "616" } },
                          { { "Bob", "555" }, { "Ian", "412" }, { "Sue", "616" } },
                          { { "Bob", "555" }, { "Ian", "412" }, { "Sue", "616" } },
                          { { "Bob", "555" }, { "Ian", "412" }, { "Sue", "616" } },
                          { { "Bob", "555" }, { "Ian", "412" }, { "Sue", "616" } },
                          { { "Bob", "555" }, { "Ian", "412" }, { "Sue", "616" } },
                          { { "Bob", "555" }, { "Ian", "412" }, { "Sue", "616" } } };
  String[] findName = { "Bob", 
                        "Ann", "Bob", "Eve", 
                        "Ann", "Bob", "Eve", "Ian", "Moe", 
                        "Ann", "Bob", "Eve", "Ian", "Moe", "Sue", "Zoe" };
  String[] findOutName = { null, 
                           "Bob", "Bob", "Bob", 
                           "Bob", "Bob",  "Ian", "Ian", "Ian",
                           "Bob", "Bob", "Ian", "Ian", "Sue", "Sue", "Sue" };

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
}
