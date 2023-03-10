package prog04;

import javax.swing.*;

import prog02.UserInterface;

/**
 *
 * @author vjm
 */
public class TestUI implements UserInterface {

  /** Creates a new instance of TestUI */
  public TestUI() {
  }

  /** Creates a new instance of TestUI */
  public TestUI(String s) {
  }

  int iCase = -1;
  int[] cases = { 1, 1, 2, 2, 0, 0, 0, 0, 1, 1, 2, 2, 0, 0, 4, 3, 4 };
  int iInfo = 0;
  String[][] info  =
  { { "Fred" }, { "Victor" },
    { "Victor" }, { "Victor" },
    { "Victor", "" },
    { "Fred", "fred" }, { "Fred", "777" },
    { "Victor", null }, 
    { null }, { "" }, { null }, { "" }, { null }, { "" },
    { },
    { },
    { } };

  /** presents set of commands for user to choose one of
      @param commands the commands to choose from
      @return the index of the command in the array
  */
  public int getCommand (String[] commands) {
    if (commands.length == 2) {
      System.out.println("commands: " + commands[0] + " " + commands[1]);
      int i = commands[1].equalsIgnoreCase("yes") ? 0 : 1;
      System.out.println("selecting " + commands[i]);
      System.out.println("-3 if it does not do a case 3 (save) before exiting");
      return i;
    }
    if (iCase >= 0 && iInfo < info[iCase].length)
      System.out.println("ERROR (-3 points):  break too soon.");
    System.out.println("\t" + "case " + cases[++iCase] + ":");
    iInfo = 0;
    return cases[iCase];
  }

  /** tell the user something
      @param message string to print out to the user
  */
  public void sendMessage (String message) {
    if (message == null || message.contains("null"))
      System.out.println("ERROR (-3 points):  message contains null.");
    System.out.println(message);
  }

  /** prompts the user for a string
      @param prompt the request
      @return what the user enters, null if nothing
  */
  public String getInfo (String prompt) {
    System.out.println("\t" + prompt);
    if (iInfo == info[iCase].length) {
      System.out.println("ERROR (-3 points):  missing break.");
      return null;
    }
    String ret = info[iCase][iInfo++];
    System.out.println("\t" + "? " + ret);
    return ret;
  }

  public static void main (String[] args) {
    UserInterface ui = new TestUI();
    String[] commands = { "hello", "how", "are", "you" };
    int choice = ui.getCommand(commands);
    ui.sendMessage("You chose " + choice);
    String result = ui.getInfo("say something");
    ui.sendMessage("You said " + result);
  }
}
