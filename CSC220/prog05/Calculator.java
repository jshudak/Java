package prog05;

import java.util.Stack;
import java.util.Scanner;

import javax.swing.SwingUtilities;

import prog02.UserInterface;
import prog02.GUI;
import prog02.ConsoleUI;

public class Calculator {
  static final String OPERATORS = "()+-*/u^";
  static final int[] PRECEDENCE = { -1, -1, 1, 1, 2, 2, 3, 4 };
  Stack<Character> operatorStack = new Stack<Character>();
  Stack<Double> numberStack = new Stack<Double>();
  UserInterface ui = new GUI("Calculator");
  private static boolean prevNoC = false;
  static char prev;
  
  Calculator (UserInterface ui) { this.ui = ui; }

  void emptyStacks () {
    while (!numberStack.empty())
      numberStack.pop();
    while (!operatorStack.empty())
      operatorStack.pop();
  }
  
  int precedence (char op) {
	  switch (op) {
	  case '(': return -1;
	  case ')': return -1;
	  case '+': return 1;
	  case '-': return 1;
	  case '*': return 2;
	  case '/': return 2;
	  case 'u': return 3;
	  case '^': return 4;
	  }
	  return 0;
	  }

  String numberStackToString () {
    String s = "numberStack: ";
    Stack<Double> helperStack = new Stack<Double>();
    // EXERCISE
    while (numberStack.empty()==false) {
    	helperStack.push(numberStack.pop());
    }
    
    while(helperStack.empty()==false) {
    	s = s + " " + numberStack.push(helperStack.pop());
    }
    
    return s;
  }

  String operatorStackToString () {
    String s = "operatorStack: ";
    Stack<Character> helpOpStack = new Stack<Character>();
    // EXERCISE
    while (operatorStack.empty()==false) {
    	helpOpStack.push(operatorStack.pop());
    }
    
    while(helpOpStack.empty()==false) {
    	s = s + " " + operatorStack.push(helpOpStack.pop());
    }
    // EXERCISE

    return s;
  }

  void displayStacks () {
    ui.sendMessage(numberStackToString() + "\n" +
                   operatorStackToString());
  }

  void doNumber (double x) {
	if (operatorStack.empty() == false && operatorStack.peek() == 'u') {
		numberStack.push(-x);
		operatorStack.pop();
	}
	
	else {
    numberStack.push(x);
	}
	
    displayStacks();
  }

  void doOperator (char op) {
	if (op == '-') {
		 if (numberStack.empty() == true || prevNoC == false) op = 'u';
	}
	
    processOperator(op);
    displayStacks();
    
    if (op == ')') prevNoC = true;
    else prevNoC = false;
  }

  double doEquals () {
    while (!operatorStack.empty())
      evaluateTopOperator();

    return numberStack.pop();
  }
    
  double evaluateOperator (double a, char op, double b) {
    switch (op) {
    case '+':
      return a + b;
    case '-':
    	return a - b;
    case '*':
    	return a*b;
    case '/':
    	return a/b;
    case '^':
    	if (a < 0) return (-1*Math.pow(a, b));
    	else return Math.pow(a, b);
      // EXERCISE
    }
    System.out.println("Unknown operator " + op);
    return 0;
  }

  void evaluateTopOperator () {
    char op = operatorStack.pop();
    
    Double b = numberStack.pop();
    Double a = numberStack.pop();
    numberStack.push(evaluateOperator(a, op, b));
    displayStacks();
	}

  void processOperator (char op) {	  
	if (op == '(' || op == 'u') {
		operatorStack.push(op);
	}
	
	
	else if (operatorStack.empty() == false) {
	
		if (op == ')') {
			while (operatorStack.empty() == false && (operatorStack.peek() != '(')) {
				evaluateTopOperator();
			}
			operatorStack.pop();
			return;
		}
		
		else {
			while (operatorStack.empty() == false && precedence(operatorStack.peek()) >= precedence(op)) {
				evaluateTopOperator();
			}
			operatorStack.push(op);
    
		}
	}
	
	
	
	else if (operatorStack.empty() == true) {
	    operatorStack.push(op);
	}
   
  }
  
  static boolean checkTokens (UserInterface ui, Object[] tokens) {
      for (Object token : tokens)
        if (token instanceof Character &&
            OPERATORS.indexOf((Character) token) == -1) {
          ui.sendMessage(token + " is not a valid operator.");
          return false;
        }
      return true;
  }

  static void processExpressions (UserInterface ui, Calculator calculator) {
    while (true) {
      String line = ui.getInfo("Enter arithmetic expression or cancel.");
      if (line == null)
        return;
      Object[] tokens = Tokenizer.tokenize(line);
      if (!checkTokens(ui, tokens))
        continue;
      try {
        for (Object token : tokens)
          if (token instanceof Double) {
            calculator.doNumber((Double) token);
            prevNoC = true;
          }
          else {
            calculator.doOperator((Character) token);
            prev = (Character)token;
          }
        double result = calculator.doEquals();
        calculator.emptyStacks();
        ui.sendMessage(line + " = " + result);
        prevNoC = false;
      } catch (Exception e) {
    	calculator.emptyStacks();
        throw e;
      }
    }
  }

  public static void main (String[] args) {
    UserInterface ui = new ConsoleUI();
    Calculator calculator = new Calculator(ui);
    processExpressions(ui, calculator);
  }
}
