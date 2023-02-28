package prog07;

import prog02.ConsoleUI;

public class TestEntryComparator extends WordPath {
  TestEntryComparator () {
    super(new ConsoleUI());
  }

  void test () {
    EntryComparator comp = new EntryComparator("rain");
    Entry said = new Entry("said");
    Entry rain = new Entry("rain");
    int c = comp.compare(said, rain);
    System.out.println("compare(said, rain) = " + c);
    if (c != 2) {
      System.out.println("failed");
      return;
    }

    c = comp.compare(rain, said);
    System.out.println("compare(rain, said) = " + c);
    if (c != -2) {
      System.out.println("failed");
      return;
    }
    
    System.out.println("Setting distance from rain to start to 3.");
    rain.prev = new Entry("raid");
    rain.prev.prev = new Entry("said");
    rain.prev.prev.prev = new Entry("slid");

    c = comp.compare(said, rain);
    System.out.println("compare(said, rain) = " + c);
    if (c != -1) {
      System.out.println("failed");
      return;
    }

    c = comp.compare(rain, said);
    System.out.println("compare(rain, said) = " + c);
    if (c != 1) {
      System.out.println("failed");
      return;
    }
    
    System.out.println("success");
  }

  public static void main (String[] args) {
    TestEntryComparator test = new TestEntryComparator();
    test.test();
  }
}
