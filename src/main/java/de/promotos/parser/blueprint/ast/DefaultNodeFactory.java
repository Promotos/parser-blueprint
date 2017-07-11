package de.promotos.parser.blueprint.ast;

import java.util.List;

import de.promotos.parser.blueprint.ast.nodes.Digit;
import de.promotos.parser.blueprint.ast.nodes.InputLine;
import de.promotos.parser.blueprint.ast.nodes.Node;
import de.promotos.parser.blueprint.ast.nodes.Num;
import de.promotos.parser.blueprint.ast.nodes.Operator;
import de.promotos.parser.blueprint.ast.nodes.Sqrt;
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
		return createWithText(new InputLine());
	}

	@Override
	public Node createNumber() {
		return createWithText(new Num());
	}
	
	@Override
	public Node createDigit() {
		return createWithText(new Digit());
	}
	
	@Override
	public Node createOperator() {
		return createWithText(new Operator());
	}

	@Override
	public Node createBraces() {
		return createEmpty(new Braces());
	}

	@Override
	public Node createSqrt() {
		return createEmpty(new Sqrt());
	}
	
	private Node createEmpty(final Node n) {
		n.setOffset(match.getStartOffset(), match.getEndOffset());
		return n;
	}
	
	private Node createWithText(final Node n) {
		final Node n1 = createEmpty(n);
		n1.setText(match.getMatch());
		return n1;
	}
	
}
