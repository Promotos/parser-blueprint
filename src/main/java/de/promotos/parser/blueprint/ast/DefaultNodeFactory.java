package de.promotos.parser.blueprint.ast;

import java.util.List;

import de.promotos.parser.blueprint.ast.nodes.Digit;
import de.promotos.parser.blueprint.ast.nodes.InputLine;
import de.promotos.parser.blueprint.ast.nodes.Node;
import de.promotos.parser.blueprint.ast.nodes.Num;
import de.promotos.parser.blueprint.ast.nodes.Operator;
import de.promotos.parser.blueprint.ast.nodes.Braces;
import de.promotos.parser.blueprint.parser.Match;

public class DefaultNodeFactory extends NodeFactory<Node> {

	public static boolean CREATE_SYNTAX_NODES = true;
	
	public DefaultNodeFactory(Match<Node> match) {
		super(match);
	}

	public static final Node DESCENT_MARK = new Node("DESCENT_MARK");
	
	public Node getDescentMark() {
		return DESCENT_MARK;
	}
	
	@Override
	public void addChildren(Node node, List<Node> children) {
		for (Node n : children) {
			node.addChild(n);
		}
	}

	@Override
	public Node createInputLine() {
		InputLine n = new InputLine();
		n.setText(match.getMatch());
		n.setOffset(match.getStartOffset(), match.getEndOffset());		
		return n;
	}

	@Override
	public Node createNumber() {
		Num n = new Num();
		n.setText(match.getMatch());
		n.setOffset(match.getStartOffset(), match.getEndOffset());		
		return n;
	}
	
	@Override
	public Node createDigit() {
		Digit n = new Digit();
		n.setText(match.getMatch());
		n.setOffset(match.getStartOffset(), match.getEndOffset());		
		return n;
	}
	
	@Override
	public Node createOperator() {
		Operator n = new Operator();
		n.setText(match.getMatch());
		n.setOffset(match.getStartOffset(), match.getEndOffset());		
		return n;
	}

	@Override
	public Node createBraces() {
		Braces n = new Braces();
		n.setOffset(match.getStartOffset(), match.getEndOffset());		
		return n;
	}
	
}
