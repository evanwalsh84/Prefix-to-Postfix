/* 
Evan Walsh
3200
Section 1
This program takes in multiple strings containing variable values and equations. It then translates the equation from prefix
form into postfix form. After translating the form of the equation it evaluates the total of the equation assuming 
non-assigned values are equal to zero.
*/
import java.io.*;
import java.util.*;
public class infoToPost {
	 public static void main(String[] args)
	  {
		 Scanner kbd = new Scanner(System.in);
		 String input[] = new String[9];
		 input = readInput(kbd);
		 System.out.println("");
		 getPostFix(input);
		 for(int x = 0; x < input.length; x++)
		 {
			 System.out.println(input[x]);
		 }
	  }
	 //Reads in the input and returns a string with the read in data.
	 public static String[] readInput(Scanner kbd)
	 {
		int size = kbd.nextInt();
		int y = 0;
		String input[] = new String[size];
		for(int x = 0; x < size && kbd.hasNext(); x++)
		{
			String next = kbd.nextLine();
				input[x] = next;
				y = 0;
		}
		return input;
	 }
	 	//Takes in an infix string and translates it to potsfix.
		public static void getPostFix(String[] infix)
		{
			 int infixloc = 0;
			 Stack<Character> prefix = new Stack<Character>();
			 //Finds the location of the first equation to postfix.
			 for (int place = 0; place < infix.length; place++)
			 {
				 if(infix[place].equals("$PART2"))
				 {
					 break;
				 }
				 else
				 {
					 infixloc++;
				 }
			 }
			 //Loops through the amount of times required to potsfix all equations.
			 for(int infixes = infixloc; infixloc < infix.length; infixloc++)
			 {
			//Prints out the original infix equation.
			System.out.println("Infix: " + infix[infixloc+1]);
			//Declares a stack of characters to store operators.
			Stack<Character> operators = new Stack<Character>(); 
			infix[infixloc+1]="("+infix[infixloc+1]+")";
			//Creates a string to store the postfix equation.
			String postfix = "";
			//Holds the current variable being modified.
			char x; 
			//Loops through the enitre equation at the given index.
			for (int i = 0; i < infix[infixloc+1].length(); i++)
				{
					x = infix[infixloc+1].charAt(i);
					//Adds x to the string of characters.
					if (Character.isLetter(x) == true) 
					{
						postfix += x;
					} 
					//Pushes the paranhesis onto the stack of operators.
					else if (x == '(') 
					{
						operators.push(x);
					} 
					//Adds to the postfix until a closing bracket is encountered.
					else if (x == ')')
					{// if character is the closing brace
						char y;// the top char of stack
						while ((y = stacktop(operators)) != '(') 
						{
							postfix += y;
							
							if(!operators.isEmpty())
							{
								operators.pop();
							}
						}
					//Removes the open brace when encountered.
					operators.pop();
					} 
					//Adds remaining operators based on precedence.
					else 
					{
						while (precedence(x) <= precedence(stacktop(operators))) 
						{
							postfix += stacktop(operators);
							//Checks if the stack of operators is empty and takes another if not.
							if(!operators.isEmpty())
							{
								operators.pop();
							}
						}
						//pushes the input operator.
					operators.push(x);
					}
			
				}
			//Prints the postfix equation.
			System.out.println("Postfix: " + postfix);
			//Evaluates the result of the postfix equation.
			evaluatepostfix(infix,infixloc);
			 }
	}
		//Calculates the input postfix equation and returns the value.
		public static void evaluatepostfix(String[] infix, int infixloc)
		{
			int total = 0;
			//Fills the array variables with the input values.
			int [] variables = getvalues(infix);
			//Loops through checking for operators and evaluates the expression.
			for(int i = 0; i < infix[infixloc].length(); i++)
			{
				if(infix[infixloc].indexOf(i) == '*')
				{
					total = variables[(int)infix[infixloc].indexOf(i-2)-(int)'a'] * variables[(int)infix[infixloc].indexOf(i-1)-(int)'a'];
				}
				else if(infix[infixloc].indexOf(i) == '/')
				{
					total = variables[(int)infix[infixloc].indexOf(i-2)-(int)'a'] / variables[(int)infix[infixloc].indexOf(i-1)-(int)'a'];
				}
				else if(infix[infixloc].indexOf(i) == '+')
				{
					total = variables[(int)infix[infixloc].indexOf(i-2)-(int)'a'] + variables[(int)infix[infixloc].indexOf(i-1)-(int)'a'];
				}
				else if(infix[infixloc].indexOf(i) == '-')
				{
					total = variables[(int)infix[infixloc].indexOf(i-2)-(int)'a'] - variables[(int)infix[infixloc].indexOf(i-1)-(int)'a'];
				}
			}
			//Prints the result of the input equation.
			System.out.println("Result: " + total + "\n");
		}
		//Obtains values from the input strings.
		public static int[] getvalues(String[] infix)
		{
			 //Creates an array to store the value of a total of 26 possibles variables.
			 int [] variables = new int[26];
			 for(int defaultval = 0; defaultval < 26; defaultval++)
			 {
				 variables[defaultval] = 0;
			 }
			 //Loops through the input strings until $PART2 is reached and stores the input values in the array.
			 for (int place = 0; place < infix.length; place++)
			 {
				 if(infix[place].equals("$PART2"))
				 {
					 break;
				 }
				 else
				 {
					 int letter = (int)infix[place].indexOf(0) - (int)'A';
					 variables[0] = (int)infix[place].indexOf(0) - (int)'0';
				 }
			 }
			 return variables;
		}
		//Checks if there is another item on the stack.
		public static Character stacktop(Stack operatorStack) {
		    if(operatorStack.isEmpty())//if stack is not empty 
		    {
		    	return '0';
		    }
		    else
		    {
		    	  return (Character) operatorStack.peek();
		    }
		}
		//Gives each operator a precedence based on the order of operations.
		public static int precedence(char operator) 
			{
		    if (operator == '*' || operator == '/')
		    {
		        return 2;
		    }
		    else if (operator == '+' || operator == '-')
		    {
		        return 1;
		    }
		    else
		    {
		    return 0;
		    }
		}
}
