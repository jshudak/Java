package prog05;

import java.util.EmptyStackException;

public class TestStack {
  static boolean test (StackInterface<Integer> stack) {
    try {
      boolean empty = stack.empty();
      System.out.println("empty() returns " + empty);
      if (!empty)
        return false;
    } catch (Exception e) {
      System.out.println("empty() throws exception " + e);
    }

    try {
      int peek = stack.peek();
      System.out.println("peek() returns " + peek);
      return false;
    } catch (EmptyStackException e) {
      System.out.println("peek() throws exception " + e);
    } catch (Exception e) {
      System.out.println("peek() throws exception " + e);
      return false;
    }

    try {
      int pop = stack.pop();
      System.out.println("pop() returns " + pop);
      return false;
    } catch (EmptyStackException e) {
      System.out.println("pop() throws exception " + e);
    } catch (Exception e) {
      System.out.println("pop() throws exception " + e);
      return false;
    }

    System.out.println("Pushing 0,1,2,3,4,5,6,7,8,9.");
    try {
      System.out.print("push returns");
      for (int i = 0; i < 10; i++) {
        int j = stack.push(i);
        System.out.print(" " + j);
        if (j != i) {
          System.out.println();
          return false;
        }
      }
      System.out.println();
    } catch (Exception e) {
      System.out.println();
      System.out.println("push() throws exception " + e);
      return false;
    }

    try {
      boolean empty = stack.empty();
      System.out.println("empty() returns " + empty);
      if (empty)
        return false;
    } catch (Exception e) {
      System.out.println("empty() throws exception " + e);
    }

    try {
      int peek = stack.peek();
      System.out.println("peek() returns " + peek);
      if (peek != 9)
        return false;
    } catch (Exception e) {
      System.out.println("peek() throws exception " + e);
      return false;
    }

    try {
      int pop = stack.pop();
      System.out.println("pop() returns " + pop);
      if (pop != 9)
        return false;
    } catch (Exception e) {
      System.out.println("pop() throws exception " + e);
      return false;
    }

    try {
      int push = stack.push(9);
      System.out.println("push(9) returns " + push);
      if (push != 9)
        return false;
    } catch (Exception e) {
      System.out.println("push() throws exception " + e);
      return false;
    }

    System.out.println("Popping 10 times.");
    try {
      System.out.print("pop returns");
      for (int i = 0; i < 10; i++) {
        int j = stack.pop();
        System.out.print(" " + j);
        if (j != 9 - i) {
          System.out.println();
          return false;
        }
      }
      System.out.println();
    } catch (Exception e) {
      System.out.println();
      System.out.println("pop() throws exception " + e);
      return false;
    }

    try {
      boolean empty = stack.empty();
      System.out.println("empty() returns " + empty);
      if (!empty)
        return false;
    } catch (Exception e) {
      System.out.println("empty() throws exception " + e);
    }

    return true;
  }

  public static void main (String[] args) {
    StackInterface<Integer> stack = new ListStack<Integer>();

    if (test(stack))
      System.out.println("Stack worked:  15/15 points");
    else
      System.out.println("Stack failed:  0/15 points");
  }
}
