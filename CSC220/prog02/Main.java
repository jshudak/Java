package prog02;

/**
 * A program to query and modify the phone directory stored in csc220.txt.
 * @author vjm
 */
public class Main {

 /** Processes user's commands on a phone directory.
      @param fn The file containing the phone directory.
      @param ui The UserInterface object to use
      to talk to the user.
      @param pd The PhoneDirectory object to use
      to process the phone directory.
	 */
 public static void processCommands(String fn, UserInterface ui, PhoneDirectory pd) {
  pd.loadData(fn);
  boolean changed = false;

  String[] commands = {
   "Add/Change Entry",
   "Look Up Entry",
   "Remove Entry",
   "Save Directory",
   "Exit"
  };

  String name, number, oldNumber;

  while (true) {
   int c = ui.getCommand(commands);
   switch (c) {

    case -1:
     ui.sendMessage("You shut down the program, restarting.  Use Exit to exit.");
     break;

    case 0:
     name = ui.getInfo("Enter name:");
     if ((name == null) || (name.length() == 0)) {
      ui.sendMessage("No name was entered.\nReturning to menu...");
      break;
     }

     number = ui.getInfo("Enter their number or email:");
     if (number == null) {
      ui.sendMessage("Add / change was cancelled.\nReturning to menu...");
      break;
     }

     oldNumber = pd.addOrChangeEntry(name, number);

     if (oldNumber == null) {
      ui.sendMessage(name + " was added to the directory.\nNew number: " + number);
      changed = true;
      break;
     }
     ui.sendMessage("Number for " + name + " was changed.\nOld number: " + oldNumber + "\nNew number: " + number);

     changed = true;
     break;

    case 1:
     name = ui.getInfo("Enter name");

     if ((name == null) || (name.length() == 0)) {
      ui.sendMessage("No name was entered.\nReturning to menu...");
      break;
     }
     // check if user cancelled OR left name blank - if so, break
     // name.length() == 0 if empty, name.equals(null) if cancelled

     number = pd.lookupEntry(name);
     if (number == null) {
      ui.sendMessage("Name does not exist in the directory.");
      break;
     }
     // check if number is null - if so, say name does not exist in directory

     ui.sendMessage(name + " has number " + number);
     break;

    case 2:
    name = ui.getInfo("Enter name");

    if ((name == null) || (name.length() == 0)) {
     ui.sendMessage("No name was entered.\nReturning to menu...");
     break;
     }
            
     number = pd.removeEntry(name);
     
     if (number == null) {
      ui.sendMessage("Name does not exist in the directory.");
      break;
     }
     
     ui.sendMessage(name + ", with number " + number + ", has been removed from the directory.");
    	
     changed = true;	
     break;

    case 3:
     pd.save();
     changed = false;
     break;

    case 4:
    	String[] yesNo = {"Yes", "No"};
    	
        if(changed == true) {
        	ui.sendMessage("The directory is not saved. Are you sure you want to quit without saving?\n                       Answer yes or no on the next prompt.");
        	c = ui.getCommand(yesNo);
        	
        	if (c == 1) {
        		break;
        	}
        }
     return;
   }  
  }
 }

 /**
  * @param args the command line arguments
  */
 public static void main(String[] args) {
  String fn = "csc220.txt";
  PhoneDirectory pd = new SortedPD();
  UserInterface ui = new TestUI("Phone Directory");
  processCommands(fn, ui, pd);
 }
}