package prog03;
import prog02.UserInterface;
import prog02.GUI;
import prog02.TestUI;

/**
 *
 * @author vjm
 */
public class Main {
  /** Use this variable to store the result of each call to fib. */
  public static double fibn;
  
  /** Determine the average time in microseconds it takes to calculate
      the n'th Fibonacci number.
      @param fib an object that implements the Fib interface
      @param n the index of the Fibonacci number to calculate
      @param ncalls the number of calls to average over
      @return the average time per call
  */
  public static double averageTime (Fib fib, int n, int ncalls) {
    // Get the current time in nanoseconds.
    long start = System.nanoTime();

    for (int i = 0; i < ncalls; i++)
      fibn = fib.fib(n);

    // Get the current time in nanoseconds.
    long end = System.nanoTime();

    // Return the average time converted to microseconds averaged over ncalls.
    return ((end - start) / 1000.0 )/ ncalls;
  }

  /** Determine the time in microseconds it takes to to calculate the
      n'th Fibonacci number.  Average over enough calls for a total
      time of at least one second.
      @param fib an object that implements the Fib interface
      @param n the index of the Fibonacci number to calculate
      @return the time it it takes to compute the n'th Fibonacci number
  */
  public static double accurateTime (Fib fib, int n) {
    // Get the time in microseconds using the time method above.
    double t = averageTime(fib, n, 1);

    // If the time is (equivalent to) more than a second, return it.
    if (t > 1000000) {return t;}


    // Estimate the number of calls that would add up to one second.
    // Use   (int)(YOUR EXPESSION)   so you can save it into an int variable.
    int numcalls = (int)(1000000/t);


    // Get the average time using averageTime above and that many
    // calls and return it.
    return averageTime(fib, n, numcalls);
  }

  private static UserInterface ui = new prog03.TestUI("Fibonacci experiments");

  /** Get a non-negative integer from the using using ui.
      If the user enters a negative integer, like -2, say
      "-2 is negative...invalid"
      If the user enters a non-integer, like abc, say
      "abc is not an integer"
      If the user clicks cancel, return -1.
      @return the non-negative integer entered by the user or -1 for cancel.
  */
  static int getInteger () {
    String n = ui.getInfo("Enter n");
    
    if (n == null) return -1;
    
    if (n.length() == 0)	{ 
    	ui.sendMessage(n + " is not an integer");
        return 0;
    }
    
    int intGot = 0;
    
    try {
    	intGot = Integer.parseInt(n);
    	
    	if (intGot < 0) {
    	      ui.sendMessage(n +"is negative... invalid");
    	      return 0;
    	     }
    }
    
    catch (NumberFormatException e) {
    	ui.sendMessage(n + " is not an integer");
    	return 0;
    	
    }
    	
    return intGot;
  }

  public static void doExperiments (Fib fib) {
    System.out.println("doExperiments " + fib);
    
    String[] yesNO = {"Yes",
    		"No"
    		};
    double c = 0;
    double tooLong = 3.6e+9;
    
    while (true) {
		  int expInt = getInteger();
		  if (expInt == -1) return;
		  if (expInt == 0) continue;
		  double estTime = 0;
		  
		  if (c == 0) {
			  double time = accurateTime(fib, expInt);
			  ui.sendMessage("fib(" + expInt + ") = " + fibn + " in " + time + " microseconds.");
			  c = time/fib.O(expInt);
		  }
		  
		  else {
		  estTime = c * fib.O(expInt);
		  
		  ui.sendMessage("Estimated running time is: " + estTime + " microseconds.");
		  
		  if(estTime > tooLong) {
				ui.sendMessage("Estimated time is more than 1 hr.\nI will ask you if you really want to run it next.");
            if(ui.getCommand(yesNO) == 1)
            	continue;
			}
		  double time = accurateTime(fib, expInt);
		  
		  ui.sendMessage("fib(" + expInt + ") = " + fibn + " in " + time + " microseconds. " + ((estTime-time)/time)*100 + "% error.");
		  
		  c = time/fib.O(expInt);
		  }
		  
	  }
  }

  public static void doExperiments () {
	   // EXERCIZE 11
	  String[] commands = {
			   "ExponentialFib",
			   "LinearFib",
			   "LogFib",
			   "ConstantFib",
			   "MysteryFib",
			   "Exit"
			  };

			  while (true) {
				  
			   int c = ui.getCommand(commands);
			   switch (c) { 
			   
			   case -1:
				     ui.sendMessage("You shut down the program.  Use Exit to exit.");
				     break;

				    case 0:
				    doExperiments(new ExponentialFib());
				      break;
			   
				    case 1:
				     doExperiments(new LinearFib());
				      break;
				      
				    case 2:
				    doExperiments(new LogFib());

				    case 3:
				     doExperiments(new ConstantFib());
				     break;

				    case 4:
				    doExperiments(new MysteryFib());
				    break;
				    
				    case 5:
				    return;
				        }
			   		
			  		}
			   }

  static void labExperiments () {
    // Create (Exponential time) Fib object and test it.
    Fib efib = new ConstantFib();
    System.out.println(efib);
    for (int i = 0; i < 11; i++)
      System.out.println(i + " " + efib.fib(i));
    
    // Determine running time for n1 = 20 and print it out.
    int n1 = 20;
    double time1 = accurateTime(efib, n1);
    System.out.println("Running for " + n1 + " times:                                 " + time1);
    
    // Calculate constant:  time = constant times O(n).
    double con = time1 / efib.O(n1);
    System.out.println("Constant Created:                                     " + con);
    
    // Estimate running time for n2=30.
    int n2 = 30;
    double time2est = con * efib.O(n2);
    System.out.println("Estimating time for " + n2 + "th fib:                          " + time2est);
    
    // Calculate actual running time for n2=30.
    double time2 = accurateTime(efib, n2);
    System.out.println("Finding the " + n2 + "th fib, using accurate time:            " + time2);
    
    // Step 2, Calculate avg time for 1 second of running
    int ncalls = (int)(1000000/time2);
    time2 = averageTime (efib, n2, ncalls);
    System.out.println("Finding the " + n2 + "th fib, using average time:             " + time2);
    
    // Step 3
    time2 = accurateTime(efib, n2);
    System.out.println("Finding the " + n2 + "th fib, using accurate time:            " + time2);
    
    //Step 4
    int n3 = 100;
    double time3est =  con * efib.O(n3);
    System.out.println("Estimating time for " + n3 + " runs:                         " + time3est);
    double years = time3est/(3.154e13);
    System.out.println("That is                                               " + years + " years");
  }

  /**
   * @param args the command line arguments
   */
  public static void main (String[] args) {
   // labExperiments();
   // int n = getInteger();
   // ui.sendMessage("You entered " + n);
   // doExperiments(new ExponentialFib());
    doExperiments();
  }
}
