package de.promotos.parser.blueprint.parser;

import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;

import de.promotos.parser.blueprint.ast.DefaultNodeFactory;
import de.promotos.parser.blueprint.ast.NodeFactory;

public class CalculatorParser<Type> extends BaseParser<Type> {

	protected Match<Type> match;
	protected NodeFactory<Type> nodeFactory;
	protected ValueStack<Type> valueStack;
	protected final BaseRules<Type> baseRules;
	
	@SuppressWarnings("unchecked")
	public CalculatorParser() {
		match = new Match<Type>();
		nodeFactory = createNodeFactory(match);
		valueStack = new ValueStack<Type>(nodeFactory);
		baseRules = Parboiled.createParser(BaseRules.class, this);
	}
	
	@SuppressWarnings("unchecked")
	public NodeFactory<Type> createNodeFactory(@SuppressWarnings("rawtypes") Match match) {
		return (NodeFactory<Type>) new DefaultNodeFactory(match);
	}
	
    public Rule InputLine() {
        return Sequence(
        		ACTION(match.clear()),
        		ACTION(valueStack.push(nodeFactory.createInputLine())),
        		Expression(), 
        		EOI,
        		ACTION(valueStack.build())
        	);
    }

    public Rule Expression() {
        return OperatorRule(Term(), FirstOf("+ ", "- "));
    }

    public Rule Term() {
        return OperatorRule(Factor(), FirstOf("* ", "/ "));
    }

    public Rule Factor() {
        // by using toRule("^ ") instead of Ch('^') we make use of the fromCharLiteral(...) transformation below
        return OperatorRule(Atom(), toRule("^ "));
    }

    public Rule OperatorRule(Rule subRule, Rule operatorRule) {
        return Sequence(
                subRule,
                ZeroOrMore(
                		operatorRule,
                		ACTION(match.reset()),
                		ACTION(valueStack.push(nodeFactory.createOperator())),
                        subRule,
                        ACTION(valueStack.build())
                )
        	);
    }

    public Rule Atom() {
        return FirstOf(
        		Number(), 
        		SquareRoot(), 
        		Parens()
        	);
    }

    public Rule SquareRoot() {
        return Sequence(
        		"SQRT",
        		ACTION(match.reset()),
        		ACTION(valueStack.push(nodeFactory.createSqrt())),
        		Parens(), 
        		ACTION(valueStack.build())
        	);
    }

    public Rule Parens() {
        return Sequence("( ", 
        		ACTION(match.reset()),
        		ACTION(valueStack.push(nodeFactory.createBraces())),
        		Expression(),
        		") ",
        		ACTION(valueStack.build())
        	);
    }

    public Rule Number() {
        return Sequence(
                Sequence(
                        Optional(Ch('-')),
                        OneOrMore(baseRules.Digit()),
                        Optional(Ch('.'), OneOrMore(baseRules.Digit()))
                ),
                ACTION(match.reset()),
                ACTION(valueStack.push(nodeFactory.createNumber())),
                ACTION(valueStack.build()),
                WhiteSpace()
        );
    }

    public Rule WhiteSpace() {
        return ZeroOrMore(AnyOf(" \t\f"));
    }

    // we redefine the rule creation for string literals to automatically match trailing whitespace if the string
    // literal ends with a space character, this way we don't have to insert extra whitespace() rules after each
    // character or string literal
    @Override
    protected Rule fromStringLiteral(String string) {
        return string.endsWith(" ") ?
                Sequence(String(string.substring(0, string.length() - 1)), WhiteSpace()) :
                String(string);
    }
	
}
