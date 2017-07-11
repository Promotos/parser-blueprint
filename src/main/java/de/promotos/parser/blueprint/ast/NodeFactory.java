package de.promotos.parser.blueprint.ast;

import java.util.List;

import de.promotos.parser.blueprint.parser.Match;

public abstract class NodeFactory<Node> {

	protected Match<Node> match;
	
	public NodeFactory(Match<Node> match) {
		this.match = match;
	}
	
	public abstract Node getDescentMark();

	public abstract void addChildren(Node node, List<Node> children);
		
	public abstract Node createInputLine();
	
	public abstract Node createNumber();
	
	public abstract Node createDigit();
	
	public abstract Node createOperator();
	
	public abstract Node createBraces();

	public abstract Node createSqrt();
}
