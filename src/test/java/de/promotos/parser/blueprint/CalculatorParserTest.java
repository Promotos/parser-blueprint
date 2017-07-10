package de.promotos.parser.blueprint;

import org.parboiled.Parboiled;
import org.parboiled.common.StringUtils;

import de.promotos.parser.blueprint.ast.nodes.Node;
import de.promotos.parser.blueprint.parser.CalculatorParser;

public class CalculatorParserTest {

	@SuppressWarnings("unchecked")
	protected CalculatorParser<Node> getParser() {
		return Parboiled.createParser(CalculatorParser.class);
	}
	
	protected String printNodes(final Node root) {
		return printNodes(root, 0);
	}

	protected String printNodes(final Node root, int indent) {
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < indent; i++) {
			sb.append(' ');
		}
		sb.append(printNode(root));
		for (Node n : root.getChildren()) {
			sb.append("\n").append(printNodes(n, indent + 1 ));
		}
		return sb.toString();
	}
	
	private String printNode(final Node n) {
		final StringBuilder sb = new StringBuilder(n.getClass().getSimpleName());
		if (n.hasText()) {
			sb.append(":").append(StringUtils.escape(n.getText()));
		}
		return sb.toString();
	}
}
