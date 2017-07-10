package de.promotos.parser.blueprint;

import static org.parboiled.errors.ErrorUtils.printParseErrors;

import org.junit.Assert;
import org.junit.Test;
import org.parboiled.parserunners.RecoveringParseRunner;
import org.parboiled.support.ParsingResult;

import de.promotos.parser.blueprint.ast.nodes.InputLine;
import de.promotos.parser.blueprint.ast.nodes.Node;

public class CalculatorParsingTest extends CalculatorParserTest {

	@Test
	public void testSimple01() {
		String input = "-5.5+4";
		ParsingResult<Node> result = new RecoveringParseRunner<Node>(getParser().InputLine()).run(input);
		Assert.assertFalse(printParseErrors(result), result.hasErrors());
		Assert.assertNotNull(result.resultValue);
		Assert.assertTrue(result.resultValue instanceof InputLine);
	}

}
