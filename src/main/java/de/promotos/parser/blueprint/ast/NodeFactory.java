package de.promotos.parser.blueprint.ast;

import java.util.List;

import de.promotos.parser.blueprint.parser.Match;

public abstract class NodeFactory<NodeType> {

	protected Match<NodeType> match;
	
	public NodeFactory(Match<NodeType> match) {
		this.match = match;
	}
	
	public abstract NodeType getDescentMark();

	public abstract void addChildren(NodeType node, List<NodeType> children);
		
	public abstract NodeType createInputLine();
	
	public abstract NodeType createNumber();
	
	public abstract NodeType createDigit();
	
	public abstract NodeType createOperator();
	
	public abstract NodeType createBraces();
}
