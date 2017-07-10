package de.promotos.parser.blueprint.parser;

import org.parboiled.BaseParser;
import org.parboiled.Rule;

import de.promotos.parser.blueprint.ast.NodeFactory;

public class BaseRules<StackType> extends BaseParser<StackType> {
	
	final CalculatorParser<StackType> baseParser;
	protected Match<StackType> match;
	protected NodeFactory<StackType> nodeFactory;
	protected ValueStack<StackType> valueStack;
	
	public BaseRules(CalculatorParser<StackType> baseParser) {
		this.baseParser = baseParser;
		
		this.match = new Match<StackType>();
		this.nodeFactory = baseParser.createNodeFactory(this.match);
		this.valueStack = new ValueStack<StackType>(nodeFactory);
	}
	
	Rule Keyword(String keyword) {
        return Terminal(keyword, FirstOf("-", LetterOrDigit()));
    }
	
	Rule Terminal(String string, Rule mustNotFollow) {
        return Sequence(
        	IgnoreCase(string),
        	TestNot(mustNotFollow)
        );
    }
	
	Rule LetterOrDigit() {
        return FirstOf(CharRange('a', 'z'), CharRange('A', 'Z'), CharRange('0', '9'));
    }

    Rule Digit() {
        return CharRange('0', '9');
    }

	public Rule FollowsBefore(Object test, Object before) {
		Rule test_rule = toRule(test);
		Rule before_rule = toRule(before);
		
		return Sequence(
			ZeroOrMore(
				Sequence(
					TestNot(FirstOf(test_rule, before_rule)),
					ANY
				)
			),
			Test(test_rule)
		);
	}

	public Rule SkipTo(Object end) {
		Rule end_rule = toRule(end);
		
		return Sequence(
			ZeroOrMore(
				TestNot(end_rule),
				ANY
			),
			Test(end_rule)
		);
	}
    
}
