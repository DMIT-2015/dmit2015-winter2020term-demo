package ca.nait.dmit.demo.servlet;

import lombok.Getter;
import lombok.Setter;

public class SimpleCalculator {

	@Getter @Setter
	private double operand1 = 1;

	@Getter @Setter
	private double operand2 = 2;
	
	@Getter @Setter
	private double result = operand1 + operand2;
	
	public void add() {
		result = operand1 + operand2;
	}
	
	public void subtract() {
		result = operand1 - operand2;
	}
	
	public void multiply() {
		result = operand1 * operand2;
	}

	public void divide() {
		result = (operand2 == 0) ? 0 : (operand1 / operand2);
	}

}
