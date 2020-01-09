package ca.nait.dmit.domain;

import javax.swing.JOptionPane;

/**
 * This Java program demonstrate how to use the Java Swing API class JOptionPane to prompt for user input
 * and to display output to the user.
 * 
 * @author Sam Wu
 * @version 2020.01.09
 */
public class Calculator {

	public static void main(String[] args) {
		// Prompt the user to enter a math expression (eg. 3 + 5)
		String mathExpression = JOptionPane.showInputDialog("Enter a math expression (eg. 3 + 5)");
		// Extract operand1, operator, operator2 from the mathExpression value
		// Assume operand1 and operand2 is always a single digit value
		// Assume operator is either +, -, ., /
		// Example of mathExpression:	
		//		"3+5"
		//		"3 + 5"
		//		"3 +		5"
	
		mathExpression = mathExpression.replaceAll(" ", "");
		int operand1 = Character.getNumericValue(mathExpression.charAt(0));
		char operator = mathExpression.charAt(1);
		int operand2 = Character.getNumericValue(mathExpression.charAt(2));
		
		int result = 0;
		// Determine the result (+, -, ., /)
		// Use the . symbol for multiplication instead of * symbol, 
		// as * refers to all the files in the current directory
		if (operator == '+') {
			result = operand1 + operand2;
		} else if (operator == '-') {
			result = operand1 - operand2;
		} else if( operator == '.' ) {
			result = operand1 * operand2;
		} else if( operator == '/' ) {
			result = operand1 / operand2;
		}		
		
		// Display the result
		//String message = operand1 + " " + operator + " " + operand2 + " = " + result;
		String message = String.format("%s %s %s = %d", operand1, operator, operand2, result);
		JOptionPane.showMessageDialog(null, message);
		
	}

}