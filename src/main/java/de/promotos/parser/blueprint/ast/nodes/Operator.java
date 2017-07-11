package de.promotos.parser.blueprint.ast.nodes;

import org.parboiled.errors.ParsingException;

public class Operator extends Node {

	public enum OperatorType {
		Plus, Minus, Multiply, Divide
	}

	private OperatorType type;

	@Override
	public void setText(String text) {
		super.setText(text);
		parserOperator(text);
	}
	
	public OperatorType getOperator() {
		return type;
	}

	private void parserOperator(final String text) {
		switch (text.trim()) {
		case "+":
			type = OperatorType.Plus;
			break;
		case "-":
			type = OperatorType.Minus;
			break;
		case "*":
			type = OperatorType.Multiply;
			break;
		case "/":
			type = OperatorType.Divide;
			break;
			default:
				throw new ParsingException("Unknown operator " + text);
		}
	}
}
