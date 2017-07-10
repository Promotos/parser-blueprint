package de.promotos.parser.blueprint.parser;

import java.util.LinkedList;

import org.parboiled.Context;
import org.parboiled.ContextAware;

import de.promotos.parser.blueprint.ast.NodeFactory;

public class ValueStack<Type> implements ContextAware<Type> {

	protected NodeFactory<Type> nodeFactory;
	private Context<Type> context;
	protected final Type DESCENT_MARK;
	
	public ValueStack(NodeFactory<Type> nodeFactory) {
		this.nodeFactory = nodeFactory;
		this.DESCENT_MARK = nodeFactory.getDescentMark();
	}
	
	public boolean push(Type node) {
		context.getValueStack().push(node);
		context.getValueStack().push(DESCENT_MARK);
		return true;
	}
	
	public boolean build() {
		LinkedList<Type> children = new LinkedList<Type>();
		while (true) {
			Type next_node = context.getValueStack().pop();
			if (next_node == DESCENT_MARK) break;
			children.addFirst(next_node);
		}
		if (context.getValueStack().peek() == null) {
			context.getValueStack().pop(); // discard empty stack points and their children
		} else {
			nodeFactory.addChildren(context.getValueStack().peek(), children);
		}
		return true;
	}

	public void setContext(Context<Type> context) {
		this.context = context;
	}
}
